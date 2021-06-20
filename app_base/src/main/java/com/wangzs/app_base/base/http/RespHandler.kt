package com.wangzs.app_base.base.http

import com.wangzs.app_base.base.http.exception.HttpRespException
import com.wangzs.app_base.base.http.exception.NetworkRespException
import com.wangzs.app_base.base.http.exception.RespException
import com.wangzs.app_base.base.http.exception.UnknownRespException
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
abstract class RespHandler<T> : DisposableObserver<T>() {

    public override fun onStart() {
        super.onStart()
    }

    override fun onNext(data: T) {
        onSuccess(data)
    }

    override fun onError(throwable: Throwable) {
        if (throwable is ConnectException || throwable is UnknownHostException || throwable is IOException) {
            // 网络连接错误
            onFailure(NetworkRespException("连接错误，请检查您的网络稍后重试。"))
        } else if (throwable is HttpException) {
            val httpCode = throwable.code()
            val msg = throwable.message()
            onFailure(HttpRespException(httpCode, msg))
        } else if (throwable is RespException) {
            // 自定义错误
            onFailure(throwable)
        } else {
            onFailure(UnknownRespException(throwable.message))
        }
        onComplete()
    }

    override fun onComplete() {
        onFinish()
    }

    open fun onSuccess(data: T) {}

    open fun onFailure(e: RespException) {}

    open fun onFinish() {}

}