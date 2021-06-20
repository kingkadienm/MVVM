package com.wangzs.app_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface MainOfficeLauncher : IProvider {

    companion object {
        const val MAIN_OFFICE_LAUNCHER = "/office/module/MainCustomerActivity"
    }

    fun startActivity(context: Context)

}