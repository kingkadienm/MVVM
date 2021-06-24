package com.wangzs.mvvm.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.wangzs.app_base.AppConfig
import com.wangzs.app_base.BuildConfig
 import com.wangzs.app_base.toast.BaseToast
import com.wangzs.app_base.utils.log.LogUtil


open class BaseApp : MultiDexApplication(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        AppConfig.init(this)
        context = this
        LogUtil.setDebugMode()
        initARouter()
        BaseToast.init(this)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }


    private fun initARouter() {
        // 必须写在init之前
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }


    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}