package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.view.View
import androidx.viewpager.widget.ViewPager
import java.util.*

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
 abstract class BaseAdapter(item:List<Any>,viewPager: ViewPager) {
    var itemViewWidth = 0
    abstract fun getCount(): Int //获取总个数
    abstract fun getView(context: Context, position: Int): View //获取指定view
    abstract fun getAllTargetView(): List<View> //获取全部指定View
    abstract fun clickItem(position: Int)//item的点击事件


    abstract fun onPageScrollStateChangedd(state: Int)
    abstract fun onPageScrolledd(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    abstract fun onPageSelected(position: Int) //当某个item被选中 该变状态


    var onPagerScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? =
            null
    var onPageSelectedInLayout: ((position: Int) -> Unit)? = null

    fun addPagerScrollListener(viewPager: ViewPager, adapter: BaseAdapter.() -> Unit) {
        var adapter3 = this
        adapter.invoke(adapter3)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                onPageScrollStateChangedd(state)
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
                adapter3.onPagerScrolled?.let { it(position, positionOffset, positionOffsetPixels) }
                onPageScrolledd(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                adapter3.onPageSelectedInLayout?.let { it(position) }
                this@BaseAdapter.onPageSelected(position)
            }
        })
    }
}