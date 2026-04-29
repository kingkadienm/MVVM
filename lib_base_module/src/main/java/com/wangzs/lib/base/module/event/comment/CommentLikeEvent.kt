package com.wangzs.lib.base.module.event.comment

import com.wangzs.lib.base.event.BaseEvent

/**
 * 评论点赞事件
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentLikeEvent<T>(code: Int) : BaseEvent<T>(code)