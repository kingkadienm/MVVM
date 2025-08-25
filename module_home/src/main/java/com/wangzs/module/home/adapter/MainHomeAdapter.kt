package com.wangzs.module.home.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wangzs.lib.base.adapter.BaseSkeletonAdapter
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.module.home.R
import com.wangzs.module.home.databinding.ItemRecipeBinding

/**
 * Describe:
 *
 *
 * @author wangzs
 * @Date 2020/12/17
 */
class MainHomeAdapter :
    BaseSkeletonAdapter<Demo, BaseDataBindingHolder<ItemRecipeBinding>>(R.layout.item_recipe) {
    override fun convert(holder: BaseDataBindingHolder<ItemRecipeBinding>, item: Demo) {
        holder.dataBinding?.apply {
            tvCaption.text = item.description
            tvName.text = item.name
            ivRecipeItemImage.apply {
                Glide.with(context)
                    .load(item.thumb)
                    .placeholder(R.drawable.ic_healthy_food)
                    .error(R.drawable.ic_healthy_food)
                    .into(this)
            }

            rlRecipeItem.setOnClickListener {
                "您点击了${holder.absoluteAdapterPosition}".showToast()
            }
        }
    }
}
