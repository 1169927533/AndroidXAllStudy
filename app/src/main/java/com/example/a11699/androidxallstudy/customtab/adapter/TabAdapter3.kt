package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil

/**
 *Create time 2020/8/4
 *Create Yu
 *description:
 */
class TabAdapter3(val list: List<String>, val viewpager: ViewPager) : BaseAdapter(list, viewpager) {
    var targetListView: List<TextView> = ArrayList()
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(context: Context, position: Int): View {
        var tv = TextView(context)
        tv.setBackgroundColor(Color.RED)
        var layoutParams = LinearLayout.LayoutParams(ViewUtil.dip2px(59f), ViewUtil.dip2px(20f))
        tv.gravity = Gravity.CENTER
        tv.layoutParams = layoutParams
        tv.text = list[position]
        return tv
    }

    override fun getAllTargetView(): List<View> {
        return targetListView
    }

    override fun clickItem(position: Int) {
        viewpager.currentItem = position
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChangedd(state: Int) {

    }

    override fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }


}