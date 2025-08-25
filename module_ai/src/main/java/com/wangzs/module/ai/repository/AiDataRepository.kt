package com.wangzs.module.ai.repository

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.wangzs.lib.net.BaseDataRepository
import com.wangzs.module.ai.bean.GenerateRequest
import com.wangzs.module.ai.bean.GenerateResponse
import com.wangzs.module.ai.repository.remotedata.AiRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response

class AiDataRepository : BaseDataRepository(), AiDataRepositorySource {
    private val aiRemoteData by lazy { AiRemoteData() }

    override   fun generateStream(request: GenerateRequest): Flow<String> {
        return flow {
            try {
                val response: Response<ResponseBody> = aiRemoteData.generateStream(request)
                if (response.isSuccessful) {
                    response.body()?.use { body ->
                        body.byteStream().bufferedReader().useLines { lines ->
                            lines.forEach { jsonLine ->
                                try {
                                    val generateResponse = Gson().fromJson(jsonLine, GenerateResponse::class.java)
                                    emit(generateResponse.response)
                                } catch (e: JsonSyntaxException) {
                                    emit("Parse error: $jsonLine")
                                }
                            }
                        }
                    } ?: emit("Error: Response body is null")
                } else {
                    emit("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                emit("Error: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }



}