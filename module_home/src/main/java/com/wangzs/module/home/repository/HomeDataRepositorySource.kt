package com.wangzs.module.home.repository

import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.dto.Resource
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author wangzs
 * @since 2022/7/10 12:10
 */
interface HomeDataRepositorySource {
    suspend fun getBeautyStar(): Flow<Resource<List<Demo>>>
}