package com.wangzs.lib.base.view.databinding

import androidx.databinding.ViewDataBinding
import com.wangzs.lib.base.view.viewbinding.IViewBindingHolder

/**
 *
 *
 * @author wangzs
 * @time 2022/1/13
 */
internal class ViewDataBindingHolder<VB : ViewDataBinding> : IViewBindingHolder.Holder<VB>() {

    override fun clearBinding(clear: VB.() -> Unit) {
        super.clearBinding {
            clear()
            unbind()
        }
    }
}