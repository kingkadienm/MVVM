package com.wangzs.app_base.base.android

import android.app.Application


abstract class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        init()
    }

    abstract fun init()

}