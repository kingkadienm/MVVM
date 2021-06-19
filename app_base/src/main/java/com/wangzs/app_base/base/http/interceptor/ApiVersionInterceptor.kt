package com.wangzs.app_base.base.http.interceptor

import com.wangzs.app_base.module_base.base.AppBaseApplication
import com.wangzs.app_base.module_base.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by xiedongdong on 2020/01/18
 */
class ApiVersionInterceptor : Interceptor {

    private val versionName = AppUtils.getAppVersionName(AppBaseApplication.context)
    private var token = ""

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("versionName", versionName)
            .addHeader("token", token)
            .build()
        return chain.proceed(request)
    }

}