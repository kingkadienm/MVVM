package com.wangzs.app_base.flutter

import com.wangzs.app_base.module_base.model.Model


/**
 * Created by xiedongdong on 2020/08/25
 */
internal class InitialRoute<T>(
    var route: String,
    var data: T
) : Model()