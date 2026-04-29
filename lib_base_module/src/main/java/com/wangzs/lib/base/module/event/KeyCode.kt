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

    interface Comment {
        companion object {
            val COMMENT_ID = "commentid"
            val PARENT_COMMENT_ID = "parentcommentid"
            val TARGET_ID = "targetid"
            val COMMENT_CONTENT = "commentcontent"
            val REPLY_TO_USER_ID = "replytouserid"
        }
    }

    interface Find

    interface Me
}
