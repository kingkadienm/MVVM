package com.wangzs.app_base.router

import com.alibaba.android.arouter.launcher.ARouter
import com.wangzs.app_base.toast.BaseToast
import com.wangzs.app_base.BuildConfig

/**
 * Created by xiedongdong on 2020/11/28
 */
object Launcher {

    @JvmStatic
    fun check(): Boolean {
        return if (!BuildConfig.IS_MODULE) {
            true
        } else {
            BaseToast.show("组件开发中")
            false
        }
    }

    @JvmStatic
    fun <T> navigation(launcher: Class<out T>?): T {
        return ARouter.getInstance().navigation(launcher)
    }

}