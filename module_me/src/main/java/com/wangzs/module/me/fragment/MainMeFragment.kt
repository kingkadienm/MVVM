package com.wangzs.module.me.fragment

import android.view.View
import com.google.gson.Gson
import com.wangzs.lib.base.utils.ToastUtils
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.base.view.BaseMvvmViewBindingFragment
import com.wangzs.lib.log.KLog
import com.wangzs.module.me.R
import com.wangzs.module.me.activity.MoreRequestServerActivity
import com.wangzs.module.me.activity.RoomTestActivity
import com.wangzs.module.me.activity.SaveStateTestActivity
import com.wangzs.module.me.databinding.FragmentMeMainBinding
import com.wangzs.module.me.viewmodel.MainMeViewModel

/**
 * Describe:
 * 首页，使用了ViewBinding示例
 *
 * @author wangzs
 * @Date 2020/12/3
 */
class MainMeFragment : BaseMvvmViewBindingFragment<FragmentMeMainBinding, MainMeViewModel>() {

    companion object {
        fun newsInstance(): MainMeFragment {
            return MainMeFragment()
        }
    }



    override fun onBindLayout(): Int = R.layout.fragment_me_main

    override fun initView(mView: View) {

    }

    override fun initData() {

    }

    override fun initListener() {
        requireBinding().button1.setOnClickListener(this::onClick)
        requireBinding().button2.setOnClickListener(this::onClick)
        requireBinding().button3.setOnClickListener(this::onClick)
        requireBinding().button4.setOnClickListener(this::onClick)
        requireBinding().button5.setOnClickListener(this::onClick)
    }

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = getString(R.string.title_mine)

    override fun onClick(v: View?) {
        if (beFastClick()) {
            return
        }
        when (v?.id) {
            R.id.button_1 -> {

            }
            R.id.button_2 -> {

            }
            R.id.button_3 -> {
                val trim = requireBinding().editText.text.toString().trim()
                if (trim.isBlank()) {
                    ToastUtils.showToastCenter("输入内容不能为空")
                    return
                }
                SaveStateTestActivity.start(mContext, trim)
            }
            R.id.button_4 -> {
                RoomTestActivity.start(mContext)
            }
            R.id.button_5 -> {
                MoreRequestServerActivity.start(mContext)
            }
        }
    }

    override fun FragmentMeMainBinding.onClear() {

    }

    override fun initViewObservable() {

    }
}
