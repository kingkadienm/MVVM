package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainTodayLauncher : IProvider {

    companion object {
        const val MAIN_TODAY_LAUNCHER = "/today/module/MainTodayActivity"
    }

    fun startActivity(context: Context)

}