package com.wangzs.module.me

import android.util.Log
import androidx.room.RoomDatabase
import com.wangzs.lib.base.module.database.DatabaseInitializer
import com.wangzs.module.me.repository.UserDatabase

class UserDatabaseInitializer : DatabaseInitializer {


    override suspend fun initializeData(database: RoomDatabase) {
        val userDatabase = database as UserDatabase
        val userDao = userDatabase.getBaseDao()


    }

    override fun getModuleName(): String {
        return "UserModule"
    }

    override fun getDatabaseClass(): Class<out RoomDatabase> {
        return UserDatabase::class.java
    }

}