package com.wangzs.module.me.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wangzs.lib.base.adapter.BaseSkeletonAdapter
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.module.me.R

/**
 *
 *
 * @author wangzs
 * @since 2024/3/15 14:19
 */
class MoreRequestDemoAdapter : BaseSkeletonAdapter<Demo, BaseViewHolder>(R.layout.item_user) {
    override fun convert(holder: BaseViewHolder, item: Demo) {
        holder.setText(R.id.tv_name, item.name)
            .setText(R.id.tv_caption, item.description)
        Glide.with(context)
            .load(item.thumb)
            .placeholder(R.drawable.loading_anim)
            .error(R.drawable.loading_bg)
            .into(holder.getView(R.id.iv_recipe_item_image))

        holder.getView<View>(R.id.rl_recipe_item).setOnClickListener {
            "您点击了${holder.absoluteAdapterPosition}".showToast()
        }
    }
}