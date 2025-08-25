package com.wangzs.lib.net.remote.network

import com.wangzs.lib.net.BuildConfig
import com.wangzs.lib.net.config.NetConfig
import com.wangzs.lib.net.config.getConfigForDomain
import com.wangzs.lib.net.remote.interceptor.RequestInterceptor
import com.wangzs.lib.net.remote.interceptor.ResponseInterceptor
import com.wangzs.lib.net.utils.SSLContextUtil
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Describe:
 * 服务代理生成
 *
 *  在application中必须先初始化NetConfig
 *  Map<String, String> heads = new HashMap<>();
 *  heads.put("1","head");
 *
 *  NetConfig config = new NetConfig.Builder()
 *          .setBaseUrl("http://192.168.137.1:8085/")
 *          .setDefaultTimeout(10_000)
 *          .setHeads(heads)
 *          .addInterceptor(interceptor)
 *          .enableHttps(true)
 *          .build();
 *   config.initContext(this);
 * @author wangzs
 * @Date 2020/12/1
 */

class RetrofitManager(private val builder: NetConfig.Builder) : AbsRetrofit() {
    companion object {
        private val mInstanceMap = hashMapOf<String, RetrofitManager?>()

        @JvmStatic
        @Synchronized
        fun getInstance(url: String): RetrofitManager {
            var mInstance = mInstanceMap[url]
            if (mInstance == null) {
                val configForDomain = getConfigForDomain(url) ?: throw NullPointerException(
                    "The network configuration information cannot be found, please initialize it in Application in time."
                )
                mInstance = RetrofitManager(configForDomain).also {
                    mInstanceMap[url] = it
                }
            }
            return mInstance
        }
    }

    private val mOkHttpBuilder: OkHttpClient.Builder = getOkHttpClientBuilder()
    private var mRetrofit: Retrofit? = null
    private var mOkHttpClient: OkHttpClient? = null
    private val mLogger = getLoggerInterceptor()

    init {
        // 添加请求拦截器
        mOkHttpBuilder.addInterceptor(RequestInterceptor())
        // 添加相应数据拦截器
        mOkHttpBuilder.addInterceptor(ResponseInterceptor())
        mOkHttpBuilder.addInterceptor(mLogger)

        mOkHttpBuilder.protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1)) // 强制多协议
        // 添加配置增加拦截器
        val interceptors = builder.interceptors
        if (interceptors.isNotEmpty()) {
            interceptors.forEach { mOkHttpBuilder.addInterceptor(it) }
        }
        // 添加网络拦截器
        val networkInterceptors = builder.networkInterceptors
        if (networkInterceptors.isNotEmpty()) {
            networkInterceptors.forEach { mOkHttpBuilder.addInterceptor(it) }
        }
        mOkHttpBuilder.connectionPool(
            ConnectionPool(
                maxIdleConnections = 5,
                keepAliveDuration = 5,
                timeUnit = TimeUnit.MINUTES
            )
        )
            .connectTimeout(30, TimeUnit.SECONDS)  // 连接超时
            .readTimeout(60, TimeUnit.SECONDS)     // 读取超时（建议大模型设置为60+秒）
            .writeTimeout(30, TimeUnit.SECONDS)
//        mOkHttpBuilder.connectTimeout(builder.connectTimeout, TimeUnit.MILLISECONDS)
//        mOkHttpBuilder.readTimeout(builder.readTimeout, TimeUnit.MILLISECONDS)
//        mOkHttpBuilder.writeTimeout(builder.writeTimeout, TimeUnit.MILLISECONDS)
        mOkHttpBuilder.retryOnConnectionFailure(builder.retryOnConnectionFailure)
        // 判断是否启用 https
        if (builder.enableHttps) {
            // 给client的builder添加了增加可以忽略SSL
            val sslParams = SSLContextUtil.getSslSocketFactory()
            mOkHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            mOkHttpBuilder.hostnameVerifier(SSLContextUtil.UnSafeHostnameVerifier)
        }
    }

    override fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(builder.baseUrl)
                .client(mOkHttpBuilder.build().also { mOkHttpClient = it })
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return mRetrofit!!
    }
}
