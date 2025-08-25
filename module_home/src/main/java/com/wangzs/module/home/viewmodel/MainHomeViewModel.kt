package com.wangzs.module.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wangzs.lib.base.mvvm.viewmodel.BaseRefreshViewModel
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.dto.Resource
import com.wangzs.module.home.repository.HomeDataRepository
import kotlinx.coroutines.launch

/**
 * Describe:
 * <p></p>
 *
 * @author wangzs
 * @Date 2020/12/1
 */
class MainHomeViewModel(state: SavedStateHandle) : BaseRefreshViewModel() {

    val savedStateHandle = state

    private val homeDataRepository by lazy { HomeDataRepository() }

    private val recipesLiveDataPrivate = MutableLiveData<Resource<List<Demo>>>()
    val recipesLiveData: LiveData<Resource<List<Demo>>> get() = recipesLiveDataPrivate


    var itemCount = 0

    private fun getRecipes(isRefresh: Boolean) {
        viewModelScope.launch {
            // 合并请求结果示例
            homeDataRepository.getBeautyStar().collect {
                // setValue() 只能在主线程中调用，postValue() 可以在任何线程中调用
                // recipesLiveDataPrivate.value = it
                recipesLiveDataPrivate.postValue(it)
                if (isRefresh) {
                    postStopRefreshEvent()
                } else {
                    postStopLoadMoreEvent()
                }
                if (itemCount > 50) {
                    postShowToastViewEvent("数据全部加载完毕")
                    postStopLoadMoreWithNoMoreDataEvent()
                }
            }
            // 单个请求示例
            /*homeDataRepository.requestRecipes().collect {
                KLog.d(TAG, it.toJson())
            }*/
        }
    }

    override fun refreshData() {
        getRecipes(true)
    }

    override fun loadMore() {
        getRecipes(false)
    }

}
