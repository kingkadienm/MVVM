package com.wangzs.module.ai.repository.remotedata

import com.google.gson.Gson
import com.wangzs.lib.log.KLog
import com.wangzs.lib.net.remote.RemoteData
import com.wangzs.module.ai.bean.GenerateRequest
import com.wangzs.module.ai.bean.GenerateResponse
import com.wangzs.module.ai.repository.service.OllamaApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming
import java.io.IOException

class AiRemoteData : RemoteData() {
    private val ollamaApiService = getService<OllamaApiService>("http://192.168.1.12:11434")



    // Repository层
    suspend fun generateStream(request: GenerateRequest): Response<ResponseBody>{
        return ollamaApiService.generateStream(request)
    }

}