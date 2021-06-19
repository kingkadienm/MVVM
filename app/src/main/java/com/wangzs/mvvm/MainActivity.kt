package com.wangzs.mvvm

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.wangzs.app_base.module_base.base.AppBaseActivity
import com.wangzs.app_base.module_base.utils.EasyPermissionsEx
import com.wangzs.app_base.module_base.utils.FileAccessor
import com.wangzs.app_base.module_base.utils.log.LogUtil

class MainActivity : AppBaseActivity() {


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?, view: View?) {


        if (EasyPermissionsEx.hasPermissions(this, needPermissionsReadExternalStorage)) {

        } else {
            EasyPermissionsEx.requestPermissions(
                this,
                getString(R.string.str_permission_save),
                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE,
                needPermissionsReadExternalStorage
            )
        }

        findViewById<Button>(R.id.testBtn).setOnClickListener {
            LogUtil.e("daddddd")
            FileAccessor.getCloudPathName()
        }
    }

    override fun initEvent() {

    }

    override fun initData() {

    }

    override fun isShowTitle(): Boolean {
        return true
    }

    override fun getTitleStr(): String? {
        return "title"
    }
}