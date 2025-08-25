package com.wangzs.lib.net.error.mapper

import com.wangzs.lib.net.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
