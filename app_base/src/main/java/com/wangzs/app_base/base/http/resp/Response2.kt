package com.wangzs.app_base.base.http.resp

import com.wangzs.app_base.module_base.model.Model

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class Response2<T>(
    var isOk: Boolean = false,
    var msg: String? = null,
    var data: T? = null
) : Model()