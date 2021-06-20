package com.wangzs.app_base.module_base.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wangzs.app_base.base.android.BaseActivity
import com.wangzs.app_base.module_base.widget.TitleBar
import com.wangzs.app_base.toast.BaseToast

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
abstract class AppBaseActivity : BaseActivity(), IBaseView {
    protected lateinit var mContext: Context
    private lateinit var mContentView: View
    protected lateinit var mTitleView: TitleBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
        initView(savedInstanceState, mContentView)
        if (isShowTitle()) {

            mTitleView = TitleBar(mContext);
//            mTitleView
            // 关于这里的解释，去查看setContentView的源码解析.txt
            val viewGroup = window.decorView as ViewGroup
            val parentView = viewGroup.getChildAt(0) as ViewGroup
            mTitleView.setTitleText(getTitleStr())
            mTitleView.setLayoutParams(
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            parentView.addView(mTitleView, 0)
        }
        initEvent()
        initData()
    }

    fun showToast(msg: String?) {
        BaseToast.show(msg)
    }


    protected open fun isShowTitle(): Boolean {
        return false
    }

    /**
     * 标题栏文字
     */
    protected open fun getTitleStr(): String? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
