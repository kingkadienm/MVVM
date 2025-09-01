package com.wangzs.module.login.fragment

import android.view.View
import com.wangzs.lib.base.view.BaseMvvmDataBindingFragment
import com.wangzs.module.login.BR
import com.wangzs.module.login.R
import com.wangzs.module.login.databinding.ModuleFragmentBinding
import com.wangzs.module.login.viewmodel.ModuleViewModel

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