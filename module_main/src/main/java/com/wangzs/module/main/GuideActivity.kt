package com.wangzs.module.main

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.wangzs.lib.base.view.BaseActivity
import com.wangzs.lib.common.utils.ext.dp2px
import com.wangzs.lib.utils.SPUtils

/**
 * Describe:
 * 引导页
 *
 * @author wangzs
 * @Date 2023/8/31
 */
class GuideActivity : BaseActivity() {

    companion object {
        private const val KEY_GUIDE_SHOWN = "key_guide_shown"

        /**
         * 检查是否需要显示引导页
         */
        fun shouldShowGuide(): Boolean {
            return !SPUtils.getInstance().getBoolean(KEY_GUIDE_SHOWN, false)
        }

        /**
         * 标记引导页已显示
         */
        fun markGuideShown() {
            SPUtils.getInstance().put(KEY_GUIDE_SHOWN, true)
        }
    }

    private lateinit var viewPager: ViewPager
    private lateinit var llIndicator: LinearLayout
    private lateinit var guideAdapter: GuideAdapter
    private val guideFragments = mutableListOf<GuideFragment>()
    private val indicators = mutableListOf<View>()

    override fun onBindLayout(): Int = R.layout.activity_guide

    override fun initView() {
        viewPager = findViewById(R.id.vp_guide)
        llIndicator = findViewById(R.id.ll_indicator)
        setupGuidePages()
        setupViewPager()
        setupIndicators()
    }

    override fun initData() {
        // 数据初始化在initView中完成
    }

    override fun initListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 更新页面指示器状态
                updatePageIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupGuidePages() {
        // 创建引导页片段
        guideFragments.add(
            GuideFragment.newInstance(
                title = getString(R.string.guide_page_1_title),
                description = getString(R.string.guide_page_1_desc),
                imageRes = R.drawable.guide_page_1,
                isLastPage = false
            )
        )

        guideFragments.add(
            GuideFragment.newInstance(
                title = getString(R.string.guide_page_2_title),
                description = getString(R.string.guide_page_2_desc),
                imageRes = R.drawable.guide_page_2,
                isLastPage = false
            )
        )

        guideFragments.add(
            GuideFragment.newInstance(
                title = getString(R.string.guide_page_3_title),
                description = getString(R.string.guide_page_3_desc),
                imageRes = R.drawable.guide_page_3,
                isLastPage = true
            )
        )
    }

    private fun setupViewPager() {
        guideAdapter = GuideAdapter(supportFragmentManager, guideFragments)
        viewPager.adapter = guideAdapter
    }

    private fun setupIndicators() {
        llIndicator.removeAllViews()
        indicators.clear()

        for (i in guideFragments.indices) {
            val indicator = View(this)
            val layoutParams = LinearLayout.LayoutParams(dp2px(8), dp2px(8))
            if (i < guideFragments.size - 1) {
                layoutParams.marginEnd = dp2px(8)
            }
            indicator.layoutParams = layoutParams

            val background = if (i == 0) {
                ContextCompat.getDrawable(this, R.drawable.guide_indicator_selected)
            } else {
                ContextCompat.getDrawable(this, R.drawable.guide_indicator_normal)
            }
            indicator.background = background

            indicators.add(indicator)
            llIndicator.addView(indicator)
        }
    }

    private fun updatePageIndicator(position: Int) {
        for (i in indicators.indices) {
            val background = if (i == position) {
                ContextCompat.getDrawable(this, R.drawable.guide_indicator_selected)
            } else {
                ContextCompat.getDrawable(this, R.drawable.guide_indicator_normal)
            }
            indicators[i].background = background
        }
    }

    /**
     * 进入主页面
     */
    fun enterMainActivity() {
        markGuideShown()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun enableToolbar(): Boolean = false

    override fun enableAllowFullScreen(): Boolean = true

    /**
     * ViewPager适配器
     */
    private class GuideAdapter(
        fm: FragmentManager,
        private val fragments: List<GuideFragment>
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment = fragments[position]
    }
}