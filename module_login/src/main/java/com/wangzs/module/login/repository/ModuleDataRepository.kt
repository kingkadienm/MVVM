package com.wangzs.module.login.repository

import com.wangzs.module.login.repository.remotedata.ModuleRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response

class ModuleDataRepository : ModuleDataRepositorySource {
    private val moduleRemoteData by lazy { ModuleRemoteData() }

    override fun generateStream(): Flow<String> {
        return flow {
            try {
                val response: Response<ResponseBody> = moduleRemoteData.generateStream()
            } catch (e: Exception) {
                emit("Error: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }


}