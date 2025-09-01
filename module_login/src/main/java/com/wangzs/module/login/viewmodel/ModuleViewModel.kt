package com.wangzs.module.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.wangzs.lib.base.mvvm.viewmodel.BaseViewModel
import com.wangzs.module.login.repository.ModuleDataRepository

class ModuleViewModel(state: SavedStateHandle) : BaseViewModel() {

    private val repository = ModuleDataRepository()


}