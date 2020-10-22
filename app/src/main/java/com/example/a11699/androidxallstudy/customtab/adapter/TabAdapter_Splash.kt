package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil

/**
 *Create time 2020/8/26
 *Create Yu
 *description:
 */
class TabAdapter_Splash(list: List<String>, private val viewPager: ViewPager) : BaseTabAdapter(list, viewPager) {
    private lateinit var mContext: Context
    var mList: List<String> = list

    override fun getCount(): Int {
        return mList.size
    }

    override fun getView(context: Context, position: Int): View {
        mContext = context
        var img = ImageView(context)
        var layoutParams = LinearLayout.LayoutParams(ViewUtil.dip2px(8f), ViewUtil.dip2px(8f))
        img.setBackgroundResource(R.drawable.shape_splash_noselect)
        img.layoutParams = layoutParams

        targetListView.add(img)

        return img
    }


    override fun clickItem(position: Int) {
        viewPager.currentItem = position
    }

    //选中时
    override fun onPageSelected(position: Int) {
        for ((index, value) in targetListView.withIndex()) {
            if (index == position) {
                value.isSelected = true
                value.setBackgroundResource(R.drawable.shape_splash_select)
            } else {
                value.isSelected = false
                value.setBackgroundResource(R.drawable.shape_splash_noselect)
            }
        }
    }

    override fun onPageScrollStateChangedd(state: Int) {
    }

    override fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }
}