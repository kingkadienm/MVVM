package com.wangzs.app_base.base.http.exception

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
class HttpRespException(val httpCode: Int, msg: String?) : RespException(msg ?: "")