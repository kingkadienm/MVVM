package com.wangzs.module.me.activity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wangzs.lib.base.module.constons.ARouteConstants
import com.wangzs.lib.base.view.BaseActivity
import com.wangzs.module.me.R
import com.wangzs.module.me.adapter.CommentTestAdapter

/**
 * 评论功能测试页面
 * 用于演示评论功能
 *
 * @author wangzs
 * @Date 2024/12/25
 */
@Route(path = ARouteConstants.Me.ME_COMMENT_TEST, name = "评论功能测试")
class CommentTestActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            ARouter.getInstance().build(ARouteConstants.Me.ME_COMMENT_TEST)
                .navigation(context)
        }
    }

    override fun getTootBarTitle(): String = "评论功能演示"

    override fun enableToolBarLeft(): Boolean = true

    override fun onBindLayout(): Int = R.layout.activity_comment_test

    override fun initView() {
        // 设置测试数据
        val testItems = listOf(
            TestItem("新闻文章", "这是一篇新闻文章，点击查看评论", "news_1"),
            TestItem("视频内容", "这是一个视频，支持评论功能", "video_1"),
            TestItem("图片动态", "这是一张图片动态，欢迎评论", "image_1"),
            TestItem("博客文章", "这是一篇技术博客，欢迎讨论", "blog_1")
        )

        val adapter = CommentTestAdapter { testItem ->
            // 点击进入评论页面
            CommentActivity.start(this, testItem.id, "${testItem.title} - 评论")
        }

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setNewInstance(ArrayList(testItems))
    }

    override fun initData() {
        // 初始化数据
    }

    data class TestItem(
        val title: String,
        val content: String,
        val id: String
    )
}