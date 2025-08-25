package com.wangzs.lib.base.view

import android.view.ViewStub
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.wangzs.lib.base.mvvm.viewmodel.BaseViewModel
import com.wangzs.lib.base.utils.ReflectUtils
import com.wangzs.lib.base.view.viewbinding.ActivityViewBinding
import com.wangzs.lib.base.view.viewbinding.ActivityViewBindingHolder

/**
 * 基础 ViewBinding 页面
 *
 * @author wangzs
 * @time 2022/3/17
 */
abstract class BaseMvvmViewBindingActivity<V : ViewBinding, VM : BaseViewModel> :
    BaseMvvmActivity<VM>(), ActivityViewBinding<V> by ActivityViewBindingHolder() {

    override fun initContentView(mViewStubContent: ViewStub) {
        with(mViewStubContent) {
            layoutResource = onBindLayout()
            inflateBinding(
                viewStub = this,
                bindingClass = onBindingClass(),
                onClear = { binding -> binding.onClear() }
            )
        }
    }

    override fun onBindViewModel(): Class<VM> {
        return ReflectUtils.getActualTypeArgument(ViewModel::class.java, this.javaClass) as? Class<VM>
            ?: throw IllegalArgumentException("找不到 ViewModelClass 实例，建议重写该方法")
    }

    open fun onBindingClass(): Class<V> {
        return ReflectUtils.getActualTypeArgument(ViewBinding::class.java, this.javaClass) as? Class<V>
            ?: throw IllegalArgumentException("找不到 BindingClass 实例，建议重写该方法")
    }

    abstract fun V.onClear()
}
