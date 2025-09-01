package com.wangzs.module.login.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.wangzs.lib.base.module.provider.IAiProvider
import com.wangzs.module.login.fragment.ModuleFragment

//@Route(path = ARouteConstants, name = "Ai服务")
class ModuleProvider: IAiProvider {
    override val aiFragment: Fragment
        get() = ModuleFragment.newsInstance()
    override val titleStr: String
        get() = "Stream"

    override fun init(context: Context?) {

    }

}