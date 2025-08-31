package com.wangzs.module.me.repository

import com.wangzs.lib.base.module.database.DatabaseInitializerManager
import com.wangzs.lib.base.module.database.extension.FlowExtensions.dealDataFlow
import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.me.repository.local.entity.UserTestRoom
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor() {

    private val userDatabase by lazy {
        // 使用共享的数据库实例
        DatabaseInitializerManager.getDatabaseInstance(UserDatabase::class.java)
            ?: throw IllegalStateException("UserDatabase 尚未初始化，请先调用 DatabaseInitializerManager.initializeAllData()")
    }
    private val userDao by lazy { userDatabase.getBaseDao() }



    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository().also { instance = it }
            }
        }
    }

    suspend fun doLogin(): Flow<Resource<String>> {
        return dealDataFlow { Resource.Success("") }
    }

    suspend fun removeUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Int>> {
        return dealDataFlow { Resource.Success(userDao.deleteUserTestRoom(userTestRoom)) }
    }

    suspend fun insertUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Long>> {
        return dealDataFlow { Resource.Success(userDao.insertUserTestRoom(userTestRoom)) }
    }

    suspend fun getAllUserTestRoom(): Flow<Resource<List<UserTestRoom>>> {
        return dealDataFlow { Resource.Success(userDao.loadAllUserTestRooms()) }
    }

    suspend fun getUserTestRoom(
        pageSize: Int,
        pageNumber: Int
    ): Flow<Resource<List<UserTestRoom>>> {
        return dealDataFlow {
            Resource.Success(
                userDao.loadUserTestRoomsPaged(pageSize, calculateOffset(pageSize, pageNumber))
            )
        }
    }
    /**
     * 计算页数偏移量
     * @param [pageSize] 页面大小
     * @param [pageNumber] 页码
     * @return [Int] 偏移量
     */
    private fun calculateOffset(pageSize: Int, pageNumber: Int): Int {
        return pageSize * (pageNumber - 1)
    }

}