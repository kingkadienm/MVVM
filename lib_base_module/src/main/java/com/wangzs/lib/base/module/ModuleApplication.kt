package com.wangzs.lib.base.module

import com.wangzs.lib.base.BaseApplication
import com.wangzs.lib.net.config.NetConfig
import com.wangzs.lib.net.config.URL_EDITH
import com.wangzs.lib.net.config.URL_MAIN
import com.wangzs.lib.net.config.domainConfigsInit

/**
 * 初始化应用程序
 * @author wangzs
 * @time 2020/11/30 23:04
 */
open class ModuleApplication : BaseApplication() {

    override fun initOnlyMainProcessInLowPriorityThread() {
        super.initOnlyMainProcessInLowPriorityThread()
        initNet()
    }

    private fun initNet() {
        domainConfigsInit(this) {
            addConfig(URL_MAIN, NetConfig.Builder().apply {
                setDefaultTimeout(5_000)
            })
            addConfig(URL_EDITH, NetConfig.Builder().apply {
                enableHttps(true)
                setDefaultTimeout(6_000)
            })
            addConfig("http://192.168.1.12:11434", NetConfig.Builder().apply {
                setDefaultTimeout(6_000)
            })

        }
    }

}
