package com.example.a11699.androidxallstudy.meinformation

import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_base.util.BarUtils
import com.google.android.material.appbar.AppBarLayout
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_meinformation.*

/**
 * @Author Yu
 * @Date 2020/11/11 14:35
 * @Description TODO
 */
class MeInforMationActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_meinformation
    }

    override fun initView() {
        cover_bg.setImageResource(R.drawable.photo4)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val alphaMaxOffset = ViewUtil.dip2px(80f)
        toolbar.setBackgroundResource(R.drawable.shape_white_bg)
        val background = toolbar.background
        background.alpha = 0

        var dOffset: Float
        val blcakBack = resources.getDrawable(R.drawable.back_icon)
        val whiteBack = resources.getDrawable(R.drawable.left_back_white_icon)
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset < -alphaMaxOffset) {
                dOffset =
                        ((-verticalOffset - alphaMaxOffset).toFloat() / alphaMaxOffset.toFloat())
                if (dOffset > 1) {
                    dOffset = 1.0f
                }
                if (dOffset >= 0.5f) {
                    toolbar.navigationIcon = blcakBack
                } else {
                    toolbar.navigationIcon = whiteBack
                }
                background.alpha = (255 * dOffset).toInt()
            } else {
                background.alpha = 0
            }
        })
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }


}