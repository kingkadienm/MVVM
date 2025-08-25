package com.wangzs.module.me.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wangzs.lib.base.module.database.database.AbstractBaseDatabase
import com.wangzs.module.me.repository.local.dao.UserTestRoomDao
import com.wangzs.module.me.repository.local.entity.UserTestRoom

@Database(
    entities = [UserTestRoom::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : AbstractBaseDatabase<UserTestRoomDao>() {

    abstract override fun getBaseDao(): UserTestRoomDao

    companion object {
        const val DB_NAME = "user_database"
    }
}