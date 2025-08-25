package com.wangzs.module.home.repository

import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.BaseDataRepository
import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.home.repository.remotedata.HomeRemoteData
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author wangzs
 * @since 2022/7/10 11:47
 */
class HomeDataRepository : BaseDataRepository(), HomeDataRepositorySource {

    private val homeRemoteData by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { HomeRemoteData() }

    override suspend fun getBeautyStar(): Flow<Resource<List<Demo>>> {
        return dealDataFlow {
            val beautyStar = homeRemoteData.getBeautyStar()
            val requestRecipes = homeRemoteData.requestRecipes()
            val resource =
                if (beautyStar.isSuccess() || requestRecipes.isSuccess()) {
                    Resource.Success(arrayListOf<Demo>())
                } else {
                    Resource.DataError((beautyStar.errorCode + requestRecipes.errorCode) / 2)
                }
            if (beautyStar.isSuccess() && beautyStar.data?.isNotEmpty() == true) {
                beautyStar.data?.let {
                    resource.data?.addAll(it)
                }
            }
            if (resource.isSuccess() && requestRecipes.data?.isNotEmpty() == true) {
                requestRecipes.data?.let {
                    resource.data?.addAll(it)
                }
            }
            resource
        }
    }
}