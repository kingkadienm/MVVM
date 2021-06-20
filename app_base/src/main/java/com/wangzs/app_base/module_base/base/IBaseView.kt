package com.wangzs.app_base.module_base.base

import android.os.Bundle
import android.view.View

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
interface IBaseView {

    val layoutId: Int

    fun initView(savedInstanceState: Bundle?, view: View?)

    fun initEvent()

    fun initData()

}
