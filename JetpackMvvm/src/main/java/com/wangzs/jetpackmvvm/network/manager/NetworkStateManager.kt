package com.wangzs.jetpackmvvm.network.manager

import com.wangzs.jetpackmvvm.callback.livedata.event.EventLiveData


/**
 * @Description: 网络变化管理者
 * @Author: wangzs
 * @Version:
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}