package com.example.mylibrary

import com.example.a11699.comp_base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_threelogin.*

/**
 * @Author Yu
 * @Date 2020/11/25 11:53
 * @Description TODO
 */
class ThreeLoginActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_threelogin
    }

    override fun initView() {
        btn_loginByWX.setOnClickListener {

        }
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }
}