package com.wangzs.lib.base.module.event

/**
 * RequestCode
 *
 * @author wangzs
 * @time 2022/1/14
 */
interface RequestCode {
    interface Main//1000开始

    interface News//2000开始

    interface Comment {//3000开始
        companion object {
            val COMMENT_ADD = 3000
            val COMMENT_EDIT = 3001
            val COMMENT_DELETE = 3002
        }
    }

    interface Find//4000开始

    interface Me {
        companion object {
            //5000开始
            val NEWS_TYPE_ADD = 5000
        }
    }
}
