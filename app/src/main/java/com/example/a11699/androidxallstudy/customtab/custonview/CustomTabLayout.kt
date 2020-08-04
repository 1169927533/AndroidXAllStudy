package com.example.a11699.androidxallstudy.customtab.custonview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.adapter.BaseAdapter
import com.example.a11699.androidxallstudy.myseekbar.ScreenUtil
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import com.example.a11699.comp_base.Util

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class CustomTabLayout : RelativeLayout {

    var navWidth = 0
    var widthSpace: Int = 0
    lateinit var navLineView: View
    lateinit var mAdapter: BaseAdapter
    var hasBottomLine = false //时候有底部条
    var average = false //是否平均每个item
    var itemMarginRight = 0f//每个item距离右边的距离
    var bottomlinewidth = 0f//底部线的宽度
    var bottomlineheight = 0f//底部线的高度
    var bottomlinecolor = 0//底部线的高度

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.customTab)
        hasBottomLine = typeArray.getBoolean(R.styleable.customTab_hasBottomLine, true)
        average = typeArray.getBoolean(R.styleable.customTab_average, true)
        itemMarginRight = typeArray.getDimension(R.styleable.customTab_itemmarginright, 0f)
        bottomlinewidth = typeArray.getDimension(R.styleable.customTab_bottomlinewidth, 0f)
        bottomlineheight = typeArray.getDimension(R.styleable.customTab_bottomlineheight, 0f)
        bottomlinecolor = typeArray.getResourceId(R.styleable.customTab_bottomlinecolor, R.drawable.item_tab_select)

        typeArray.recycle()
    }

    fun setAdapter(adapter: BaseAdapter, viewPager: ViewPager) {
        mAdapter = adapter
        initViewPager(viewPager)
        removeAllViews()
        var titleLayout = LinearLayout(context)
        var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        titleLayout.orientation = LinearLayout.HORIZONTAL
        layoutParams.gravity = Gravity.CENTER
        titleLayout.layoutParams = layoutParams

        for (index in 0 until adapter.getCount()) {
            Util.printLog(index.toString())
            var view = adapter.getView(context, index)
            if (index <= adapter.getCount() - 2) {
                view.layoutParams?.let {
                    var params = it as LinearLayout.LayoutParams
                    params.rightMargin = itemMarginRight.toInt()
                    view.layoutParams = params
                }
            }
            titleLayout.addView(view)
        }

        widthSpace = if (average) {
            ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }

        val lp1 = LayoutParams(widthSpace, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp1.addRule(CENTER_VERTICAL, TRUE)//是每个view垂直居中
        mAdapter.onPageSelected(0)
        initBottomView()
        addView(titleLayout, lp1)
    }

    private fun initBottomView() {
        if (hasBottomLine) {
            setNavLine(bottomlinewidth, bottomlineheight, bottomlinecolor, 0)
        }
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
            width / mAdapter.getCount()
        } else {
            linWidth.toInt()
        }
        var navHeight = lineHeight.toInt()
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
        val lp = line.layoutParams as LayoutParams
        var widthh = (width - itemMarginRight * (mAdapter.getCount() - 1)) / mAdapter.getCount()
        var wioiii = (widthh + itemMarginRight )
        var marginleftt = position * wioiii + (wioiii * percent).toInt() + (widthh - navWidth) / 2

        lp.width = (navWidth - percent * 2).toInt()
        lp.setMargins((marginleftt + percent).toInt(), 0, percent.toInt(), 0)
        Util.printLog((marginleftt + percent).toString())
        line.requestLayout()

    }

    private fun initViewPager(mViewPager: ViewPager) {
        mAdapter.addPagerScrollListener(viewPager = mViewPager) {
            onPagerScrolled = { position, positionOffset, _ ->
                if (hasBottomLine) {
                    moveBar(navLineView, navWidth, positionOffset, position)
                }
            }
        }
    }
}