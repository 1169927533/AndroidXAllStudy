package com.example.a11699.androidxallstudy.xmlwriteandread.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.xmlwriteandread.FaceModel

/**
 * @Author Yu
 * @Date 2020/11/4 13:53
 * @Description 展示表情的适配器
 */
class EmoticonViewPagerAdapter(var mContext:Context,var faceModeList:List<FaceModel>,var mLinearLayout:LinearLayout) : PagerAdapter() {

    override fun getCount(): Int {
        return faceModeList.size/20
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        var layout = `object` as View
        container.removeView(layout)
    }

    override fun instantiateItem(container: ViewGroup, pos: Int): Any {
        var startIndex: Int = pos * (20)
        var count: Int = faceModeList.size - startIndex
        count = Math.min(count, 20)

        mLinearLayout.setVisibility(View.VISIBLE)
        val gridView = GridView(mContext)
       // gridView.onItemClickListener = emojiListener
        gridView.adapter = EmotionAdapter(mContext, faceModeList.subList(startIndex, startIndex + count))
        gridView.numColumns = 7
        gridView.horizontalSpacing = 5
        gridView.verticalSpacing = 5
        gridView.gravity = Gravity.CENTER
        gridView.setSelector(R.drawable.emoji_item_selector)
        container.addView(gridView)
        return gridView
    }
}