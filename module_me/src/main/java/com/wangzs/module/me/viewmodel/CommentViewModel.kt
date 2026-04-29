package com.wangzs.module.me.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wangzs.lib.base.mvvm.viewmodel.BaseRefreshViewModel
import com.wangzs.lib.domain.entity.Comment
import com.wangzs.lib.domain.entity.CommentLike
import com.wangzs.lib.domain.entity.CommentRequest
import com.wangzs.lib.domain.entity.CommentListRequest
import com.wangzs.lib.domain.entity.CommentLikeRequest
import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.me.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 评论ViewModel
 * 处理评论相关的业务逻辑
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentViewModel(private val state: SavedStateHandle) : BaseRefreshViewModel() {
    
    private val commentRepository = CommentRepository.getInstance()
    
    // 评论列表
    private val _commentsFlow = MutableStateFlow<Resource<List<Comment>>>(Resource.Loading())
    val commentsFlow: StateFlow<Resource<List<Comment>>> = _commentsFlow.asStateFlow()
    
    // 发表评论结果
    private val _addCommentFlow = MutableStateFlow<Resource<Comment>?>(null)
    val addCommentFlow: StateFlow<Resource<Comment>?> = _addCommentFlow.asStateFlow()
    
    // 点赞结果
    private val _likeFlow = MutableStateFlow<Resource<CommentLike>?>(null)
    val likeFlow: StateFlow<Resource<CommentLike>?> = _likeFlow.asStateFlow()
    
    // 删除评论结果
    private val _deleteFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val deleteFlow: StateFlow<Resource<Boolean>?> = _deleteFlow.asStateFlow()
    
    // 子评论列表
    private val _repliesFlow = MutableStateFlow<Resource<List<Comment>>?>(null)
    val repliesFlow: StateFlow<Resource<List<Comment>>?> = _repliesFlow.asStateFlow()
    
    // 当前评论列表数据
    private val commentsList = mutableListOf<Comment>()
    private var currentPage = 1
    private var targetId = ""
    
    // 当前正在输入回复的评论
    private val _currentReplyComment = MutableStateFlow<Comment?>(null)
    val currentReplyComment: StateFlow<Comment?> = _currentReplyComment.asStateFlow()
    
    // 评论输入框相关状态
    private val _isInputVisible = MutableStateFlow(false)
    val isInputVisible: StateFlow<Boolean> = _isInputVisible.asStateFlow()
    
    private val _inputHint = MutableStateFlow("写评论...")
    val inputHint: StateFlow<String> = _inputHint.asStateFlow()

    /**
     * 初始化评论列表
     */
    fun initComments(targetId: String) {
        this.targetId = targetId
        refreshData()
    }

    override fun refreshData() {
        currentPage = 1
        getCommentList(isRefresh = true)
    }

    override fun loadMore() {
        currentPage++
        getCommentList(isRefresh = false)
    }

    /**
     * 获取评论列表
     */
    private fun getCommentList(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val request = CommentListRequest(
                targetId = targetId,
                page = currentPage,
                pageSize = 20
            )
            
            commentRepository.getCommentList(request).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        if (isRefresh) {
                            _commentsFlow.value = Resource.Loading()
                        }
                    }
                    is Resource.Success -> {
                        val newComments = resource.data ?: emptyList()
                        if (isRefresh) {
                            commentsList.clear()
                            commentsList.addAll(newComments)
                            postStopRefreshEvent(true)
                        } else {
                            commentsList.addAll(newComments)
                            if (newComments.isEmpty()) {
                                postStopLoadMoreWithNoMoreDataEvent()
                            } else {
                                postStopLoadMoreEvent(true)
                            }
                        }
                        _commentsFlow.value = Resource.Success(commentsList.toList())
                    }
                    is Resource.DataError -> {
                        if (isRefresh) {
                            postStopRefreshEvent(false)
                        } else {
                            postStopLoadMoreEvent(false)
                        }
                        _commentsFlow.value = resource
                    }
                    is Resource.Error -> {
                        if (isRefresh) {
                            postStopRefreshEvent(false)
                        } else {
                            postStopLoadMoreEvent(false)
                        }
                        _commentsFlow.value = Resource.Error("resource")
                    }
                }
            }
        }
    }

    /**
     * 发表评论
     */
    fun addComment(content: String, parentId: String? = null, replyToUserId: String? = null) {
        if (content.isBlank()) return
        
        viewModelScope.launch {
            val request = CommentRequest(
                content = content,
                parentId = parentId,
                replyToUserId = replyToUserId,
                targetId = targetId
            )
            
            commentRepository.addComment(request).collect { resource ->
                _addCommentFlow.value = resource
                
                if (resource is Resource.Success) {
                    val newComment = resource.data
                    if (newComment != null) {
                        if (parentId == null) {
                            // 一级评论，直接添加到列表顶部
                            commentsList.add(0, newComment)
                        } else {
                            // 二级评论，添加到对应父评论的回复列表中
                            addReplyToParent(parentId, newComment)
                        }
                        _commentsFlow.value = Resource.Success(commentsList.toList())
                        
                        // 清空输入状态
                        hideCommentInput()
                    }
                }
            }
        }
    }

    /**
     * 点赞/取消点赞评论
     */
    fun likeComment(commentId: String, isLike: Boolean) {
        viewModelScope.launch {
            val request = CommentLikeRequest(commentId, isLike)
            
            commentRepository.likeComment(request).collect { resource ->
                _likeFlow.value = resource
                
                if (resource is Resource.Success) {
                    // 更新本地评论的点赞状态
                    updateCommentLikeStatus(commentId, isLike)
                }
            }
        }
    }

    /**
     * 删除评论
     */
    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId).collect { resource ->
                _deleteFlow.value = resource
                
                if (resource is Resource.Success && resource.data == true) {
                    // 从本地列表中移除评论
                    removeCommentFromList(commentId)
                    _commentsFlow.value = Resource.Success(commentsList.toList())
                }
            }
        }
    }

    /**
     * 展开/折叠评论回复
     */
    fun toggleCommentExpand(commentId: String) {
        val comment = findCommentById(commentId)
        if (comment != null) {
            if (comment.isExpanded) {
                // 折叠
                comment.replies.clear()
                updateCommentInList(comment.copy(isExpanded = false))
            } else {
                // 展开，加载回复
                loadReplies(commentId)
            }
        }
    }

    /**
     * 加载子评论
     */
    private fun loadReplies(parentCommentId: String) {
        viewModelScope.launch {
            commentRepository.getReplies(parentCommentId).collect { resource ->
                _repliesFlow.value = resource
                
                if (resource is Resource.Success) {
                    val replies = resource.data ?: emptyList()
                    val parentComment = findCommentById(parentCommentId)
                    if (parentComment != null) {
                        parentComment.replies.clear()
                        parentComment.replies.addAll(replies)
                        updateCommentInList(parentComment.copy(isExpanded = true))
                    }
                }
            }
        }
    }

    /**
     * 显示评论输入框
     */
    fun showCommentInput(replyComment: Comment? = null) {
        _currentReplyComment.value = replyComment
        _isInputVisible.value = true
        
        if (replyComment != null) {
            _inputHint.value = "回复 ${replyComment.userName}:"
        } else {
            _inputHint.value = "写评论..."
        }
    }

    /**
     * 隐藏评论输入框
     */
    fun hideCommentInput() {
        _currentReplyComment.value = null
        _isInputVisible.value = false
        _inputHint.value = "写评论..."
    }

    /**
     * 工具方法：将回复添加到父评论
     */
    private fun addReplyToParent(parentId: String, reply: Comment) {
        for (i in commentsList.indices) {
            if (commentsList[i].id == parentId) {
                commentsList[i].replies.add(0, reply)
                commentsList[i] = commentsList[i].copy(
                    isExpanded = true,
                    totalReplies = commentsList[i].totalReplies + 1
                )
                break
            }
        }
    }

    /**
     * 工具方法：更新评论点赞状态
     */
    private fun updateCommentLikeStatus(commentId: String, isLike: Boolean) {
        for (i in commentsList.indices) {
            val comment = commentsList[i]
            if (comment.id == commentId) {
                val newLikeCount = if (isLike) comment.likeCount + 1 else maxOf(0, comment.likeCount - 1)
                commentsList[i] = comment.copy(isLiked = isLike, likeCount = newLikeCount)
                break
            } else {
                // 检查子评论
                for (j in comment.replies.indices) {
                    if (comment.replies[j].id == commentId) {
                        val reply = comment.replies[j]
                        val newLikeCount = if (isLike) reply.likeCount + 1 else maxOf(0, reply.likeCount - 1)
                        comment.replies[j] = reply.copy(isLiked = isLike, likeCount = newLikeCount)
                        break
                    }
                }
            }
        }
        _commentsFlow.value = Resource.Success(commentsList.toList())
    }

    /**
     * 工具方法：从列表中移除评论
     */
    private fun removeCommentFromList(commentId: String) {
        commentsList.removeAll { it.id == commentId }
        // 同时检查子评论
        for (comment in commentsList) {
            comment.replies.removeAll { it.id == commentId }
        }
    }

    /**
     * 工具方法：根据ID查找评论
     */
    private fun findCommentById(commentId: String): Comment? {
        for (comment in commentsList) {
            if (comment.id == commentId) {
                return comment
            }
            for (reply in comment.replies) {
                if (reply.id == commentId) {
                    return reply
                }
            }
        }
        return null
    }

    /**
     * 工具方法：更新列表中的评论
     */
    private fun updateCommentInList(updatedComment: Comment) {
        for (i in commentsList.indices) {
            if (commentsList[i].id == updatedComment.id) {
                commentsList[i] = updatedComment
                break
            }
        }
        _commentsFlow.value = Resource.Success(commentsList.toList())
    }

    /**
     * 清空状态
     */
    fun clearStates() {
        _addCommentFlow.value = null
        _likeFlow.value = null
        _deleteFlow.value = null
        _repliesFlow.value = null
    }
}