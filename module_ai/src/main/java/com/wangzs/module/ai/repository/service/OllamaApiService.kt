package com.wangzs.module.ai.repository.service

import com.wangzs.lib.net.remote.service.BaseApiService
import com.wangzs.module.ai.bean.GenerateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface OllamaApiService : BaseApiService {
    @POST("/api/generate")
    @Streaming
    @Headers("Accept: text/event-stream")
    suspend fun generateStream(@Body request: GenerateRequest): Response<ResponseBody>
}

