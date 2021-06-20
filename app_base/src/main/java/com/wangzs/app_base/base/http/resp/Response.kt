package com.wangzs.app_base.base.http.resp

import com.wangzs.app_base.module_base.model.Model


/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class Response<T>(
    var errorCode: Int = 0,
    var errorMsg: String? = null,
    var data: T? = null
) : Model()