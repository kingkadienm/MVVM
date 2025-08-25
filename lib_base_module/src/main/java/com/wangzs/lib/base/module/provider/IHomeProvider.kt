package com.wangzs.lib.base.module.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Describe:
 * 首页
 *
 * @author wangzs
 * @Date 2020/12/3
 */
interface IHomeProvider : IProvider {
    val mainHomeFragment: Fragment
}