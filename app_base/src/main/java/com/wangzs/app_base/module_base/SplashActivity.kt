package com.wangzs.app_base.module_base

import android.os.Bundle
import com.wangzs.app_base.base.android.BaseActivity

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toMainActivity()
    }

    private fun toMainActivity() {
//        if (BuildConfig.DEBUG && BuildConfig.IS_MODULE) {
//            // 根据包名，跳转不同的组件
//            when (AppUtils.getAppPackageName(this)) {
//                "com.wangzs.app_base.biz.today" -> Launcher.navigation(MainTodayLauncher::class.java)
//                    .startActivity(this)
//                "com.wangzs.app_base.biz.customer" -> Launcher.navigation(MainCustomerLauncher::class.java)
//                    .startActivity(this)
//                "com.wangzs.app_base.biz.email" -> Launcher.navigation(MainEmailLauncher::class.java)
//                    .startActivity(this)
//                "com.wangzs.app_base.biz.office" -> Launcher.navigation(MainOfficeLauncher::class.java)
//                    .startActivity(this)
//                "com.wangzs.app_base.biz.mine" -> Launcher.navigation(MainMineLauncher::class.java)
//                    .startActivity(this)
//            }
//        } else {
//            if (Launcher.check()) {
//                Launcher.navigation(MainLauncher::class.java).startMainActivity(this)
//            }
//        }

        finish()
    }

}