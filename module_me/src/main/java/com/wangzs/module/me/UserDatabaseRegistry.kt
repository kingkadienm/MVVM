package com.wangzs.module.me

import com.google.auto.service.AutoService
import com.wangzs.lib.base.module.database.DatabaseInitializerManager
import com.wangzs.lib.base.module.database.DatabaseInitializerRegistry

@AutoService(DatabaseInitializerRegistry::class)
class UserDatabaseRegistry : DatabaseInitializerRegistry {
    override fun registerInitializers(manager: DatabaseInitializerManager) {
        manager.registerInitializer(UserDatabaseInitializer())
    }
}