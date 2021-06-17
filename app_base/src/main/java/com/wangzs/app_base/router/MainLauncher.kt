package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by xiedongdong on 2020/12/01
 */
interface MainLauncher : IProvider {

    companion object {
        const val MAIN_LAUNCHER = "/app/main/MainActivity"
    }

    fun startMainActivity(context: Context)

}