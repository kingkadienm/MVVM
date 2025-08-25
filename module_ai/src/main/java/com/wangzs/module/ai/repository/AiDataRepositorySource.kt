package com.wangzs.module.ai.repository

import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.ai.bean.ChatRequest
import com.wangzs.module.ai.bean.ChatResponse
import com.wangzs.module.ai.bean.GenerateRequest
import com.wangzs.module.ai.bean.GenerateResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Author: wangzs<br>
 * Created Date: 2025/8/23 20:42<br>
 * Desc : <br>
 */
interface AiDataRepositorySource {

      fun generateStream(request: GenerateRequest): Flow<String>

}

