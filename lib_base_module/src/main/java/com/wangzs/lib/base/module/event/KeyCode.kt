package com.wangzs.lib.base.module.event

/**
 * KeyCode
 *
 * @author wangzs
 * @time 2022/1/14
 */
interface KeyCode {
    interface Main

    interface News {
        companion object {
            val NEWS_TYPE = "newstype"
            val NEWS_ID = "newsid"
        }
    }

    interface Find

    interface Me
}
