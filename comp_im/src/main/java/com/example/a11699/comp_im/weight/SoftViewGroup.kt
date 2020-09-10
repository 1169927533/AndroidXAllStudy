package com.example.a11699.comp_im.weight

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.a11699.comp_im.util.InputUtil
import com.example.a11699.lib_im.util.ScreenUtil

/**
 *Create time 2020/9/9
 *Create Yu
 *description:父布局 里面必须包含两个子view
 * 用来承装输入框 和 显示界面
 */
class SoftViewGroup : LinearLayout {
    var mContext: Context? = null
    var childHeightList = ArrayList<Int>() //保存每个子view的高度
    var mShouldShowMore = 0 //0第一次进入页面  1展示更多内容  2不展示更多内容

    var mEditText: EditText? = null
    fun setValeToShowMore(shouldShowMore: Int) {
        mShouldShowMore = shouldShowMore
        when (mShouldShowMore) {
            0 -> { //全部都是收起状态
                InputUtil.hideSoftInput(this, mContext!!)
            }
            1 -> { //展示更多 软键盘处于打开状态
                if (ScreenUtil.isSoftShowing(this)) {
                    InputUtil.hideSoftInput(this, mContext!!)
                } else {
                    requestLayout()
                }
            }
            2 -> { //不展示更多 软键盘谈起
                InputUtil.showSoftInout(mEditText!!, mContext!!)
            }
        }

    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        mContext = context
        orientation = VERTICAL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthParent = MeasureSpec.getSize(widthMeasureSpec)
        var heightParent = MeasureSpec.getSize(heightMeasureSpec)
        childHeightList.clear()
        for (index in 0 until childCount) {
            var childView = getChildAt(index)
            //测量子view
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            var cHeight = childView.measuredHeight
            childHeightList.add(cHeight)
        }
        setMeasuredDimension(widthParent, heightParent)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var secondViewHeight = 0//第二个view展示高度
        var topVisibilityHeight = ScreenUtil.getVisibleHeight(this)//顶部可见距离
        var screenHeight = ScreenUtil.getScreenTotalHeight(this) - ScreenUtil.getTitleBarHeight(this, true)//整个屏幕的高度

        if (childCount < 2) {
            throw IllegalStateException("我的这个控件必须包含至少两个控件才可以！")
        }

        when (mShouldShowMore) {
            0 -> { //全部都是收起状态
                secondViewHeight = 0
            }
            1 -> { //展示更多 软键盘处于打开状态
                secondViewHeight = childHeightList[1]
            }
            2 -> { //不展示更多 软键盘谈起
                secondViewHeight = screenHeight + ScreenUtil.getTitleBarHeight(this, true) - topVisibilityHeight
            }
        }
        setTransition(500)

        for ((index, childView) in children.withIndex()) {
            var layoutParams = childView.layoutParams
            when (index) {
                0 -> {
                    childView.layout(l, t - secondViewHeight, r, screenHeight - secondViewHeight)
                    if (layoutParams.height != childHeightList[0]) {
                        layoutParams.height = childHeightList[0]
                        childView.layoutParams = layoutParams
                    }
                }
                1 -> {
                    childView.layout(l, screenHeight - secondViewHeight, r, screenHeight + secondViewHeight)
                    if (layoutParams.height != childHeightList[1]) {
                        layoutParams.height = childHeightList[1]
                        childView.layoutParams = layoutParams
                    }
                }
            }
        }
    }

    /**
     * 设置动画时长
     *
     * @param duration
     */
    private fun setTransition(duration: Long) {
        val changeBounds = ChangeBounds()
        changeBounds.duration = duration
        TransitionManager.beginDelayedTransition(this@SoftViewGroup, changeBounds)
    }
}