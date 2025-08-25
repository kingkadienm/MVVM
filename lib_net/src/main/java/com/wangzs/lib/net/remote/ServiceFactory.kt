package com.wangzs.lib.net.remote

import com.wangzs.lib.net.remote.network.RetrofitManager
import com.wangzs.lib.net.remote.service.BaseApiService

object ServiceFactory {

    private val serviceCache = mutableMapOf<String, Any>()

    // 移除 inline 修饰符
    fun <T : BaseApiService> createService(baseUrl: String, serviceClass: Class<T>): T {
        val cacheKey = "${serviceClass.name}-$baseUrl"

        @Suppress("UNCHECKED_CAST")
        return serviceCache.getOrPut(cacheKey) {
            val retrofitManager = RetrofitManager.Companion.getInstance(baseUrl)
            retrofitManager.create(serviceClass)
        } as T
    }

    // 添加一个带 reified 的扩展函数（可选）
    inline fun <reified T : BaseApiService> createService(baseUrl: String): T {
        return createService(baseUrl, T::class.java)
    }

    fun clearCache() {
        serviceCache.clear()
    }
}