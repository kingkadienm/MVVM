package debug

import android.view.View
import com.wangzs.lib.base.view.BaseMvvmViewBindingActivity
import com.wangzs.lib.utils.ToastUtils
import com.wangzs.module.me.R
import com.wangzs.module.me.activity.MoreRequestServerActivity
import com.wangzs.module.me.activity.RoomTestActivity
import com.wangzs.module.me.activity.SaveStateTestActivity
import com.wangzs.module.me.databinding.FragmentMeMainBinding
import com.wangzs.module.me.viewmodel.MainMeViewModel

/**
 *
 *
 * @author wangzs
 * @since 2022/7/7 22:56
 */
class MainMeActivity : BaseMvvmViewBindingActivity<FragmentMeMainBinding, MainMeViewModel>() {


    override fun onBindLayout(): Int = R.layout.fragment_me_main

    override fun initView() {
    }

    override fun initData() {

    }

    override fun initListener() {
        requireBinding().button1.setOnClickListener(this::onClick)
        requireBinding().button2.setOnClickListener(this::onClick)
        requireBinding().button3.setOnClickListener(this::onClick)
        requireBinding().button4.setOnClickListener(this::onClick)
        requireBinding().button5.setOnClickListener(this::onClick)
    }

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = "Me"

    override fun onClick(v: View?) {
        if (beFastClick()) {
            return
        }
        when (v?.id) {
            R.id.button_1 -> {
            }

            R.id.button_2 -> {
            }

            R.id.button_3 -> {
                val trim = requireBinding().editText.text.toString().trim()
                if (trim.isBlank()) {
                    ToastUtils.showShort("输入内容不能为空")
                    return
                }
                SaveStateTestActivity.start(mContext, trim)
            }

            R.id.button_4 -> {
                RoomTestActivity.start(mContext)
            }

            R.id.button_5 -> {
                MoreRequestServerActivity.start(mContext)
            }
        }
    }

    override fun FragmentMeMainBinding.onClear() {

    }

    override fun initViewObservable() {

    }

}