package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by xiedongdong on 2020/12/01
 */
interface MainTodayLauncher : IProvider {

    companion object {
        const val MAIN_TODAY_LAUNCHER = "/today/module/MainTodayActivity"
    }

    fun startActivity(context: Context)

}