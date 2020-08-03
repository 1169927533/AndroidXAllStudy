package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.custonview.ColorTrackTextView
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import kotlinx.android.synthetic.main.item_tab.view.*

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class TabAdapter(list: List<String>) : BaseAdapter {

    var mList: List<String> = list
    var colorTvView: ArrayList<ColorTrackTextView> = ArrayList<ColorTrackTextView>()
    override fun getCount(): Int {
        return mList.size
    }

    override fun getView(context: Context, position: Int): View {
        var itemTabLayout = LayoutInflater.from(context).inflate(R.layout.item_tab, null)
        var colorTrackTextView = itemTabLayout.customView as ColorTrackTextView
        colorTrackTextView.initOriginPaint(ViewUtil.dip2px(14f).toFloat(), Color.BLUE, Color.GRAY) //设置字体大小和宽度
        colorTrackTextView.text = mList[position]
        colorTvView.add(colorTrackTextView)
        return itemTabLayout
    }

    override fun getAllTargetView(): ArrayList<ColorTrackTextView> {
        return colorTvView
    }

    override fun changItemState(position: Int) {
        for ((index, value) in colorTvView.withIndex()) {
            value.isSelected = index == position
        }
    }
}