package com.wangzs.lib.net.config

import android.app.Application

/**
 *
 *
 * @author wangzs
 * @since 2024/3/14 17:04
 */
data class DomainConfig(
    val baseUrl: String,
    val configBuilder: NetConfig.Builder
)

private val domainConfigBuilder = DomainConfigBuilder()

fun domainConfigsInit(application: Application, configurations: DomainConfigBuilder.() -> Unit) {
    NetAppContext.init(application)
    domainConfigBuilder.configurations()
}

fun getConfigForDomain(baseUrl: String): NetConfig.Builder? {
    return domainConfigBuilder.getConfigForDomain(baseUrl)
}

class DomainConfigBuilder {

    private val configs = mutableListOf<DomainConfig>()

    fun addConfig(baseUrl: String, configBuilder: NetConfig.Builder) {
        configBuilder.setBaseUrl(baseUrl)
        configs.add(DomainConfig(baseUrl, configBuilder))
    }

    fun getConfigForDomain(baseUrl: String): NetConfig.Builder? {
        return configs.firstOrNull { it.baseUrl == baseUrl }?.configBuilder
    }

}