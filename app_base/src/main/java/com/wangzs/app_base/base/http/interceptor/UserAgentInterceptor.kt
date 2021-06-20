package com.wangzs.app_base.base.http.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class UserAgentInterceptor(context: Context) : Interceptor {
    private val userAgent: String = com.wangzs.app_base.base.http.UserAgentBuilder.ua(context)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().header("User-Agent", userAgent).build()
        return chain.proceed(request)
    }

}