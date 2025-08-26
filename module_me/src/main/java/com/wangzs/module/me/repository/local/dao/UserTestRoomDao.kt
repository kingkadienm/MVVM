package com.wangzs.module.me.repository.local.dao

import androidx.room.*
import com.wangzs.lib.base.module.database.dao.BaseDao
import com.wangzs.module.me.repository.local.entity.UserTestRoom


/**
 * 用户 DAO 接口 - 继承 BaseRoomDao 并添加特定方法
 */
@Dao
abstract class UserTestRoomDao : BaseDao<UserTestRoom>() {

    @Insert
    abstract suspend  fun insertUserTestRoom(userTestRoom: UserTestRoom): Long

    @Update
    abstract suspend fun updateUserTestRoom(newUserTestRoom: UserTestRoom)

    @Query("SELECT * FROM USERTESTROOM ORDER BY id DESC")
    abstract suspend  fun loadAllUserTestRooms(): List<UserTestRoom>

    @Query("SELECT * FROM USERTESTROOM WHERE AGE > :age")
    abstract suspend  fun loadUserTestRoomOlderThan(age: Int): List<UserTestRoom>

    @Query("SELECT * FROM USERTESTROOM ORDER BY id DESC LIMIT :pageSize OFFSET :offset")
    abstract suspend   fun loadUserTestRoomsPaged(pageSize: Int, offset: Int): List<UserTestRoom>

    @Delete
    abstract suspend  fun deleteUserTestRoom(deleteUserTestRoom: UserTestRoom): Int

    @Query("DELETE FROM USERTESTROOM WHERE lastName = :lastName")
    abstract suspend  fun deleteUserTestRoomByLastName(lastName: String): Int
}