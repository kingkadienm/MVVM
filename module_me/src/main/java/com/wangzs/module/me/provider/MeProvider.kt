package com.wangzs.module.me.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wangzs.lib.base.module.constons.ARouteConstants
import com.wangzs.lib.base.module.provider.IMeProvider
import com.wangzs.module.me.fragment.MainMeFragment

/**
 * Describe:
 * 个人中心服务
 *
 * @author wangzs
 * @Date 2020/12/3
 */
@Route(path = ARouteConstants.Me.ME_MAIN, name = "个人中心服务")
class MeProvider : IMeProvider {
    override val mainMeFragment: Fragment
        get() = MainMeFragment.newsInstance()

    override fun init(context: Context?) {

    }

}