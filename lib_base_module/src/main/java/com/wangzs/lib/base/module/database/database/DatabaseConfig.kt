package com.wangzs.lib.base.module.database.database

import androidx.room.migration.Migration
import java.util.concurrent.Executor



data class DatabaseConfig(
    val dbName: String,
    val migrations: List<Migration> = emptyList(),
    val queryExecutor: Executor? = null,
    val transactionExecutor: Executor? = null,
    val enableForeignKeys: Boolean = true,
    val allowMainThreadQueries: Boolean = false,
    val enableMultiInstanceInvalidation: Boolean = false,
    val requireMigration: Boolean = true,
    val enableDebugLogging: Boolean = false
) {
    companion object {
        fun inMemoryConfig() = DatabaseConfig(
            dbName = "test_db",
            enableForeignKeys = true,
            allowMainThreadQueries = true,
            requireMigration = false,

            )
        fun defaultConfig(dbName: String) = DatabaseConfig(
            dbName = dbName,
            enableForeignKeys = true,
            allowMainThreadQueries = false,
            requireMigration = true
        )
    }
}