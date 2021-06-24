package com.example.a11699.lib_customview.softinputLinearlayout

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.example.a11699.lib_customview.util.InputUtil
import com.example.a11699.lib_customview.util.ScreenUtil


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
    var click_soft = 0
    fun setValeToShowMore(shouldShowMore: Int) {
        mShouldShowMore = shouldShowMore
        when (mShouldShowMore) {
            0 -> { //全部都是收起状态
                InputUtil.hideSoftInput(this, mContext!!)
            }
            1 -> { //展示更多 软键盘处于打开状态
                if (ScreenUtil.isSoftShowing(this)) {
                    InputUtil.hideSoftInput(this, mContext!!)
                    click_soft = 1
                } else {
                    if (click_soft == 1) {
                        //   mShouldShowMore = 0
                        //   InputUtil.showSoftInout(mEditText!!, mContext!!)
                        requestLayout()
                    } else {
                        requestLayout()
                    }
                    click_soft = 0
                }
            }
            2 -> { //不展示更多 软键盘弹起
                InputUtil.showSoftInout(mEditText!!, mContext!!)
            }
            3 -> { //全部不展示
                InputUtil.hideSoftInput(this, mContext!!)
                requestLayout()
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
        if (childHeightList.size == 0) {
            for (index in 0 until childCount) {
                var childView = getChildAt(index)
                //测量子view
                measureChild(childView, widthMeasureSpec, heightMeasureSpec)
                var cHeight = childView.measuredHeight
                childHeightList.add(cHeight)
            }
            setMeasuredDimension(widthParent, heightParent)
        }

    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
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
    //    setTransition(200)

        for ((index, childView) in children.withIndex()) {
            var layoutParams = childView.layoutParams
            when (index) {
                0 -> {
                    var s = screenHeight - (topVisibilityHeight - ScreenUtil.getTitleBarHeight(this, true))
                    if (mShouldShowMore == 1) {
                        childView.layout(l, 0, r, screenHeight - secondViewHeight)
                        if (layoutParams.height != topVisibilityHeight - ScreenUtil.getTitleBarHeight(this, true) - childHeightList[1]) {
                            layoutParams.height = topVisibilityHeight - ScreenUtil.getTitleBarHeight(this, true) - childHeightList[1]
                            childView.layoutParams = layoutParams
                        }
                    } else {
                        childView.layout(l, s - secondViewHeight, r, screenHeight - secondViewHeight)
                        if (layoutParams.height != topVisibilityHeight - ScreenUtil.getTitleBarHeight(this, true)) {
                            layoutParams.height = topVisibilityHeight - ScreenUtil.getTitleBarHeight(this, true)
                            childView.layoutParams = layoutParams
                        }
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
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setTransition(duration: Long) {
        val changeBounds = ChangeBounds()
        changeBounds.duration = duration
        TransitionManager.beginDelayedTransition(this@SoftViewGroup, changeBounds)
    }


    fun moveToTopAnimal(view: View, distance: Float) {
        ObjectAnimator.ofFloat(view, "translationY", distance)
                .apply {
                    duration = 2000
                    start()
                }
    }
}