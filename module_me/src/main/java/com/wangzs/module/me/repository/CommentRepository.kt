package com.wangzs.module.me.repository

import com.wangzs.lib.domain.entity.Comment
import com.wangzs.lib.domain.entity.CommentLike
import com.wangzs.lib.domain.entity.CommentRequest
import com.wangzs.lib.domain.entity.CommentListRequest
import com.wangzs.lib.domain.entity.CommentLikeRequest
import com.wangzs.lib.net.dto.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

/**
 * 评论Repository
 * 负责处理评论相关的数据请求
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentRepository private constructor() {

    companion object {
        @Volatile
        private var instance: CommentRepository? = null

        fun getInstance(): CommentRepository {
            return instance ?: synchronized(this) {
                instance ?: CommentRepository().also { instance = it }
            }
        }
    }

    /**
     * 获取评论列表
     */
    suspend fun getCommentList(request: CommentListRequest): Flow<Resource<List<Comment>>> {
        return flow {
            emit(Resource.Loading())
            delay(1000) // 模拟网络请求
            
            try {
                // 模拟评论数据
                val comments = generateMockComments(request.targetId)
                emit(Resource.Success(comments))
            } catch (e: Exception) {
                emit(Resource.Error("获取评论列表失败: ${e.message}"))
            }
        }
    }

    /**
     * 发表评论
     */
    suspend fun addComment(request: CommentRequest): Flow<Resource<Comment>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            
            try {
                val newComment = Comment(
                    id = "comment_${System.currentTimeMillis()}",
                    content = request.content,
                    userId = "current_user_id",
                    userName = "当前用户",
                    userAvatar = "https://via.placeholder.com/40",
                    createTime = "刚刚",
                    likeCount = 0,
                    isLiked = false,
                    parentId = request.parentId,
                    replyToUserId = request.replyToUserId
                )
                emit(Resource.Success(newComment))
            } catch (e: Exception) {
                emit(Resource.Error("发表评论失败: ${e.message}"))
            }
        }
    }

    /**
     * 删除评论
     */
    suspend fun deleteComment(commentId: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            
            try {
                // 模拟删除操作
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("删除评论失败: ${e.message}"))
            }
        }
    }

    /**
     * 点赞/取消点赞评论
     */
    suspend fun likeComment(request: CommentLikeRequest): Flow<Resource<CommentLike>> {
        return flow {
            emit(Resource.Loading())
            delay(300)
            
            try {
                val commentLike = CommentLike(
                    id = "like_${System.currentTimeMillis()}",
                    commentId = request.commentId,
                    userId = "current_user_id",
                    isLiked = request.isLike,
                    createTime = "刚刚"
                )
                emit(Resource.Success(commentLike))
            } catch (e: Exception) {
                emit(Resource.Error("操作失败: ${e.message}"))
            }
        }
    }

    /**
     * 获取子评论列表
     */
    suspend fun getReplies(parentCommentId: String, page: Int = 1): Flow<Resource<List<Comment>>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            
            try {
                val replies = generateMockReplies(parentCommentId)
                emit(Resource.Success(replies))
            } catch (e: Exception) {
                emit(Resource.Error("获取回复失败: ${e.message}"))
            }
        }
    }

    /**
     * 生成模拟评论数据
     */
    private fun generateMockComments(targetId: String): List<Comment> {
        val comments = mutableListOf<Comment>()
        
        for (i in 1..10) {
            val comment = Comment(
                id = "comment_$i",
                content = "这是第${i}条评论内容，用于测试评论功能的展示效果。",
                userId = "user_$i",
                userName = "用户$i",
                userAvatar = "https://via.placeholder.com/40",
                createTime = "${i}分钟前",
                likeCount = (0..50).random(),
                isLiked = (i % 3 == 0),
                totalReplies = if (i <= 3) (2..8).random() else 0
            )
            
            // 为前三条评论添加回复
            if (i <= 3) {
                comment.replies.addAll(generateMockReplies(comment.id!!))
                comment.hasMoreReplies = comment.totalReplies > comment.replies.size
            }
            
            comments.add(comment)
        }
        
        return comments
    }

    /**
     * 生成模拟回复数据
     */
    private fun generateMockReplies(parentCommentId: String): List<Comment> {
        val replies = mutableListOf<Comment>()
        val replyCount = (2..5).random()
        
        for (i in 1..replyCount) {
            val reply = Comment(
                id = "${parentCommentId}_reply_$i",
                content = "这是对评论的第${i}个回复。",
                userId = "reply_user_$i",
                userName = "回复用户$i",
                userAvatar = "https://via.placeholder.com/40",
                createTime = "${i * 2}分钟前",
                likeCount = (0..20).random(),
                isLiked = (i % 4 == 0),
                parentId = parentCommentId,
                replyToUserId = if (i > 1) "reply_user_${i-1}" else null,
                replyToUserName = if (i > 1) "回复用户${i-1}" else null
            )
            replies.add(reply)
        }
        
        return replies
    }
}