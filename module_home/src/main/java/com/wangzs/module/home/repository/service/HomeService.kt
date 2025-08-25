package com.wangzs.module.home.repository.service

import com.wangzs.lib.domain.base.BaseResponse
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.remote.service.BaseApiService
import retrofit2.Response
import retrofit2.http.GET

/**
 *
 *
 * @author wangzs
 * @since 2022/7/10 11:50
 */
interface HomeService : BaseApiService {
    @GET("res/demo/beauty_star.json")
    suspend fun getBeautyStar(): Response<BaseResponse<List<Demo>>>
}