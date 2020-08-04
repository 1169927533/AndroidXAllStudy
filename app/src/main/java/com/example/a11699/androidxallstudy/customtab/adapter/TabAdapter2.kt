package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.TextureView
import android.view.View
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.custonview.ColorTrackTextView
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class TabAdapter2(list: List<String>, private val viewPager: ViewPager) : BaseAdapter(list, viewPager) {
    private lateinit var mContext: Context
    var mList: List<String> = list
    var colorTvView: ArrayList<TextView> = ArrayList<TextView>()

    override fun getCount(): Int {
        return mList.size
    }

    override fun getView(context: Context, position: Int): View {
        mContext = context
        var tv = TextView(context)
        var layoutParams = LinearLayout.LayoutParams(ViewUtil.dip2px(80f), ViewUtil.dip2px(28f))
        tv.setBackgroundResource(R.drawable.item_tab_selectt)
        tv.text = mList[position]
        tv.layoutParams = layoutParams
        tv.gravity = Gravity.CENTER
        colorTvView.add(tv)
        tv.setOnClickListener {
            clickItem(position)
        }
        return tv
    }

    override fun getAllTargetView(): List<View> {
        return colorTvView
    }

    override fun clickItem(position: Int) {
        viewPager.currentItem = position
    }

    //选中时
    override fun onPageSelected(position: Int) {
        for ((index, value) in colorTvView.withIndex()) {
            if (index == position) {
                value.isSelected = true
                value.setTextColor(Color.WHITE)
            } else {
                value.isSelected = false
                value.setTextColor(mContext.resources.getColor(R.color.color_a0a0a0))
            }
        }
    }

    override fun onPageScrollStateChangedd(state: Int) {
    }

    override fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }
}