package com.wangzs.mvvm.ext.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.wangzs.app_base.utils.log.LogUtil

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        KtxActivityManger.pushActivity(activity)
        LogUtil.d("onActivityCreated : ${activity.localClassName}")
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtil.d(  "onActivityStarted : ${activity.localClassName}")
    }

    override fun onActivityResumed(activity: Activity) {
        LogUtil.d( "onActivityResumed : ${activity.localClassName}")
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtil.d( "onActivityPaused : ${activity.localClassName}")
    }


    override fun onActivityDestroyed(activity: Activity) {
        LogUtil.d( "onActivityDestroyed : ${activity.localClassName}")
        KtxActivityManger.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
        LogUtil.d( "onActivityStopped : ${activity.localClassName}")
    }


}