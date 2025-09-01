package com.wangzs.module.login.repository.remotedata

import com.wangzs.lib.net.remote.RemoteData
import com.wangzs.module.login.repository.service.ModuleApiService
import okhttp3.ResponseBody
import retrofit2.Response

class ModuleRemoteData : RemoteData() {
    private val ollamaApiService = getService<ModuleApiService>("http://192.168.1.12:11434")

    // Repository层
    suspend fun generateStream(): Response<ResponseBody>{
        return ollamaApiService.generateStream()
    }

}