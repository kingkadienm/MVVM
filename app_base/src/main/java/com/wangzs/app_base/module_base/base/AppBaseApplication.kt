package com.wangzs.app_base.module_base.base

import android.annotation.SuppressLint
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.wangzs.app_base.base.android.BaseApplication
import com.wangzs.app_base.toast.BaseToast

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
open class AppBaseApplication : BaseApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun init() {
        context = this
        initARouter()
        BaseToast.init(this)
    }

    private fun initARouter() {
        // 必须写在init之前
//        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
//        }
        ARouter.init(this)
    }

}
