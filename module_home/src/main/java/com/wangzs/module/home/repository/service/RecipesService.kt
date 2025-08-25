package com.wangzs.module.home.repository.service

import com.wangzs.lib.domain.base.BaseResponse
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.remote.service.BaseApiService
import retrofit2.Response
import retrofit2.http.GET

/**
 * 服务端提供数据接口方法
 * @author wangzs
 * @time 2020/11/30
 */
interface RecipesService : BaseApiService {
    @GET("res/demo/recipes.json")
    suspend fun fetchRecipes(): Response<BaseResponse<List<Demo>>>
}