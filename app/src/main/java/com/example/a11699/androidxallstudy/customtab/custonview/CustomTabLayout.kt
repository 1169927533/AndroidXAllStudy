package com.example.a11699.androidxallstudy.customtab.custonview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
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
 * v:1.0 实现自定义的顶部菜单栏
 * v:2.0 实现顶部菜单栏的滑动，当菜单量超过屏幕宽度，然后点击下一个itme的时候菜单栏会进行滑动
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
    var horizontalScrollView: CusHorizontalScrollView? = null
    var itemViewWidth: Int = 0

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.customTab)
        hasBottomLine = typeArray.getBoolean(R.styleable.customTab_hasBottomLine, true)
        average = typeArray.getBoolean(R.styleable.customTab_average, true)
        itemMarginRight = typeArray.getDimension(R.styleable.customTab_itemmarginright, 0f)
        bottomlinewidth = typeArray.getDimension(R.styleable.customTab_bottomlinewidth, 0f)
        bottomlineheight = typeArray.getDimension(R.styleable.customTab_bottomlineheight, 0f)
        bottomlinecolor = typeArray.getResourceId(
                R.styleable.customTab_bottomlinecolor,
                R.drawable.item_tab_select
        )

        typeArray.recycle()
    }

    fun setAdapter(adapter: BaseAdapter, viewPager: ViewPager) {
        mAdapter = adapter
        initViewPager(viewPager)

        removeAllViews()

        var titleLayout = LinearLayout(context)
        var layoutParams2222 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        titleLayout.orientation = LinearLayout.HORIZONTAL
        layoutParams2222.gravity = Gravity.CENTER
        titleLayout.layoutParams = layoutParams2222
        titleLayout!!.setBackgroundResource(R.color.colorFFFFFF)



        for (index in 0 until adapter.getCount()) {
            var view = adapter.getView(context, index)
            itemViewWidth = adapter.itemViewWidth
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

        horizontalScrollView = CusHorizontalScrollView(context)
        val layoutParams2 = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        horizontalScrollView!!.isFillViewport = true
        horizontalScrollView!!.addView(titleLayout, layoutParams2)
        horizontalScrollView!!.isHorizontalScrollBarEnabled = false
        addView(horizontalScrollView, lp1)
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

        if (hasBottomLine) {
            navLineView.layoutParams = layoutParams
            val lp = LayoutParams(navWidth, navHeight)
            lp.addRule(ALIGN_PARENT_BOTTOM, TRUE)

            addView(navLineView, lp)
            moveBar(navLineView, navWidth, 0f, currentPosition)
        }
    }

    fun initBottomView(view: View) {
        navLineView = view
    }


    /**
     * line 需要移动的view
     * navWidth 一个view的宽度
     * percent  偏移量
     */
    fun moveBar(line: View, navWidth: Int, percent: Float, position: Int) {
        val lp = line.layoutParams as LayoutParams
        var widthh = (width - itemMarginRight * (mAdapter.getCount() - 1)) / mAdapter.getCount()
        var wioiii = (widthh + itemMarginRight)
        var marginleftt = position * wioiii + (wioiii * percent).toInt() + (widthh - navWidth) / 2

        lp.width = (navWidth - percent * 2).toInt()
        lp.setMargins((marginleftt + percent).toInt(), 0, percent.toInt(), 0)
        line.requestLayout()


    }

    private fun initViewPager(mViewPager: ViewPager) {
        mAdapter.addPagerScrollListener(viewPager = mViewPager) {
            onPagerScrolled = { position, positionOffset, positionOffsetPixels ->
                if (hasBottomLine) {
                    moveBar(navLineView, navWidth, positionOffset, position)
                }
                onPageScrollChanged(position, positionOffset)
            }
            onPageSelectedInLayout = { position ->
                onPageLayoutSelected(position)
            }
        }
    }

    private fun onPageLayoutSelected(position: Int) {
        if (canSmoothToRight(position) && (position + 2) * itemViewWidth > width) {

        }
    }

    private fun canSmoothToRight(position: Int): Boolean {
        return (mAdapter.getCount() - position) * itemViewWidth >= width
    }

    private fun onPageScrollChanged(position: Int, positionOffset: Float) {
        if (mAdapter != null) {
            horizontalScrollView?.let {
                // 手指跟随滚动
                if (position >= 0 && position < mAdapter.getCount()) {

                    val currentPosition =
                            Math.min(mAdapter.getCount() - 1, position)
                    val nextPosition =
                            Math.min(mAdapter.getCount() - 1, position + 1)

                    val scrollTo: Float =
                            ((currentPosition * itemViewWidth) + itemViewWidth / 2) + itemMarginRight - it.width * (0.5f)

                    val nextScrollTo: Float =
                            ((nextPosition * itemViewWidth) + itemViewWidth / 2) + itemMarginRight - it.width * (0.5f)


                    it.scrollTo(
                            (scrollTo + (nextScrollTo - scrollTo) * positionOffset).toInt(),
                            0
                    )
                }
            }


        }


    }

    class CusHorizontalScrollView : HorizontalScrollView {
        private var onScrollChangedListener: OnScrollChangedListener? = null

        constructor(context: Context?) : super(context) {}
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
                context,
                attrs,
                defStyleAttr
        ) {
        }

        fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener?) {
            this.onScrollChangedListener = onScrollChangedListener
        }

        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            super.onScrollChanged(l, t, oldl, oldt)
            if (onScrollChangedListener != null) {
                onScrollChangedListener!!.onScrollChanged(l, t, oldl, oldt)
            }
        }
    }

    interface OnScrollChangedListener {
        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
    }
}