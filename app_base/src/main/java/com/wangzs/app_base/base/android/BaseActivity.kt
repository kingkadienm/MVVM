package com.wangzs.app_base.base.android

import android.os.Bundle
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity


open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}