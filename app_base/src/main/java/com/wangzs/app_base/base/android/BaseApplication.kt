package com.wangzs.app_base.base.android

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.wangzs.app_base.AppConfig
import com.wangzs.app_base.BuildConfig
import com.wangzs.app_base.module_base.utils.log.LogUtil


abstract class BaseApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
        LogUtil.setDebugMode()
        init()

    }


    abstract fun init()

}