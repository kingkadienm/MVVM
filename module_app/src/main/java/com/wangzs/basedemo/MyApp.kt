package com.wangzs.basedemo

import com.wangzs.lib.base.manager.ActivityManager
import com.wangzs.lib.base.module.ModuleApplication

/**
 * Describe:
 * App
 *
 * @author wangzs
 * @Date 2020/12/1
 */
class MyApp : ModuleApplication(){
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityManager.instance)
    }
}
