package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil

/**
 *Create time 2020/8/4
 *Create Yu
 *description:
 */
class TabAdapter3(val list: List<String>, val viewpager: ViewPager) : BaseTabAdapter(list, viewpager) {
    var targetListView: ArrayList<TextView> = ArrayList()
    lateinit var mContext: Context
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(context: Context, position: Int): View {
        mContext = context
        var tv = TextView(context)
        var layoutParams = LinearLayout.LayoutParams(ViewUtil.dip2px(66f), ViewUtil.dip2px(25f))
        tv.gravity = Gravity.CENTER
        tv.layoutParams = layoutParams
        tv.text = list[position]
        tv.setTextColor(context.resources.getColor(R.color.color_a0a0a0))
        tv.textSize = 14f
        tv.setOnClickListener {
            clickItem(position)
        }
        targetListView.add(tv)
        return tv
    }

    override fun getAllTargetView(): List<View> {
        return targetListView
    }

    override fun clickItem(position: Int) {
        viewpager.currentItem = position
    }

    override fun onPageSelected(position: Int) {
        for ((index, value) in targetListView.withIndex()) {
            if (index == position) {
                value.setTextColor(Color.BLACK)
                // value.textSize = 18f
                value.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            } else {
                // value.textSize = 14f
                value.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                value.setTextColor(mContext.resources.getColor(R.color.color_a0a0a0))
            }
        }
    }

    override fun onPageScrollStateChangedd(state: Int) {
    }

    override fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (1.28f - positionOffset <= 1) {
            targetListView[position].scaleX = ((1f).toFloat())
            targetListView[position].scaleY = ((1f).toFloat())
        } else {
            targetListView[position].scaleX = ((1.28f - positionOffset).toFloat())
            targetListView[position].scaleY = ((1.28f - positionOffset).toFloat())
        }

        try {
            if (1f + positionOffset > 1.28f) {
                targetListView[position + 1].scaleX = ((1.28f).toFloat())
                targetListView[position + 1].scaleY = ((1.28f).toFloat())
            } else {
                targetListView[position + 1].scaleX = ((1f + positionOffset).toFloat())
                targetListView[position + 1].scaleY = ((1f + positionOffset).toFloat())
            }
        } catch (e: Exception) {
        }
    }


}