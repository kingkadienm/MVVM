package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainLauncher : IProvider {

    companion object {
        const val MAIN_LAUNCHER = "/app/main/MainActivity"
    }

    fun startMainActivity(context: Context)

}