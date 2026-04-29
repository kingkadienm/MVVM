package com.wangzs.lib.base.module.event.comment

import com.wangzs.lib.base.event.BaseEvent

/**
 * 评论展开/折叠事件
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentExpandEvent<T>(code: Int) : BaseEvent<T>(code)