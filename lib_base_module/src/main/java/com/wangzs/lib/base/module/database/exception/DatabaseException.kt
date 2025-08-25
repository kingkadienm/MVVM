package com.wangzs.lib.base.module.database.exception

sealed class DatabaseException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause) {

    // 事务失败异常
    class TransactionException(cause: Throwable) :
        DatabaseException("Database transaction failed", cause)

    // 乐观锁冲突异常
    class OptimisticLockException(entity: Any) :
        DatabaseException("Optimistic lock failed for ${entity.javaClass.simpleName}")

    // 查询无结果异常
    class NotFoundException(entityClass: Class<*>) :
        DatabaseException("${entityClass.simpleName} not found")

    // 无效操作异常
    class IllegalOperationException(message: String) :
        DatabaseException(message)
}