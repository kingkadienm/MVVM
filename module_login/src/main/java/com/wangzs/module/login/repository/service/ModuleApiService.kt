package com.wangzs.module.login.repository.service

import com.wangzs.lib.net.remote.service.BaseApiService
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ModuleApiService : BaseApiService {
    @POST("/api/generate")
    @Streaming
    @Headers("Accept: text/event-stream")
    suspend fun generateStream(): Response<ResponseBody>
}

