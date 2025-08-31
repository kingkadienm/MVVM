package com.wangzs.lib.base.utils

import com.wangzs.lib.utils.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

/**
 * 协程相关工具类
 *
 * @author wangzs
 */
object CoroutineUtils {
    private val mainScope = CoroutineScope(Main + SupervisorJob())
    private val ioScope = CoroutineScope(IO + SupervisorJob())
    private val jobMap = ConcurrentHashMap<String, Job>()

    /**
     * 在IO线程执行异步任务
     */
    fun launchIO(
        taskName: String = "", block: suspend CoroutineScope.() -> Unit
    ): Job {
        return ioScope.launch {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Main) {
                    ToastUtils.showShort("协程任务执行出错: ${e.message}")
                }
            } finally {
                if (taskName.isNotEmpty()) {
                    jobMap.remove(taskName)
                }
            }
        }.also {
            if (taskName.isNotEmpty()) {
                jobMap[taskName] = it
            }
        }
    }

    /**
     * 延迟执行任务
     */
    fun launchIODelayed(
        delayMillis: Long, block: suspend CoroutineScope.() -> Unit
    ): Job {
        return ioScope.launch {
            delay(delayMillis)
            block()
        }
    }

    /**
     * 取消指定任务
     */
    fun cancelTask(taskName: String) {
        jobMap[taskName]?.cancel()
        jobMap.remove(taskName)
    }

    /**
     * 切换到主线程执行
     */
    suspend fun <T> runOnUiThread(block: suspend CoroutineScope.() -> T): T {
        return withContext(Main) {
            block()
        }
    }

    /**
     * 立即切换到主线程执行（不等待当前协程完成）
     */
    fun runOnUiThreadImmediately(block: suspend CoroutineScope.() -> Unit) {
        mainScope.launch {
            block()
        }
    }

    /**
     * 延迟切换到主线程执行
     */
    fun runOnUiThreadDelayed(delayMillis: Long, block: suspend CoroutineScope.() -> Unit) {
        mainScope.launch {
            delay(delayMillis)
            block()
        }
    }

    /**
     * 清理所有协程任务
     */
    fun cleanup() {
        ioScope.coroutineContext.cancelChildren()
        mainScope.coroutineContext.cancelChildren()
        jobMap.clear()
    }
}