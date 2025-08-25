package com.wangzs.lib.net.remote.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *
 *
 * @author wangzs
 * @since 2024/3/14 17:24
 */
interface IRetrofit {
    fun getOkHttpClientBuilder(): OkHttpClient.Builder

    fun getLoggerInterceptor(): Interceptor

    fun getRetrofit(): Retrofit
}