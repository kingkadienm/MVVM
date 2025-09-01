package com.wangzs.module.login.repository

import kotlinx.coroutines.flow.Flow

/**
 * Author: wangzs<br>
 * Created Date: 2025/8/23 20:42<br>
 * Desc : <br>
 */
interface ModuleDataRepositorySource {

    fun generateStream(): Flow<String>

}

