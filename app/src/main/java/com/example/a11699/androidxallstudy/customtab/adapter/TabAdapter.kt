package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.custonview.ColorTrackTextView
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import kotlinx.android.synthetic.main.item_tab.view.*

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class TabAdapter(list: List<String>, val viewPager: ViewPager) : BaseTabAdapter(list,viewPager) {

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

        var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.weight = 1f
        colorTrackTextView.layoutParams = layoutParams

        colorTrackTextView.setOnClickListener { clickItem(position) }
        colorTvView.add(colorTrackTextView)
        return itemTabLayout
    }

    override fun getAllTargetView(): ArrayList<ColorTrackTextView> {
        return colorTvView
    }

    override fun clickItem(position: Int) {
        viewPager.currentItem = position
    }

    override fun onPageSelected(position: Int) {
        for ((index, value) in colorTvView.withIndex()) {
            value.isSelected = index == position
        }
    }

    override fun onPageScrollStateChangedd(state: Int) {
    }

    override fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (getAllTargetView()[position] is ColorTrackTextView) {
            var mLeft = getAllTargetView()[position] as ColorTrackTextView

            mLeft.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
            if (1.28f - positionOffset <= 1) {
                mLeft.scaleX = ((1f).toFloat())
                mLeft.scaleY = ((1f).toFloat())
            } else {
                mLeft.scaleX = ((1.28f - positionOffset).toFloat())
                mLeft.scaleY = ((1.28f - positionOffset).toFloat())
            }

            mLeft.setCurrentProcess((1 - positionOffset).toFloat())
            try {
                var mRight = getAllTargetView()[position + 1] as ColorTrackTextView
                mRight.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                if (1f + positionOffset > 1.28f) {
                    mRight.scaleX = ((1.28f).toFloat())
                    mRight.scaleY = ((1.28f).toFloat())
                } else {
                    mRight.scaleX = ((1f + positionOffset).toFloat())
                    mRight.scaleY = ((1f + positionOffset).toFloat())
                }

                mRight.setCurrentProcess((positionOffset).toFloat())
            } catch (e: Exception) {
            }
        }
    }

}