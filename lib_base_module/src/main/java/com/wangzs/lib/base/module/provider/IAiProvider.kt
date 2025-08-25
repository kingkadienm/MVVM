package com.wangzs.lib.base.module.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IAiProvider : IProvider {
    val aiFragment: Fragment

    val titleStr:String
}