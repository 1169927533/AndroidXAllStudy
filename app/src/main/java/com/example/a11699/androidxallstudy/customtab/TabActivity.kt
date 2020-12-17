package com.example.a11699.androidxallstudy.customtab

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.adapter.TabAdapter
import com.example.a11699.androidxallstudy.customtab.adapter.TabAdapter2
import com.example.a11699.androidxallstudy.customtab.adapter.TabAdapter3
import com.example.a11699.androidxallstudy.customtab.adapter.TabAdapter_Splash
import com.example.a11699.androidxallstudy.customtab.custonview.ColorTrackTextView
import com.example.a11699.androidxallstudy.customtab.custonview.CustomTabLayout
import com.example.a11699.androidxallstudy.customtab.fragment.ItemFragmetn
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import kotlinx.android.synthetic.main.activity_tab.*
import kotlin.math.abs

/**
 *Create time 2020/8/2
 *Create Yu
 *description:
 */
class TabActivity : AppCompatActivity() {
    var items = arrayListOf<String>("直播", "推荐", "视频")
    var mIndicators = ArrayList<ColorTrackTextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        customView.setOnClickListener {
            if (customView.isSelected) {
                customView.isSelected = false
                rightToLeft()
            } else {
                customView.isSelected = true
                leftToRight()
            }
        }
        initIndicator()

        initViewPager()

        initCustomTab()
    }


    private fun initIndicator() {
        for (value in items) {
            var params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            var colorTrackTextView = ColorTrackTextView(this)
            colorTrackTextView.initOriginPaint(ViewUtil.dip2px(14f).toFloat(), Color.BLACK, Color.RED)

            colorTrackTextView.layoutParams = params
            colorTrackTextView.text = value
            mIndicators.add(colorTrackTextView)
            indicator_view.addView(colorTrackTextView)
        }
    }

    private fun initViewPager() {
        view_parent.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ItemFragmetn.newInstance(items[position])
            }

            override fun getCount(): Int {
                return items.count()
            }
        }
        view_parent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                var left = mIndicators.get(position)
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
                left.setCurrentProcess((1 - positionOffset).toFloat())

                try {
                    var right = mIndicators.get(position + 1)
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                    right.setCurrentProcess((positionOffset).toFloat())
                } catch (e: Exception) {
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })


        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ItemFragmetn.newInstance(items[position])
            }

            override fun getCount(): Int {
                return items.count()
            }
        }

    }

    fun leftToRight() {
        var animator = ObjectAnimator.ofFloat(0f, 1f)
        animator.duration = 2000
        animator.start()
        animator.addUpdateListener {
            var currentProcess = it.animatedValue as Float
            customView.setCurrentProcess(currentProcess)
        }
    }

    fun rightToLeft() {
        var animator = ObjectAnimator.ofFloat(1f, 0f)
        animator.duration = 2000
        animator.start()
        animator.addUpdateListener {
            var currentProcess = it.animatedValue as Float
            customView.setCurrentProcess(currentProcess)
        }
    }

    private fun initCustomTab() {
        var adapter = TabAdapter(items, viewPager)
        var imgView2 = ImageView(this)
        imgView2.setBackgroundResource(R.drawable.shape_btn)
        var layoutParams2 = RelativeLayout.LayoutParams(ViewUtil.dip2px(16f), ViewUtil.dip2px(3f))
        imgView2.layoutParams = layoutParams2
        customTabLayout.initBottomView(imgView2)


        customTabLayout.setAdapter(adapter, viewPager,0)


        var adapter2 = TabAdapter2(items, viewPager)
        customTabLayout1.setAdapter(adapter2, viewPager,0)


        var adapter3 = TabAdapter3(items, viewPager)
        //初始化底部view
        var imgView = ImageView(this)
        imgView.setBackgroundResource(R.drawable.shape_btn)
        var layoutParams = RelativeLayout.LayoutParams(ViewUtil.dip2px(16f), ViewUtil.dip2px(3f))
        imgView.layoutParams = layoutParams
        customTabLayout2.initBottomView(imgView)
        customTabLayout2.setAdapter(adapter3, viewPager,0)


        var tab_splash_adapter = TabAdapter_Splash(items, view_parent)
        customTabLayout_splash.setAdapter(tab_splash_adapter, view_parent,0)

    }
}