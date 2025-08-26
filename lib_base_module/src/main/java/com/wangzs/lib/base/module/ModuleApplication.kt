package com.wangzs.lib.base.module

import android.util.Log
import com.wangzs.lib.base.BaseApplication
import com.wangzs.lib.base.module.database.DatabaseInitializerManager
import com.wangzs.lib.base.module.database.database.DatabaseBuilder
import com.wangzs.lib.log.KLog
import com.wangzs.lib.net.config.NetConfig
import com.wangzs.lib.net.config.URL_EDITH
import com.wangzs.lib.net.config.URL_MAIN
import com.wangzs.lib.net.config.domainConfigsInit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 初始化应用程序
 * @author wangzs
 * @time 2020/11/30 23:04
 */
open class ModuleApplication : BaseApplication() {

    override fun initOnlyMainProcessInLowPriorityThread() {
        super.initOnlyMainProcessInLowPriorityThread()
        initNet()
    }

    override fun onCreate() {
        super.onCreate()
        // 自动发现并注册所有模块的初始化器
        DatabaseInitializerManager.autoDiscoverInitializers()

        // 异步初始化所有模块数据
        initializeDatabases()
    }

    private fun initializeDatabases() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val startTime = System.currentTimeMillis()
                DatabaseInitializerManager.initializeAllData(this@ModuleApplication)
                val duration = System.currentTimeMillis() - startTime
                Log.d("App", "数据库初始化完成，耗时: ${duration}ms")
                // 通知所有观察者数据库已就绪
                notifyDatabaseReady()
            } catch (e: Exception) {
                Log.e("App", "数据库初始化失败", e)
            }
        }
    }
    protected fun notifyDatabaseReady() {
        // 可以通过 LiveData、EventBus 等方式通知其他组件
    }

    private fun initNet() {
        domainConfigsInit(this) {
            addConfig(URL_MAIN, NetConfig.Builder().apply {
                setDefaultTimeout(5_000)
            })
            addConfig(URL_EDITH, NetConfig.Builder().apply {
                enableHttps(true)
                setDefaultTimeout(6_000)
            })
            addConfig("http://192.168.1.12:11434", NetConfig.Builder().apply {
                setDefaultTimeout(6_000)
            })

        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // 清理资源
        DatabaseInitializerManager.clearInitializers()
        DatabaseBuilder.clearAll()
    }

}
