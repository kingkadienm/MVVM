package com.wangzs.module.me.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wangzs.lib.base.module.constons.ARouteConstants
import com.wangzs.lib.base.module.event.KeyCode
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.base.view.BaseMvvmRefreshDataBindingActivity
import com.wangzs.lib.net.utils.ext.launch
import com.wangzs.module.me.BR
import com.wangzs.module.me.R
import com.wangzs.module.me.adapter.CommentAdapter
import com.wangzs.module.me.databinding.ActivityCommentBinding
import com.wangzs.module.me.viewmodel.CommentViewModel
import kotlinx.coroutines.launch

/**
 * 评论Activity
 * 支持一级评论、二级评论、点赞、折叠展示功能
 *
 * @author wangzs
 * @Date 2024/12/25
 */
@Route(path = ARouteConstants.Me.ME_COMMENT, name = "评论页面")
class CommentActivity : BaseMvvmRefreshDataBindingActivity<ActivityCommentBinding, CommentViewModel>() {

    companion object {
        fun start(context: Context, targetId: String, title: String = "评论") {
            val intent = Intent(context, CommentActivity::class.java).apply {
                putExtra(KeyCode.Comment.TARGET_ID, targetId)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var mAdapter: CommentAdapter
    private var targetId: String = ""
    private lateinit var etComment: EditText
    private lateinit var tvSend: TextView
    private lateinit var commentInputLayout: View

    override fun getTootBarTitle(): String = intent.getStringExtra("title") ?: "评论"

    override fun enableToolBarLeft(): Boolean = true

    override fun onBindRefreshLayout(): Int = R.id.refreshLayout

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewModel to mViewModel)
    }

    override fun ActivityCommentBinding.onClear() {
        // 清理资源
    }

    override fun initBundle() {
        super.initBundle()
        targetId = intent.getStringExtra(KeyCode.Comment.TARGET_ID) ?: ""
    }

    override fun initViewObservable() {
        // 观察评论列表数据
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.commentsFlow.collect { resource ->
                    resource.launch(
                        success = { comments ->
                            comments?.let {
                                if (isRefresh) {
                                    mAdapter.setNewInstance(ArrayList(it))
                                } else {
                                    mAdapter.addData(ArrayList(it))
                                }
                            }
                        },
                        fail = { message ->
                            message?.showToast()
                        }
                    )
                }
            }
        }

        // 观察发表评论结果
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.addCommentFlow.collect { resource ->
                    resource?.launch(
                        success = {
                            "评论发表成功".showToast()
                            etComment.setText("")
                        },
                        fail = { message ->
                            message?.showToast()
                        }
                    )
                }
            }
        }

        // 观察点赞结果
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.likeFlow.collect { resource ->
                    resource?.launch(
                        success = { commentLike ->
                            val message = if (commentLike?.isLiked == true) "点赞成功" else "取消点赞"
                            message.showToast()
                        },
                        fail = { message ->
                            message?.showToast()
                        }
                    )
                }
            }
        }

        // 观察删除评论结果
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.deleteFlow.collect { resource ->
                    resource?.launch(
                        success = {
                            "评论删除成功".showToast()
                        },
                        fail = { message ->
                            message?.showToast()
                        }
                    )
                }
            }
        }

        // 观察输入框显示状态
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.isInputVisible.collect { isVisible ->
                    commentInputLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
                }
            }
        }

        // 观察输入框提示文字
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.inputHint.collect { hint ->
                    etComment.hint = hint
                }
            }
        }
    }

    override fun onBindLayout(): Int = R.layout.activity_comment

    override fun initView() {
        initCommentInput()
        initRecyclerView()
    }

    override fun initData() {
        mViewModel.initComments(targetId)
        onRefreshEvent()
    }

    private fun initCommentInput() {
        commentInputLayout = requireBinding().commentInputLayout
        etComment = requireBinding().etComment
        tvSend = requireBinding().tvSend

        // 发送按钮点击事件
        tvSend.setOnClickListener {
            val content = etComment.text.toString().trim()
            if (content.isNotEmpty()) {
                val currentReply = mViewModel.currentReplyComment.value
                if (currentReply != null) {
                    // 回复评论
                    mViewModel.addComment(
                        content = content,
                        parentId = currentReply.parentId ?: currentReply.id,
                        replyToUserId = currentReply.userId
                    )
                } else {
                    // 发表评论
                    mViewModel.addComment(content)
                }
            } else {
                "请输入评论内容".showToast()
            }
        }

        // 监听输入变化，控制发送按钮状态
        etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvSend.isEnabled = !s.isNullOrEmpty()
                tvSend.alpha = if (s.isNullOrEmpty()) 0.5f else 1.0f
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 默认隐藏输入框
        commentInputLayout.visibility = View.GONE
    }

    private fun initRecyclerView() {
        mAdapter = CommentAdapter().apply {
            // 设置点击事件监听
            setOnCommentClickListener(object : CommentAdapter.OnCommentClickListener {
                override fun onLikeClick(commentId: String, isLiked: Boolean) {
                    mViewModel.likeComment(commentId, !isLiked)
                }

                override fun onReplyClick(comment: com.wangzs.lib.domain.entity.Comment) {
                    mViewModel.showCommentInput(comment)
                }

                override fun onExpandClick(commentId: String) {
                    mViewModel.toggleCommentExpand(commentId)
                }

                override fun onDeleteClick(commentId: String) {
                    mViewModel.deleteComment(commentId)
                }
            })
        }

        requireBinding().recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CommentActivity)
            adapter = mAdapter
        }
    }

    override fun onRefreshEvent() {
        mViewModel.refreshData()
    }

    override fun onLoadMoreEvent() {
        mViewModel.loadMore()
    }

    override fun getToolBarRightTxt(): String = "写评论"

    override fun getToolBarRightTxtClick(): View.OnClickListener {
        return View.OnClickListener {
            mViewModel.showCommentInput()
        }
    }

    override fun onBackPressed() {
        if (mViewModel.isInputVisible.value) {
            mViewModel.hideCommentInput()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.clearStates()
    }
}