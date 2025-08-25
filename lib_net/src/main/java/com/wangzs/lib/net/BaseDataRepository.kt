package com.wangzs.lib.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext


/**
 * 基础公共数据仓库进行分发
 * * 服务端
 * * 本地
 * @author wangzs
 * @time 2020/12/1 0:21
 */
open class BaseDataRepository : BaseDataRepositorySource {


    protected val mIoDispatcher: CoroutineContext = Dispatchers.IO



    protected inline fun <reified T> dealDataFlow(crossinline block: suspend () -> T): Flow<T> {
        return flow {
            emit(block.invoke())
        }.flowOn(mIoDispatcher)
    }
}
