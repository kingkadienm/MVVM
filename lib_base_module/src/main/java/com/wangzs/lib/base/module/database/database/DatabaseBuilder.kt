package com.wangzs.lib.base.module.database.database


import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wangzs.lib.log.KLog
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object DatabaseBuilder {

    private val instances = mutableMapOf<String, RoomDatabase>()
    private val lock = Any()

    fun <T : RoomDatabase> build(
        context: Context,
        dbClass: Class<T>,
        config: DatabaseConfig
    ): T {
        val key = "${dbClass.name}_${config.dbName}"

        return synchronized(lock) {
            instances.getOrPut(key) {
                val builder = if (config.inMemory) {
                    Room.inMemoryDatabaseBuilder(context, dbClass)
                } else {
                    Room.databaseBuilder(context, dbClass, config.dbName)
                }

                builder.apply {
                    setJournalMode(config.journalMode)

                    // 1. 配置迁移
                    config.migrations.forEach { migration ->
                        addMigrations(migration)
                    }

                    // 2. 线程池配置
                    config.queryExecutor?.let { setQueryExecutor(it) }
                        ?: setQueryExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))

                    // 3. 外键支持
                    if (config.enableForeignKeys) {
                        addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                db.execSQL("PRAGMA foreign_keys=ON")
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
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
                    // 6. 添加回调监听
                    addCallback(createDatabaseCallback())
                }.build()
            } as T
        }
    }

    private fun createDatabaseCallback(): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("DatabaseBuilder", "Database created successfully")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d("DatabaseBuilder", "Database opened successfully")
            }
        }
    }

    fun clearInstance(dbClass: Class<*>, dbName: String) {
        val key = "${dbClass.name}_$dbName"
        synchronized(lock) {
            instances[key]?.close()
            instances.remove(key)
        }
    }

    fun clearAll() {
        synchronized(lock) {
            instances.values.forEach { it.close() }
            instances.clear()
        }
    }

    fun getInstanceCount(): Int {
        return synchronized(lock) {
            instances.size
        }
    }
}
