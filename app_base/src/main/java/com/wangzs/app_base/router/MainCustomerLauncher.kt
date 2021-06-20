package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainCustomerLauncher : IProvider {

    companion object {
        const val MAIN_CUSTOMER_LAUNCHER = "/customer/module/MainCustomerActivity"
    }

    fun startActivity(context: Context)

}