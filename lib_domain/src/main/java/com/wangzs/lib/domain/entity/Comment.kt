package com.wangzs.lib.domain.entity

import com.wangzs.lib.domain.base.BaseBean

/**
 * 评论实体类
 * 支持一级评论和二级评论
 *
 * @author wangzs
 * @Date 2024/12/25
 */
data class Comment(
    val id: String?,
    val content: String?,
    val userId: String?,
    val userName: String?,
    val userAvatar: String?,
    val createTime: String?,
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val parentId: String? = null, // 父评论ID，null表示一级评论
    val replyToUserId: String? = null, // 回复的用户ID
    val replyToUserName: String? = null, // 回复的用户名
    val replies: MutableList<Comment> = mutableListOf(), // 子评论列表
    val isExpanded: Boolean = false, // 是否展开显示所有回复
    var hasMoreReplies: Boolean = false, // 是否还有更多回复
    val totalReplies: Int = 0 // 总回复数
) : BaseBean()