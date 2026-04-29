package com.wangzs.lib.domain.entity

import com.wangzs.lib.domain.base.BaseBean

/**
 * 评论请求参数实体类
 *
 * @author wangzs
 * @Date 2024/12/25
 */
data class CommentRequest(
    val content: String,
    val parentId: String? = null, // 父评论ID，用于二级评论
    val replyToUserId: String? = null, // 回复的用户ID
    val targetId: String // 目标ID（文章、视频等）
) : BaseBean()

/**
 * 评论列表请求参数
 */
data class CommentListRequest(
    val targetId: String, // 目标ID
    val page: Int = 1,
    val pageSize: Int = 20,
    val sortType: String = "time" // time: 按时间排序, hot: 按热度排序
) : BaseBean()

/**
 * 评论点赞请求参数
 */
data class CommentLikeRequest(
    val commentId: String,
    val isLike: Boolean // true: 点赞, false: 取消点赞
) : BaseBean()