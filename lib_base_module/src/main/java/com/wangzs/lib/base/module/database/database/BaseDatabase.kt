package com.wangzs.lib.base.module.database.database

import com.wangzs.lib.base.module.database.dao.CoreDao
import com.wangzs.lib.base.module.database.entity.CoreEntity

/**
* 基础数据库接口
* 所有组件数据库都应继承此接口
*/
interface BaseDatabase<T : CoreDao<out CoreEntity>> {
    fun getBaseDao(): T
}