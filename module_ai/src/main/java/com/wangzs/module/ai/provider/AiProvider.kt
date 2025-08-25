package com.wangzs.module.ai.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wangzs.lib.base.module.constons.ARouteConstants
import com.wangzs.lib.base.module.provider.IAiProvider
import com.wangzs.module.ai.fragment.AiFragment

@Route(path = ARouteConstants.Ai.AI_MAIN, name = "Ai服务")
class AiProvider: IAiProvider {
    override val aiFragment: Fragment
        get() = AiFragment.newsInstance()
    override val titleStr: String
        get() = "Stream"

    override fun init(context: Context?) {

    }

}