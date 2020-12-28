package com.pince.module_heart

import android.content.Intent
import com.example.a11699.comp_base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_heart.*

/**
 * @Author Yu
 * @Date 2020/12/28 13:56
 * @Description TODO
 */
class MainHeatActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_heart
    }

    override fun initView() {
        btn_leida.setOnClickListener {
            startActivity(Intent(this,LeiDaActivity::class.java))
        }
        btn_tantan.setOnClickListener {
        }
        btn_soul.setOnClickListener {
        }
    }

    override fun observeLiveData() {

    }

    override fun initViewData() {

    }
}