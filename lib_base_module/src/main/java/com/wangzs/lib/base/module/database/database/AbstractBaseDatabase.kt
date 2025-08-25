package com.wangzs.lib.base.module.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wangzs.lib.base.module.database.database.BaseDatabase
import com.wangzs.lib.base.module.database.dao.CoreDao
import com.wangzs.lib.base.module.database.entity.CoreEntity

/**
 * 抽象基础数据库类
 */
abstract class AbstractBaseDatabase<T : CoreDao<out CoreEntity>> : RoomDatabase(), BaseDatabase<T>{

}