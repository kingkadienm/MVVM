package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainMineLauncher : IProvider {

    companion object {
        const val MAIN_MINE_LAUNCHER = "/mine/module/MainCustomerActivity"
    }

    fun startActivity(context: Context)

}