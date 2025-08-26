package com.wangzs.lib.base.module.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import com.wangzs.lib.base.module.database.database.DatabaseBuilder
import com.wangzs.lib.base.module.database.database.DatabaseConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ServiceLoader

object DatabaseInitializerManager {

    private val initializers = mutableListOf<DatabaseInitializer>()
    private val databaseInstances = mutableMapOf<String, RoomDatabase>()
    private val lock = Any()

    /**
     * 注册模块的数据初始化器
     */
    fun registerInitializer(initializer: DatabaseInitializer) {
        synchronized(lock) {
            if (!initializers.any { it.getModuleName() == initializer.getModuleName() }) {
                initializers.add(initializer)
                Log.d("DatabaseInitializer", "注册初始化器: ${initializer.getModuleName()}")
            }
        }
    }

    /**
     * 自动发现并注册所有模块的初始化器
     */
    fun autoDiscoverInitializers() {
        try {
            ServiceLoader.load(DatabaseInitializerRegistry::class.java)
                .forEach { registry ->
                    registry.registerInitializers(this)
                }
            Log.d(
                "DatabaseInitializer",
                "自动发现初始化器完成，共注册 ${initializers.size} 个初始化器"
            )
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "自动发现初始化器失败", e)
        }
    }

    /**
     * 初始化所有注册的模块数据
     */
    suspend fun initializeAllData(context: Context) {
        initializers.forEach { initializer ->
            try {
                initializeModuleData(context, initializer)
            } catch (e: Exception) {
                Log.e(
                    "DatabaseInitializer",
                    "${initializer.getModuleName()} 数据初始化失败: ${e.message}", e
                )
            }
        }
    }

    /**
     * 初始化特定模块的数据
     */
    suspend fun initializeModuleData(context: Context, initializer: DatabaseInitializer) {
        val databaseClass = initializer.getDatabaseClass()
        val databaseName = getDatabaseName(databaseClass)
        val key = "${databaseClass.name}_$databaseName"

        synchronized(lock) {
            if (key in databaseInstances) {
                Log.d("DatabaseInitializer", "${initializer.getModuleName()} 数据已初始化，跳过")
                return
            }
        }

        withContext(Dispatchers.IO) {
            Log.d("DatabaseInitializer", "开始初始化 ${initializer.getModuleName()} 数据")

            // 创建数据库实例并保存
            val database = DatabaseBuilder.build(
                context = context,
                dbClass = databaseClass,
                config = DatabaseConfig(dbName = databaseName)
            )

            // 执行数据初始化
            initializer.initializeData(database)

            synchronized(lock) {
                databaseInstances[key] = database
            }

            Log.d("DatabaseInitializer", "${initializer.getModuleName()} 数据初始化完成")
        }
    }

    /**
     * 获取已初始化的数据库实例
     */
    fun <T : RoomDatabase> getDatabaseInstance(databaseClass: Class<T>): T? {
        val databaseName = getDatabaseName(databaseClass)
        val key = "${databaseClass.name}_$databaseName"

        return synchronized(lock) {
            databaseInstances[key] as? T
        }
    }

    /**
     * 获取或创建数据库实例（如果不存在则创建）
     */
    fun <T : RoomDatabase> getOrCreateDatabase(
        context: Context,
        databaseClass: Class<T>,
        config: DatabaseConfig
    ): T {
        val key = "${databaseClass.name}_${config.dbName}"

        return synchronized(lock) {
            databaseInstances.getOrPut(key) {
                DatabaseBuilder.build(context, databaseClass, config)
            } as T
        }
    }

    private fun getDatabaseName(databaseClass: Class<out RoomDatabase>): String {
        val annotation = databaseClass.getAnnotation(Database::class.java)
        return annotation?.entities?.firstOrNull()?.simpleName?.lowercase() + "_db"
    }

    /**
     * 检查数据库是否已初始化
     */
    fun isDatabaseInitialized(databaseClass: Class<out RoomDatabase>): Boolean {
        val databaseName = getDatabaseName(databaseClass)
        val key = "${databaseClass.name}_$databaseName"

        return synchronized(lock) {
            databaseInstances.containsKey(key)
        }
    }

    /**
     * 获取所有已初始化的数据库名称
     */
    fun getInitializedDatabases(): List<String> {
        return synchronized(lock) {
            databaseInstances.keys.toList()
        }
    }

    /**
     * 清空所有初始化器和数据库实例
     */
    fun clearInitializers() {
        synchronized(lock) {
            initializers.clear()
            databaseInstances.values.forEach { it.close() }
            databaseInstances.clear()
        }
    }

    /**
     * 获取已注册的初始化器数量
     */
    fun getInitializerCount(): Int {
        return synchronized(lock) {
            initializers.size
        }
    }
}