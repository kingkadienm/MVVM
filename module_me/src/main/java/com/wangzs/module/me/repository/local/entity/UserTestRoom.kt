package com.wangzs.module.me.repository.local.entity

import androidx.room.Entity
import com.wangzs.lib.base.module.database.entity.CoreEntity

/**
 * Describe:
 * <p></p>
 *
 * @author wangzs
 * @Date 2020/12/7
 */
@Entity
data class UserTestRoom(
    var image: String,
    var firstName: String,
    var lastName: String,
    var age: Int
) : CoreEntity()