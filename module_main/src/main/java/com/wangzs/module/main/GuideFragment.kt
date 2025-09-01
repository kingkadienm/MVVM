package com.wangzs.module.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Describe:
 * 引导页片段
 *
 * @author wangzs
 * @Date 2023/8/31
 */
class GuideFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_IMAGE_RES = "arg_image_res"
        private const val ARG_IS_LAST_PAGE = "arg_is_last_page"

        fun newInstance(
            title: String,
            description: String,
            imageRes: Int,
            isLastPage: Boolean
        ): GuideFragment {
            val fragment = GuideFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
                putInt(ARG_IMAGE_RES, imageRes)
                putBoolean(ARG_IS_LAST_PAGE, isLastPage)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var ivGuideImage: ImageView
    private lateinit var tvGuideTitle: TextView
    private lateinit var tvGuideDesc: TextView
    private lateinit var btnStartExperience: Button
    private lateinit var tvSkip: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupData()
        setupListeners()
    }

    private fun initViews(view: View) {
        ivGuideImage = view.findViewById(R.id.iv_guide_image)
        tvGuideTitle = view.findViewById(R.id.tv_guide_title)
        tvGuideDesc = view.findViewById(R.id.tv_guide_desc)
        btnStartExperience = view.findViewById(R.id.btn_start_experience)
        tvSkip = view.findViewById(R.id.tv_skip)
    }

    private fun setupData() {
        arguments?.let { args ->
            val title = args.getString(ARG_TITLE, "")
            val description = args.getString(ARG_DESCRIPTION, "")
            val imageRes = args.getInt(ARG_IMAGE_RES, 0)
            val isLastPage = args.getBoolean(ARG_IS_LAST_PAGE, false)

            tvGuideTitle.text = title
            tvGuideDesc.text = description
            
            if (imageRes != 0) {
                ivGuideImage.setImageResource(imageRes)
            }

            // 只在最后一页显示"开始体验"按钮
            btnStartExperience.visibility = if (isLastPage) View.VISIBLE else View.GONE
            
            // 在最后一页隐藏跳过按钮
            tvSkip.visibility = if (isLastPage) View.GONE else View.VISIBLE
        }
    }

    private fun setupListeners() {
        btnStartExperience.setOnClickListener {
            // 通知GuideActivity进入主页面
            (activity as? GuideActivity)?.enterMainActivity()
        }
        
        tvSkip.setOnClickListener {
            // 跳过引导直接进入主页面
            (activity as? GuideActivity)?.enterMainActivity()
        }
    }
}