package com.wangzs.mvvm

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.wangzs.app_base.utils.log.LogUtil
import com.wangzs.mvvm.base.activity.BaseVmActivity
import com.wangzs.mvvm.base.viewmodel.BaseViewModel

class MainActivity : BaseVmActivity<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        val findViewById = findViewById<RaiseHandsFloatWindow>(R.id.RaiseHandsFloatWindow)
        findViewById.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        findViewById.textView.text = "adadasd"
    }


    override fun createObserver() {

    }

    override fun dismissLoading() {

    }

    override fun showLoading(message: String) {

    }


}