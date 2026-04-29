package com.wangzs.lib.base.module.event

/**
 * EventCode
 *
 * @author wangzs
 * @time 2022/1/14
 */
interface EventCode {
    interface MainCode//1000开始

    interface NewsCode//2000开始

    interface CommentCode {//3000开始
        companion object {
            val COMMENT_ADD = 3000
            val COMMENT_DELETE = 3001
            val COMMENT_UPDATE = 3002
            val COMMENT_QUERY = 3003
            val COMMENT_LIKE = 3004
            val COMMENT_UNLIKE = 3005
            val COMMENT_REPLY = 3006
            val COMMENT_EXPAND = 3007
            val COMMENT_COLLAPSE = 3008
        }
    }

    interface FindCode//4000开始

    interface MeCode {
        companion object {
            //5000开始
            val NEWS_TYPE_ADD = 5000
            val NEWS_TYPE_DELETE = 5001
            val NEWS_TYPE_UPDATE = 5002
            val NEWS_TYPE_QUERY = 5003

            val news_detail_add = 5004
        }
    }
}
