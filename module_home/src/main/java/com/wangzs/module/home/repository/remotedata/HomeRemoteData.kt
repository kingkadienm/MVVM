package com.wangzs.module.home.repository.remotedata

import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.dto.Resource
import com.wangzs.lib.net.remote.RemoteData
import com.wangzs.module.home.repository.service.HomeService
import com.wangzs.module.home.repository.service.RecipesService

/**
 * Home 远端仓库
 *
 * @author wangzs
 * @since 2022/7/10 11:40
 */
class HomeRemoteData : RemoteData() {
    private val recipesService = getService<RecipesService>()
    private val homeService = getService<HomeService>()


    // 使用包装版本
    suspend fun getBeautyStar(): Resource<List<Demo>?> {
        return wrappedApiCall { recipesService.fetchRecipes() }
    }
    // 正确用法：使用 wrappedApiCall 处理包装响应
    suspend fun requestRecipes(): Resource<List<Demo>?> {
        return wrappedApiCall { homeService.getBeautyStar() }
    }
}