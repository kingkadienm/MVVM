package com.wangzs.lib.base.module.database.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.lang.System
import kotlin.Long

/**
 * 基础实体类，所有 Room 实体都应继承此类
 */

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    open var id: Long = 0

    @ColumnInfo(name = "create_time", defaultValue = "CURRENT_TIMESTAMP")
    open var createTime: Long = System.currentTimeMillis()

    @ColumnInfo(name = "update_time")
    open var updateTime: Long = System.currentTimeMillis()

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    open var isDeleted: Boolean = false

    @ColumnInfo(name = "version")
    open var version: Int = 0

    // 审计字段
    @ColumnInfo(name = "created_by")
    open var createdBy: String? = null

    @ColumnInfo(name = "modified_by")
    open var modifiedBy: String? = null

    // 深度拷贝方法
    open fun deepCopy(): BaseEntity {
        throw IllegalStateException("Subclasses must override this method")
    }

    // 乐观锁版本更新
    open fun incrementVersion() {
        version++
        updateTime = System.currentTimeMillis()
    }

    // 标记删除方法
    open fun markDeleted(operator: String? = null) {
        isDeleted = true
        updateTime = System.currentTimeMillis()
        modifiedBy = operator
    }
}