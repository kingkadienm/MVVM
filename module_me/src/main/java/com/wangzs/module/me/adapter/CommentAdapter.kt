package com.wangzs.module.me.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wangzs.lib.domain.entity.Comment
import com.wangzs.module.me.R

/**
 * 评论适配器
 * 支持一级评论和二级评论的展示
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_comment) {

    private var onCommentClickListener: OnCommentClickListener? = null

    interface OnCommentClickListener {
        fun onLikeClick(commentId: String, isLiked: Boolean)
        fun onReplyClick(comment: Comment)
        fun onExpandClick(commentId: String)
        fun onDeleteClick(commentId: String)
    }

    fun setOnCommentClickListener(listener: OnCommentClickListener) {
        this.onCommentClickListener = listener
    }

    override fun convert(holder: BaseViewHolder, item: Comment) {
        bindCommentData(holder, item)
        setupClickListeners(holder, item)
    }

    private fun bindCommentData(holder: BaseViewHolder, comment: Comment) {
        // 基本信息
        holder.setText(R.id.tvUserName, comment.userName ?: "匿名用户")
        holder.setText(R.id.tvCreateTime, comment.createTime ?: "")
        holder.setText(R.id.tvContent, comment.content ?: "")
        
        // 用户头像
        val ivAvatar = holder.getView<ImageView>(R.id.ivUserAvatar)
        if (!comment.userAvatar.isNullOrEmpty()) {
            Glide.with(context)
                .load(comment.userAvatar)
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .circleCrop()
                .into(ivAvatar)
        } else {
            ivAvatar.setImageResource(R.drawable.ic_default_avatar)
        }

        // 点赞状态和数量
        val tvLikeCount = holder.getView<TextView>(R.id.tvLikeCount)
        val ivLike = holder.getView<ImageView>(R.id.ivLike)
        
        tvLikeCount.text = comment.likeCount.toString()
        if (comment.isLiked) {
            ivLike.setImageResource(R.drawable.ic_like_filled)
            tvLikeCount.setTextColor(context.getColor(R.color.color_like_active))
        } else {
            ivLike.setImageResource(R.drawable.ic_like_empty)
            tvLikeCount.setTextColor(context.getColor(R.color.color_text_secondary))
        }

        // 回复信息（如果是二级评论）
        val tvReplyTo = holder.getView<TextView>(R.id.tvReplyTo)
        if (!comment.replyToUserName.isNullOrEmpty()) {
            tvReplyTo.visibility = View.VISIBLE
            tvReplyTo.text = "回复 ${comment.replyToUserName}:"
        } else {
            tvReplyTo.visibility = View.GONE
        }

        // 子评论列表
        setupRepliesList(holder, comment)
        
        // 展开/折叠按钮
        setupExpandButton(holder, comment)
    }

    private fun setupRepliesList(holder: BaseViewHolder, comment: Comment) {
        val rvReplies = holder.getView<RecyclerView>(R.id.rvReplies)
        
        if (comment.replies.isNotEmpty() && comment.isExpanded) {
            rvReplies.visibility = View.VISIBLE
            
            if (rvReplies.adapter == null) {
                val replyAdapter = ReplyAdapter()
                replyAdapter.setOnReplyClickListener(object : ReplyAdapter.OnReplyClickListener {
                    override fun onLikeClick(commentId: String, isLiked: Boolean) {
                        onCommentClickListener?.onLikeClick(commentId, isLiked)
                    }

                    override fun onReplyClick(comment: Comment) {
                        onCommentClickListener?.onReplyClick(comment)
                    }

                    override fun onDeleteClick(commentId: String) {
                        onCommentClickListener?.onDeleteClick(commentId)
                    }
                })
                
                rvReplies.layoutManager = LinearLayoutManager(context)
                rvReplies.adapter = replyAdapter
            }
            
            (rvReplies.adapter as ReplyAdapter).setNewInstance(ArrayList(comment.replies))
        } else {
            rvReplies.visibility = View.GONE
        }
    }

    private fun setupExpandButton(holder: BaseViewHolder, comment: Comment) {
        val tvExpandReplies = holder.getView<TextView>(R.id.tvExpandReplies)
        
        if (comment.totalReplies > 0) {
            tvExpandReplies.visibility = View.VISIBLE
            
            if (comment.isExpanded) {
                tvExpandReplies.text = "收起回复"
            } else {
                tvExpandReplies.text = "展开 ${comment.totalReplies} 条回复"
            }
            
            tvExpandReplies.setOnClickListener {
                onCommentClickListener?.onExpandClick(comment.id ?: "")
            }
        } else {
            tvExpandReplies.visibility = View.GONE
        }
    }

    private fun setupClickListeners(holder: BaseViewHolder, comment: Comment) {
        // 点赞点击
        val likeLayout = holder.getView<View>(R.id.layoutLike)
        likeLayout.setOnClickListener {
            onCommentClickListener?.onLikeClick(comment.id ?: "", comment.isLiked)
        }

        // 回复点击
        val replyLayout = holder.getView<View>(R.id.layoutReply)
        replyLayout.setOnClickListener {
            onCommentClickListener?.onReplyClick(comment)
        }

        // 删除点击（可以根据当前用户权限控制显示）
        val deleteLayout = holder.getView<View>(R.id.layoutDelete)
        if (comment.userId == "current_user_id") { // 这里应该判断是否为当前用户
            deleteLayout.visibility = View.VISIBLE
            deleteLayout.setOnClickListener {
                onCommentClickListener?.onDeleteClick(comment.id ?: "")
            }
        } else {
            deleteLayout.visibility = View.GONE
        }
    }
}

/**
 * 子评论适配器
 */
class ReplyAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_reply) {

    private var onReplyClickListener: OnReplyClickListener? = null

    interface OnReplyClickListener {
        fun onLikeClick(commentId: String, isLiked: Boolean)
        fun onReplyClick(comment: Comment)
        fun onDeleteClick(commentId: String)
    }

    fun setOnReplyClickListener(listener: OnReplyClickListener) {
        this.onReplyClickListener = listener
    }

    override fun convert(holder: BaseViewHolder, item: Comment) {
        // 基本信息
        holder.setText(R.id.tvUserName, item.userName ?: "匿名用户")
        holder.setText(R.id.tvCreateTime, item.createTime ?: "")
        holder.setText(R.id.tvContent, item.content ?: "")

        // 用户头像
        val ivAvatar = holder.getView<ImageView>(R.id.ivUserAvatar)
        if (!item.userAvatar.isNullOrEmpty()) {
            Glide.with(context)
                .load(item.userAvatar)
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .circleCrop()
                .into(ivAvatar)
        } else {
            ivAvatar.setImageResource(R.drawable.ic_default_avatar)
        }

        // 回复信息
        val tvReplyTo = holder.getView<TextView>(R.id.tvReplyTo)
        if (!item.replyToUserName.isNullOrEmpty()) {
            tvReplyTo.visibility = View.VISIBLE
            tvReplyTo.text = "回复 ${item.replyToUserName}:"
        } else {
            tvReplyTo.visibility = View.GONE
        }

        // 点赞状态和数量
        val tvLikeCount = holder.getView<TextView>(R.id.tvLikeCount)
        val ivLike = holder.getView<ImageView>(R.id.ivLike)
        
        tvLikeCount.text = item.likeCount.toString()
        if (item.isLiked) {
            ivLike.setImageResource(R.drawable.ic_like_filled)
            tvLikeCount.setTextColor(context.getColor(R.color.color_like_active))
        } else {
            ivLike.setImageResource(R.drawable.ic_like_empty)
            tvLikeCount.setTextColor(context.getColor(R.color.color_text_secondary))
        }

        // 设置点击事件
        setupClickListeners(holder, item)
    }

    private fun setupClickListeners(holder: BaseViewHolder, comment: Comment) {
        // 点赞点击
        val likeLayout = holder.getView<View>(R.id.layoutLike)
        likeLayout.setOnClickListener {
            onReplyClickListener?.onLikeClick(comment.id ?: "", comment.isLiked)
        }

        // 回复点击
        val replyLayout = holder.getView<View>(R.id.layoutReply)
        replyLayout.setOnClickListener {
            onReplyClickListener?.onReplyClick(comment)
        }

        // 删除点击
        val deleteLayout = holder.getView<View>(R.id.layoutDelete)
        if (comment.userId == "current_user_id") {
            deleteLayout.visibility = View.VISIBLE
            deleteLayout.setOnClickListener {
                onReplyClickListener?.onDeleteClick(comment.id ?: "")
            }
        } else {
            deleteLayout.visibility = View.GONE
        }
    }
}