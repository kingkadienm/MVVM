package com.wangzs.module.ai.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.wangzs.lib.base.view.BaseMvvmDataBindingFragment
import com.wangzs.lib.base.view.BaseMvvmViewBindingFragment
import com.wangzs.lib.net.utils.ext.observe
import com.wangzs.module.ai.BR
import com.wangzs.module.ai.databinding.FragmentAiBinding
import com.wangzs.module.ai.viewmodel.AiViewModel
import com.wangzs.module.ai.R

class AiFragment : BaseMvvmDataBindingFragment<FragmentAiBinding, AiViewModel>() {
    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewmodel to mViewModel)
    }

    companion object {
        fun newsInstance(): AiFragment {
            return AiFragment()
        }
    }

    override fun FragmentAiBinding.onClear() {

    }

    override fun initViewObservable() {
        // observe(mViewModel.recipesLiveData, ::handleRecipesList)

    }

    override fun onBindLayout(): Int = R.layout.fragment_ai

    override fun initView(mView: View) {


    }

    override fun initData() {

    }

}