package com.wangzs.lib.base.module.database

import androidx.room.RoomDatabase

/**
 * Author: wangzs<br>
 * Created Date: 2025/8/26 14:54<br>
 * Desc : <br>
 */
interface DatabaseInitializer {
    /**
    * 初始化模块特定的数据
    */
    suspend fun initializeData(database: RoomDatabase)

    /**
    * 获取模块名称，用于日志和标识
    */
    fun getModuleName(): String

    /**
    * 获取关联的数据库类
    */
    fun getDatabaseClass(): Class<out RoomDatabase>
}