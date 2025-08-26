package com.wangzs.module.me

import com.wangzs.lib.base.module.database.DatabaseInitializerManager
import com.wangzs.lib.base.module.database.DatabaseInitializerRegistry

class UserDatabaseRegistry : DatabaseInitializerRegistry {
    override fun registerInitializers(manager: DatabaseInitializerManager) {
        manager.registerInitializer(UserDatabaseInitializer())
        // 可以注册多个用户相关的初始化器
        // manager.registerInitializer(UserSettingsInitializer())
    }
}