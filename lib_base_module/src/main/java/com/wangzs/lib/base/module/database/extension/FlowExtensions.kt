package com.wangzs.lib.base.module.database.extension

import com.wangzs.lib.base.module.database.exception.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.jvm.java

import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

object FlowExtensions {

    // region 基础转换
    inline fun <reified T> Flow<T?>.toSingleResult(): Flow<Result<T>> {
        return this
            .map { data ->
                data?.let { Result.success(it) }
                    ?: Result.failure(DatabaseException.NotFoundException(T::class.java))
            }
            .catch { e -> emit(Result.failure(e)) }
    }

    fun <T> Flow<T?>.toSingleResult(entityClass: KClass<*>): Flow<Result<T>> {
        return this
            .map { data ->
                data?.let { Result.success(it) }
                    ?: Result.failure(DatabaseException.NotFoundException(entityClass.java))
            }
            .catch { e -> emit(Result.failure(e)) }
    }
    // endregion

    // region 集合扩展
    inline fun <reified T> Flow<List<T>>.toResultList(): Flow<Result<List<T>>> {
        return this
            .map { data ->
                if (data.isEmpty()) {
                    Result.failure(DatabaseException.NotFoundException(T::class.java))
                } else {
                    Result.success(data)
                }
            }
            .catch { e -> emit(Result.failure(e)) }
    }
    // endregion

    // region 分页扩展
    data class PagingResult<T>(
        val items: List<T>,
        val currentPage: Int,
        val hasNext: Boolean
    )

    inline fun <reified T> pagingFlow(
        crossinline loader: suspend (page: Int) -> List<T>
    ): Flow<PagingResult<T>> = flow {
        var page = 0
        do {
            val items = loader(page)
            emit(
                PagingResult(
                    items = items,
                    currentPage = page,
                    hasNext = items.isNotEmpty()
                )
            )
            page++
        } while (items.isNotEmpty())
    }

    val _mIoDispatcher: CoroutineContext = Dispatchers.IO


    inline fun <reified T> dealDataFlow(crossinline block: suspend () -> T): Flow<T> {
        return flow {
            emit(block.invoke())
        }.flowOn(_mIoDispatcher)
    }

    // endregion
}