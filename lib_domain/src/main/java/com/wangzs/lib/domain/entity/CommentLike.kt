package com.wangzs.lib.domain.entity

import com.wangzs.lib.domain.base.BaseBean

/**
 * 评论点赞实体类
 *
 * @author wangzs
 * @Date 2024/12/25
 */
data class CommentLike(
    val id: String?,
    val commentId: String?,
    val userId: String?,
    val isLiked: Boolean = false,
    val createTime: String?
) : BaseBean()