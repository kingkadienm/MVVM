package com.wangzs.lib.base.module.database.database

import com.wangzs.lib.base.module.database.dao.BaseDao
import com.wangzs.lib.base.module.database.entity.BaseEntity

/**
* 基础数据库接口
* 所有组件数据库都应继承此接口
*/
interface BaseDatabase<T : BaseDao<out BaseEntity>> {
    fun getBaseDao(): T
}