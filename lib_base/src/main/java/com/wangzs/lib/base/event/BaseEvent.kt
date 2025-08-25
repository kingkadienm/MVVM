package com.wangzs.lib.base.event

/**
 * BaseEvent
 *
 * @author wangzs
 * @Data 
 */
open class BaseEvent<T> {
    var code: Int = 0
    var data: T? = null

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }
}
