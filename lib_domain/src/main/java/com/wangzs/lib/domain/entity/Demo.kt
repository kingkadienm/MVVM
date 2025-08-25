package com.wangzs.lib.domain.entity

import com.wangzs.lib.domain.base.BaseBean


/**
 * Describe:
 * <p></p>
 *
 * @author wangzs
 * @Date 2020/12/1
 */
data class Demo(
    val calories: String?,
    val carbos: String?,
    val description: String?,
    val difficulty: Int?,
    val fats: String?,
    val headline: String?,
    val id: String?,
    val image: String?,
    val name: String?,
    val proteins: String?,
    val thumb: String?,
    val time: String?
) : BaseBean()
