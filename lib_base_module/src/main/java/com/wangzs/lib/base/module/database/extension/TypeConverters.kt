package com.wangzs.lib.base.module.database.extension


import androidx.room.TypeConverter
import java.util.*

class TypeConverters {
    // Date <-> Long
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // Boolean <-> Int
    @TypeConverter
    fun fromBoolean(value: Boolean): Int = if (value) 1 else 0

    @TypeConverter
    fun toBoolean(value: Int): Boolean = value == 1

    // Enum <-> String
    @TypeConverter
    fun <T : Enum<T>> enumToString(value: T?): String? = value?.name

}