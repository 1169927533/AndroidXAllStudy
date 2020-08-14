package com.example.a11699.androidxallstudy.customview

/**
 *Create time 2020/8/14
 *Create Yu
 *description:动态控制viewpager是否可以进行左右滑动
 *
 */
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 *Create time 2020/6/23
 *Create Yu
 *description:
 */
class MyViewPager : ViewPager {
    private var noScroll = false
    private var mPx = 0f
    private var mCox = 0f
    var noticeShowToast: (() -> Unit)? = null //会掉到外面去弹引导付费的弹窗

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        noScroll = false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (canScroll(ev!!)) false else super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {

        return if (canScroll(arg0!!)) false else super.onInterceptTouchEvent(arg0)
    }

    fun canScroll(event: MotionEvent): Boolean {
        noScroll = false //这里是开关动态赋予这个值就可以改边滑动状态

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPx = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                mCox = event.x
                if (noScroll && (abs(mCox - mPx) > 50)) {
                    noticeShowToast?.let {
                        it.invoke()
                    }
                }
            }

        }



        return noScroll
    }

}
