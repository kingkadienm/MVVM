package com.wangzs.module.template.fragment

import android.view.View
import com.wangzs.lib.base.view.BaseMvvmDataBindingFragment
import com.wangzs.module.template.BR
import com.wangzs.module.template.R
import com.wangzs.module.template.databinding.ModuleFragmentBinding
import com.wangzs.module.template.viewmodel.ModuleViewModel

class ModuleFragment : BaseMvvmDataBindingFragment<ModuleFragmentBinding, ModuleViewModel>() {
    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewModule to mViewModel)
    }

    companion object {
        fun newsInstance(): ModuleFragment {
            return ModuleFragment()
        }
    }

    override fun ModuleFragmentBinding.onClear() {

    }

    override fun initViewObservable() {


    }

    override fun onBindLayout(): Int = R.layout.module_fragment

    override fun initView(mView: View) {


    }

    override fun initData() {

    }

}