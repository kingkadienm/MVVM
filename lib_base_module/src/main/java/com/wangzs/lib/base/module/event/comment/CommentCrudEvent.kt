package com.wangzs.lib.base.module.event.comment

import com.wangzs.lib.base.event.BaseEvent

/**
 * 评论CRUD事件
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentCrudEvent<T>(code: Int) : BaseEvent<T>(code)