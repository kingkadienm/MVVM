package com.wangzs.lib.net.remote.interceptor

import com.wangzs.lib.net.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class CustomLoggingInterceptor : Interceptor {
    private val loggingInterceptor = HttpLoggingInterceptor()
    
    init {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // 检查是否为流式请求
        val isStreamingRequest = request.header("Accept")?.contains("text/event-stream") == true
        
        // 如果是流式请求，直接转发，不记录日志
        if (isStreamingRequest) {
            return chain.proceed(request)
        }
        
        // 对于非流式请求，使用标准的日志拦截器
        return loggingInterceptor.intercept(chain)
    }
}