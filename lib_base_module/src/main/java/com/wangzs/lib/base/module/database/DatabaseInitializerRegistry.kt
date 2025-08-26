package com.wangzs.lib.base.module.database

interface DatabaseInitializerRegistry {
    fun registerInitializers(manager: DatabaseInitializerManager)
}