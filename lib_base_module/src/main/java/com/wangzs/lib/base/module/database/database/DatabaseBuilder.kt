package com.wangzs.lib.base.module.database.database


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object DatabaseBuilder {

    private val instances = mutableMapOf<String, RoomDatabase>()

    fun <T : RoomDatabase> build(
        context: Context,
        dbClass: Class<T>,
        config: DatabaseConfig
    ): T {
        val key = "${dbClass.name}_${config.dbName}"
        return instances.getOrPut(key) {
            Room.databaseBuilder(context, dbClass, config.dbName).apply {
                // 1. 配置迁移
                config.migrations.forEach { migration ->
                    addMigrations(migration)
                }

                // 2. 线程池配置
                config.queryExecutor?.let { setQueryExecutor(it) }
                    ?: setQueryExecutor(Executors.newFixedThreadPool(4))

                // 3. 外键支持
                if (config.enableForeignKeys) {
                    addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            db.execSQL("PRAGMA foreign_keys=ON")
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            db.execSQL("PRAGMA foreign_keys=ON")
                        }
                    })
                }

                // 4. 其他配置
                if (config.allowMainThreadQueries) {
                    allowMainThreadQueries()
                }
                if (!config.requireMigration) {
                    fallbackToDestructiveMigration()
                }
            }.build()
        } as T
    }

    fun clearAll() {
        instances.values.forEach { it.close() }
        instances.clear()
    }
}
