package com.wangzs.lib.base.utils.ext.view

import com.wangzs.lib.utils.ToastUtils

/**
 * Describe:
 * <p></p>
 *
 * @author wangzs
 * @Date 2020/12/7
 */


/**
 * 使用方式
 * "This is Toast".showToast(context,Toast.LENGTH_SHORT)
 */
fun String.showToast() {
    ToastUtils.showShort(this)
}

/**
 * 使用方式
 * "This is Toast".showToast(context,Toast.LENGTH_SHORT)
 */
fun Int.showToast() {
    ToastUtils.showShort(this)
}


