package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainEmailLauncher : IProvider {

    companion object {
        const val MAIN_EMAIL_LAUNCHER = "/email/module/MainCustomerActivity"
    }

    fun startActivity(context: Context)

}