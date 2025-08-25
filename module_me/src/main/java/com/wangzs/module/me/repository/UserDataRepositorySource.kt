package com.wangzs.module.me.repository

import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.me.repository.local.entity.UserTestRoom
import kotlinx.coroutines.flow.Flow

interface UserDataRepositorySource {
    suspend fun doLogin(): Flow<Resource<String>>
    suspend fun removeUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Int>>
    suspend fun insertUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Long>>
    suspend fun getAllUserTestRoom(): Flow<Resource<List<UserTestRoom>>>
    suspend fun getUserTestRoom(pageSize: Int, pageNumber: Int): Flow<Resource<List<UserTestRoom>>>
}