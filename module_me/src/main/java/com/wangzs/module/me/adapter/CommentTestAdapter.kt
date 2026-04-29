package com.wangzs.module.me.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wangzs.module.me.R
import com.wangzs.module.me.activity.CommentTestActivity

/**
 * 评论测试适配器
 *
 * @author wangzs
 * @Date 2024/12/25
 */
class CommentTestAdapter(
    private val onItemClick: (CommentTestActivity.TestItem) -> Unit
) : BaseQuickAdapter<CommentTestActivity.TestItem, BaseViewHolder>(R.layout.item_comment_test) {

    override fun convert(holder: BaseViewHolder, item: CommentTestActivity.TestItem) {
        holder.setText(R.id.tvTitle, item.title)
        holder.setText(R.id.tvContent, item.content)
        
        holder.getView<View>(R.id.layoutItem).setOnClickListener {
            onItemClick(item)
        }
    }
}