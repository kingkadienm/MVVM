package com.wangzs.lib.base.module.database.database

import androidx.room.RoomDatabase
import com.wangzs.lib.base.module.database.dao.BaseDao
import com.wangzs.lib.base.module.database.entity.BaseEntity

/**
 * 抽象基础数据库类
 */
abstract class AbstractBaseDatabase<T : BaseDao<out BaseEntity>> : RoomDatabase(), BaseDatabase<T>{

}