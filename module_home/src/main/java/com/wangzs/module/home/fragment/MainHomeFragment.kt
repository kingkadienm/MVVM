package com.wangzs.module.home.fragment

import android.view.View
import android.view.animation.AnimationUtils
import com.wangzs.lib.base.utils.CoroutineUtils
import com.wangzs.lib.base.utils.ext.view.showToast
import com.wangzs.lib.base.view.BaseMvvmRefreshDataBindingFragment
import com.wangzs.lib.domain.entity.Demo
import com.wangzs.lib.net.dto.Resource
import com.wangzs.lib.net.utils.ext.launch
import com.wangzs.lib.net.utils.ext.observe
import com.wangzs.module.home.BR
import com.wangzs.module.home.R
import com.wangzs.module.home.adapter.MainHomeAdapter
import com.wangzs.module.home.databinding.FragmentHomeMainBinding
import com.wangzs.module.home.viewmodel.MainHomeViewModel
import kotlin.random.Random

/**
 * Describe:
 * 首页
 *
 * @author wangzs
 * @Date 2020/12/3
 */
class MainHomeFragment :
    BaseMvvmRefreshDataBindingFragment<FragmentHomeMainBinding, MainHomeViewModel>() {

    companion object {
        fun newsInstance(): MainHomeFragment {
            return MainHomeFragment()
        }
    }

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

    override fun initView(mView: View) {
        mAdapter = MainHomeAdapter()
        mAdapter.bindSkeletonScreen(
            requireBinding().mRecyclerView,
            com.wangzs.lib.base.R.layout.skeleton_default_service_item,
            8
        )
    }

    override fun initData() {
        onRefreshEvent()
    }

    var firstLoad = true

    override fun onRefreshEvent() {
        // 为了展示骨架屏
        if (firstLoad) {
            firstLoad = false
            CoroutineUtils.runOnUiThreadDelayed(1000,{ mViewModel.refreshData() })
        } else {
            mViewModel.refreshData()
        }
    }

    override fun onLoadMoreEvent() {
        mViewModel.loadMore()
    }

    override fun onBindRefreshLayout(): Int = R.id.mRefreshLayout

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = getString(R.string.title_home)

    private fun handleRecipesList(resource: Resource<List<Demo>>) {
        resource.launch {
            it?.apply {
                bindListData(recipes = ArrayList(this))
            }
        }
    }

    private fun bindListData(recipes: ArrayList<Demo>) {
        if (isRefresh) {
            val first = Random.nextInt(0, recipes.size / 2)
            val second = Random.nextInt(recipes.size / 2, recipes.size)
            val cache = ArrayList<Demo>()
            for (i in first.coerceAtMost(second)..first.coerceAtLeast(second)) {
                cache.add(recipes[i])
            }
            mViewModel.itemCount = cache.size
            mAdapter.setNewInstance(cache)
            // 执行列表动画
            requireBinding().mRecyclerView.apply {
                layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_fall_down)
                scheduleLayoutAnimation()
            }
        } else {
            val first = Random.nextInt(0, recipes.size)
            val second = Random.nextInt(0, recipes.size)
            val cache = ArrayList<Demo>()
            for (i in first.coerceAtMost(second)..first.coerceAtLeast(second)) {
                cache.add(recipes[i])
            }
            mViewModel.itemCount += cache.size
            mAdapter.addData(cache)
            "加载了${cache.size}条数据".showToast()
            // 执行列表动画
            // requireBinding().mRecyclerView.apply {
            //     layoutAnimation =
            //         AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_from_right)
            //     scheduleLayoutAnimation()
            // }
        }
    }
}
