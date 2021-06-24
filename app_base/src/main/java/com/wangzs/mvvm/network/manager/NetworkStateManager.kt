package com.wangzs.mvvm.network.manager

import com.wangzs.mvvm.callback.livedata.event.EventLiveData


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