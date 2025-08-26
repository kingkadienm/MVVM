package com.wangzs.lib.base.module.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.wangzs.lib.base.module.database.entity.BaseEntity
import com.wangzs.lib.base.module.database.exception.DatabaseException
import java.lang.reflect.ParameterizedType

/**
 * 通用 Base DAO 接口，支持基本 CRUD 操作和自定义查询
 * @param T 实体类型，必须继承 BaseRoomEntity
 */


@Dao
abstract class BaseDao<T : BaseEntity> {
    companion object {
        private const val BASE_SOFT_DELETE_QUERY = """
            UPDATE %s 
            SET is_deleted = 1, 
                update_time = :timestamp 
            WHERE id = :id
        """
    }

    // 动态生成查询
    protected open fun getSoftDeleteQuery(): String {
        return BASE_SOFT_DELETE_QUERY.format(getTableName())
    }

    @RawQuery
    protected abstract suspend fun _executeRaw(query: SupportSQLiteQuery): Int

    protected suspend fun markDeleted(id: Long, timestamp: Long): Int {
        return _executeRaw(
            SimpleSQLiteQuery(
                getSoftDeleteQuery(),
                arrayOf(timestamp, id)
            )
        )
    }

    // region 基础CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: T): Long

    @Update
    abstract suspend fun update(entity: T): Int

    @Delete
    abstract suspend fun delete(entity: T): Int


    suspend fun softDelete(entity: T): Int {
        entity.markDeleted()
        return markDeleted(entity.id, entity.updateTime)
    }
    // endregion

    // region 查询方法
    // region 核心查询方法（使用RawQuery实现动态表名）
    @RawQuery
    protected abstract suspend fun _rawQuery(query: SupportSQLiteQuery): List<T>

    @RawQuery
    protected abstract suspend fun _rawQueryForSingle(query: SupportSQLiteQuery): T?

    suspend fun getById(id: Long): T? {
        return _rawQueryForSingle(
            SimpleSQLiteQuery(
                "SELECT * FROM ${getTableName()} WHERE id = ? AND is_deleted = 0",
                arrayOf(id)
            )
        )
    }

    suspend fun getAll(): List<T> {
        return _rawQuery(
            SimpleSQLiteQuery("SELECT * FROM ${getTableName()} WHERE is_deleted = 0")
        )
    }

    @RawQuery
    protected abstract suspend fun rawQuery(query: SupportSQLiteQuery): List<T>

    suspend fun findByCondition(
        condition: String,
        vararg args: Any,
        orderBy: String = "create_time DESC"
    ): List<T> {
        val query = SimpleSQLiteQuery(
            "SELECT * FROM ${getTableName()} WHERE $condition AND is_deleted = 0 ORDER BY $orderBy",
            args
        )
        return rawQuery(query)
    }
    // endregion

    // region 分页查询
    data class PagedResult<T>(
        val data: List<T>,
        val total: Int,
        val currentPage: Int,
        val pageSize: Int
    )

    suspend fun pagedQuery(
        page: Int,
        pageSize: Int,
        condition: String? = null,
        orderBy: String = "create_time DESC",
        vararg args: Any
    ): PagedResult<T> {
        val offset = page * pageSize
        val whereClause = if (condition.isNullOrEmpty()) "" else "WHERE $condition AND"

        val dataQuery = SimpleSQLiteQuery(
            "SELECT * FROM ${getTableName()} $whereClause is_deleted = 0 ORDER BY $orderBy LIMIT $pageSize OFFSET $offset",
            args
        )

        val countQuery = SimpleSQLiteQuery(
            "SELECT COUNT(*) FROM ${getTableName()} $whereClause is_deleted = 0",
            args
        )

        return PagedResult(
            data = rawQuery(dataQuery),
            total = rawQuery(countQuery).firstOrNull()?.let { (it as Number).toInt() } ?: 0,
            currentPage = page,
            pageSize = pageSize
        )
    }
    // endregion

    // 获取表名工具方法
    private fun getTableName(): String {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val clazz = type as Class<*>
        return clazz.getAnnotation(Entity::class.java)?.tableName
            ?: throw IllegalStateException("Entity must have @Entity annotation")
    }
    // region 事务增强
    @Transaction
    open suspend fun <R> executeWithTransaction(
        operation: suspend () -> R
    ): R {
        return try {
            operation()
        } catch (e: Exception) {
            throw DatabaseException.TransactionException(e)
        }
    }
}
