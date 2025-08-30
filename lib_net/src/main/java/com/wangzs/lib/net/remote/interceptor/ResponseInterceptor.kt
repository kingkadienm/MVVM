package com.wangzs.lib.net.remote.interceptor

import android.text.TextUtils
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.utils.LogUtils
import com.wangzs.lib.net.config.Encoding
import com.wangzs.lib.net.config.contentTypeValue
import com.wangzs.lib.net.error.ApiException
import com.wangzs.lib.net.error.NULL_DATA
import com.wangzs.lib.net.error.PARSE_ERROR
import com.wangzs.lib.net.error.mapper.ErrorManager
import com.wangzs.lib.net.error.mapper.ErrorMapper
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * Describe:
 * <p>相应数据拦截器</p>
 *
 * @author wangzs
 * @Date 2020/12/1
 */
class ResponseInterceptor : Interceptor {

    companion object {
        val TAG: String = ResponseInterceptor::class.java.simpleName
    }

    private val errorManager: ErrorManager = ErrorManager(ErrorMapper())

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // 判断是否为流式返回，如果是则直接返回，不进行处理
        if (request.header("Accept")?.contains("text/event-stream") == true) {
            return response
        }

        val body = bufferBody(response)

        // 网络相应不成功返回
        if (!response.isSuccessful) {
            val ex = ApiException(response.code)
            ex.message = errorManager.getError(response.code).description
            ex.message.showToast()
            LogUtils.e(TAG, ex.message, ex)
            throw ex
        }

        return try {
            if (TextUtils.isEmpty(body) || "null".equals(body, ignoreCase = true)) {
                throw ApiException(NULL_DATA, errorManager.getError(NULL_DATA).description)
            }
            val jsonObject = JSONObject(body)
            val status = jsonObject.getString("status").toInt()
            val message = jsonObject.getString("message")
            if (0 == status) {
                response.newBuilder()
                    .body(body.toResponseBody(contentTypeValue.toMediaTypeOrNull()))
                    .build()
            } else {
                throw ApiException(status, message ?: "")
            }
        } catch (e: Exception) {
            throw ApiException(PARSE_ERROR, errorManager.getError(PARSE_ERROR).description, e)
        }
    }

    private fun bufferBody(response: Response): String {
        val source = response.body!!.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer
        return buffer.clone().readString(Charset.forName(Encoding))
    }
}
