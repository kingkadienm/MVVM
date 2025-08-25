package com.wangzs.module.home.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wangzs.lib.base.module.constons.ARouteConstants
import com.wangzs.lib.base.module.provider.IHomeProvider
import com.wangzs.module.home.fragment.MainHomeFragment

/**
 * Describe:
 * 首页服务
 *
 * @author wangzs
 * @Date 2020/12/3
 */
@Route(path = ARouteConstants.Main.HOME_MAIN, name = "首页服务")
class HomeProvider : IHomeProvider {
    override val mainHomeFragment: Fragment
        get() = MainHomeFragment.newsInstance()

    override fun init(context: Context?) {

    }
}