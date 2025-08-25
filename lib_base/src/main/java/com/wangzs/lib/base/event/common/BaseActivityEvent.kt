package com.wangzs.lib.base.event.common

import com.wangzs.lib.base.event.BaseEvent

/**
 * 基于事件
 *
 * @author wangzs
 * @Data
 */
open class BaseActivityEvent<T>(code: Int) : BaseEvent<T>(code)
