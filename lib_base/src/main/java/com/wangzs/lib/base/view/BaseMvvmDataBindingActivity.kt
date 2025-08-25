package com.wangzs.lib.base.view

import android.view.ViewStub
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.wangzs.lib.base.mvvm.viewmodel.BaseViewModel
import com.wangzs.lib.base.utils.ReflectUtils
import com.wangzs.lib.base.view.databinding.ActivityBindingHolder
import com.wangzs.lib.base.view.databinding.ActivityViewDataBindingHolder

/**
 * Describe:
 * 基础 DataBinding 页面
 *
 * @author wangzs
 * @Date 2020/12/17
 */
abstract class BaseMvvmDataBindingActivity<V : ViewDataBinding, VM : BaseViewModel> :
    BaseMvvmActivity<VM>(), ActivityBindingHolder<V> by ActivityViewDataBindingHolder() {

    override fun initContentView(mViewStubContent: ViewStub) {
        with(mViewStubContent) {
            layoutResource = onBindLayout()
            inflateBinding(viewStub = this, onClear = { binding -> binding.onClear() }) { binding ->
                binding.lifecycleOwner = this@BaseMvvmDataBindingActivity
                onBindVariableId().forEach { pair ->
                    binding.setVariable(pair.first, pair.second)
                }
            }
        }
    }

    override fun onBindViewModel(): Class<VM> {
        return ReflectUtils.getActualTypeArgument(ViewModel::class.java, this.javaClass) as? Class<VM>
            ?: throw IllegalArgumentException("找不到 ViewModelClass 实例，建议重写该方法")
    }

    abstract fun onBindVariableId(): MutableList<Pair<Int, Any>>

    abstract fun V.onClear()
}
