package debug

import com.wangzs.lib.base.utils.ThreadUtils
import com.wangzs.lib.base.view.BaseFragment
import com.wangzs.lib.base.view.BaseMvvmRefreshDataBindingActivity
import com.wangzs.lib.common.utils.EnvironmentUtils
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.log.KLog
import com.wangzs.lib.net.dto.Resource
import com.wangzs.lib.net.utils.ext.launch
import com.wangzs.lib.net.utils.ext.observe
import com.wangzs.module.home.BR
import com.wangzs.module.home.R
import com.wangzs.module.home.adapter.MainHomeAdapter
import com.wangzs.module.home.databinding.FragmentHomeMainBinding
import com.wangzs.module.home.viewmodel.MainHomeViewModel

/**
 *
 *
 * @author wangzs
 * @since 2022/7/7 23:00
 */
class MainHomeActivity :
    BaseMvvmRefreshDataBindingActivity<FragmentHomeMainBinding, MainHomeViewModel>() {

    private lateinit var mAdapter: MainHomeAdapter

    override fun onBindVariableId(): MutableList<Pair<Int, Any>> {
        return arrayListOf(BR.viewModel to mViewModel)
    }

    override fun FragmentHomeMainBinding.onClear() {

    }

    override fun initViewObservable() {
        observe(mViewModel.recipesLiveData, ::handleRecipesList)
    }

    override fun onBindLayout(): Int = R.layout.fragment_home_main

    override fun initView() {
        mAdapter = MainHomeAdapter()
        mAdapter.bindSkeletonScreen(
            requireBinding().mRecyclerView,
            com.wangzs.lib.base.R.layout.skeleton_default_service_item,
            8
        )
    }

    override fun initData() {
        onRefreshEvent()
        KLog.d(BaseFragment.TAG, EnvironmentUtils.Storage.getCachePath(mContext))
    }

    var firstLoad = true

    override fun onRefreshEvent() {
        // 为了展示骨架屏
        if (firstLoad) {
            firstLoad = false
            ThreadUtils.runOnUiThread({ mViewModel.refreshData() }, 1000)
        } else {
            mViewModel.refreshData()
        }
    }

    override fun onLoadMoreEvent() {
        mViewModel.loadMore()
    }

    override fun onBindRefreshLayout(): Int = R.id.mRefreshLayout

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = "首页"

    private fun handleRecipesList(resource: Resource<List<Demo>>) {
        resource.launch {
            it?.apply {
                bindListData(recipes = ArrayList(this))
            }
        }
    }

    private fun bindListData(recipes: ArrayList<Demo>) {
        mAdapter.setNewInstance(recipes)
    }
}