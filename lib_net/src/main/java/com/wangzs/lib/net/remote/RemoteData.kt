package com.wangzs.lib.net.remote

import com.wangzs.lib.base.AppConfig
import com.wangzs.lib.domain.base.BaseResponse
import com.wangzs.lib.net.dto.Resource
import com.wangzs.lib.net.remote.network.AbsRemoteDataSource
import com.wangzs.lib.net.remote.network.RetrofitManager
import com.wangzs.lib.net.remote.service.BaseApiService
import retrofit2.Response

/**
 * 服务（域名）实现接口请求实现类
 *
 * @author wangzs
 * @since 2024/3/14
 */
open class RemoteData : AbsRemoteDataSource() {
    companion object {
        private val serviceCache = mutableMapOf<String, Any>()
        fun <T : BaseApiService> createService(baseUrl: String, serviceClass: Class<T>): T {
            val cacheKey = "${serviceClass.name}-$baseUrl"
            @Suppress("UNCHECKED_CAST")
            return serviceCache.getOrPut(cacheKey) {
                val retrofitManager = RetrofitManager.Companion.getInstance(baseUrl)
                retrofitManager.create(serviceClass)
            } as T
        }
        inline fun <reified T : BaseApiService> createService(baseUrl: String): T {
            return createService(baseUrl, T::class.java)
        }
    }

    // 默认使用主URL
    protected inline fun <reified T : BaseApiService> getService(baseUrl: String = AppConfig.getHostUrl()): T {
        return createService(baseUrl)
    }
    protected inline fun <reified T : BaseApiService> getMainService(): T {
        return createService(AppConfig.getHostUrl())
    }
    /**
     * 处理原始响应（非BaseResponse包装）
     */
    protected suspend fun <T> apiCall(responseCall: suspend () -> Response<T>): Resource<T?> {
        return processCall(responseCall)
    }
    /**
     * 处理BaseResponse包装的响应（推荐使用这个）
     */
    protected suspend fun <T> wrappedApiCall(responseCall: suspend () -> Response<BaseResponse<T>>): Resource<T?> {
        return convertWrapper(processCall(responseCall))
    }
}