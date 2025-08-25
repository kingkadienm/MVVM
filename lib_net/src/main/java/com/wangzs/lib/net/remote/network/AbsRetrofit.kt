package com.wangzs.lib.net.remote.network

import com.wangzs.lib.net.BuildConfig
import com.wangzs.lib.net.remote.interceptor.CustomLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 *
 *
 * @author wangzs
 * @since 2024/3/14 17:25
 */
abstract class AbsRetrofit : IRetrofit {

    override fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    override fun getLoggerInterceptor(): Interceptor {
        return CustomLoggingInterceptor()
    }

    fun <S> create(serviceClass: Class<S>): S = getRetrofit().create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}