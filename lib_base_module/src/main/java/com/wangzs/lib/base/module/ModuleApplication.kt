package com.wangzs.lib.base.module

import android.os.Environment
import android.util.Log
import android.view.Gravity
import com.wangzs.lib.base.BaseApplication
import com.wangzs.lib.base.module.database.DatabaseInitializerManager
import com.wangzs.lib.base.module.database.database.DatabaseBuilder
import com.wangzs.lib.common.utils.CrashHandler
import com.wangzs.lib.net.config.NetConfig
import com.wangzs.lib.net.config.URL_EDITH
import com.wangzs.lib.net.config.URL_MAIN
import com.wangzs.lib.net.config.domainConfigsInit
import com.wangzs.lib.utils.LogUtils
import com.wangzs.lib.utils.ToastUtils
import com.wangzs.lib.utils.Utils
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
        //初始化lib_utils
        Utils.init(this)
        // 初始化 LogUtils
        LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)      // 设置日志总开关
            .setConsoleSwitch(BuildConfig.DEBUG)  // 设置控制台日志开关
            .setGlobalTag("MyApp")   // 设置全局日志标签
            .setLogHeadSwitch(true)  // 设置日志头部信息显示
            .setLog2FileSwitch(true) // 设置是否把日志写入文件
//            .setDir("${Environment.getExternalStorageDirectory()}/MyApp/logs")
            .setFilePrefix("app_log")
            .setFileFilter(LogUtils.I) // 设置文件日志过滤级别
            .setBorderSwitch(true)   // 设置日志边框开关
            .setSingleTagSwitch(true)// 设置是否使用单一标签
            .setConsoleFilter(LogUtils.V) // 设置控制台日志过滤级别
            .setStackDeep(1)         // 设置堆栈深度
            .setStackOffset(0);      // 设置堆栈偏移
        //初始Toast
        val defaultMaker = ToastUtils.getDefaultMaker()
        defaultMaker.setGravity(Gravity.CENTER,0,0)
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
                CrashHandler.instance.init(this@ModuleApplication)
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
