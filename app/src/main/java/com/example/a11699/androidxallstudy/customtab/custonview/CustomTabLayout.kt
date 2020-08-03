package com.example.a11699.androidxallstudy.customtab.custonview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.adapter.BaseAdapter
import com.example.a11699.androidxallstudy.customtab.fragment.ItemFragmetn
import com.example.a11699.androidxallstudy.myseekbar.ScreenUtil
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import com.example.a11699.comp_base.Util
import kotlinx.android.synthetic.main.activity_tab.*

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class CustomTabLayout : RelativeLayout {

    var navWidth = 0
    lateinit var navLineView: View
    lateinit var mAdapter: BaseAdapter
    var hasBottomLine = false //时候有底部条

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.customTab)
        hasBottomLine = typeArray.getBoolean(R.styleable.customTab_hasBottomLine, true)
        typeArray.recycle()
    }

    fun setAdapter(adapter: BaseAdapter, viewPager: ViewPager) {
        initViewPager(viewPager)
        removeAllViews()
        var titleLayout = LinearLayout(context)
        var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        titleLayout.orientation = LinearLayout.HORIZONTAL
        titleLayout.layoutParams = layoutParams
        for (index in 0 until adapter.getCount()) {
            Util.printLog(index.toString())
            var view = adapter.getView(context, index)

            var layoutParams2 = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams2.weight = 1f
            layoutParams2.gravity = Gravity.CENTER

            view.layoutParams = layoutParams2

            titleLayout.addView(view)
        }
        mAdapter = adapter
        var lll = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lll.addRule(CENTER_VERTICAL, TRUE)
    //    mAdapter.changItemState(0)
        addView(titleLayout, lll)
    }

    /**
     *linWidth      一个item的宽度 如果为0 那么就平均一下屏幕宽度  否则用自己的宽度
     * lineHeight   一个item的高度
     * color        一个item的颜色
     * currentPosition 当前选中哪个
     */
    @SuppressLint("ResourceType")
    fun setNavLine(linWidth: Float, lineHeight: Float, color: Int, currentPosition: Int) {
        navWidth = if (linWidth == 0f) {
            ScreenUtil.getScreenWidth() / mAdapter.getCount()
        } else {
            ViewUtil.dip2px(linWidth)
        }
        var navHeight = ViewUtil.dip2px(lineHeight)
        var layoutParams = RelativeLayout.LayoutParams(navWidth, navHeight)
        navLineView = View(context)
        navLineView.layoutParams = layoutParams
        navLineView.setBackgroundResource(color)

        val lp = LayoutParams(navWidth, navHeight)
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE)

        addView(navLineView, lp)
        moveBar(navLineView, navWidth, 0f, currentPosition)
    }


    /**
     * line 需要移动的view
     * navWidth 一个view的宽度
     * percent  偏移量
     */
    fun moveBar(line: View, navWidth: Int, percent: Float, position: Int) {

        var width1 = width / mAdapter.getCount()
        val lp = line.layoutParams as LayoutParams
        val marginleft = position * width1 + (width1 * percent).toInt() + (width1 - navWidth) / 2
        lp.width = (navWidth - percent * 2).toInt()
        lp.setMargins((marginleft + percent).toInt(), 0, percent.toInt(), 0)
        line.requestLayout()

    }

    private fun initViewPager(mViewPager: ViewPager) {
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (hasBottomLine) {
                    moveBar(navLineView, navWidth, positionOffset, position)
                }

                if (mAdapter.getAllTargetView()[position] is ColorTrackTextView) {
                    var mLeft = mAdapter.getAllTargetView()[position] as ColorTrackTextView

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
                        var mRight = mAdapter.getAllTargetView()[position + 1] as ColorTrackTextView
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

            override fun onPageSelected(position: Int) {
                mAdapter.changItemState(position)
            }
        })
    }

}