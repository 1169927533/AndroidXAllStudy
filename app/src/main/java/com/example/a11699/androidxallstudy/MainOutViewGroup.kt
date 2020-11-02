package com.example.a11699.androidxallstudy

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.jar.Attributes

/**
 *Create time 2020/10/30
 *Create Yu
 *description:
 */
class MyViewGroup(private val resumedActivity: Activity) : FrameLayout(resumedActivity) {

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchViewDown = findEventSrcView(event, this)
                if (touchViewDown != null) {
                    Log.d("${this.javaClass.name}", "Activity:" + resumedActivity::class.java.name
                            + "- ACTION_DOWN:" + touchViewDown::class.java.name)
                }
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                val touchViewUp = findEventSrcView(event, this)
                if (touchViewUp != null) {
                    Log.d("${this.javaClass.name}", "Activity:" + resumedActivity::class.java.name
                            + "- ACTION_UP:" + touchViewUp::class.java.name)
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun findEventSrcView(event: MotionEvent, srcView: View): View? {
        if (srcView is ViewGroup) {
            val viewGroup = srcView
            val size = viewGroup.childCount
            for (i in 0 until size) {
                val view = viewGroup.getChildAt(i)
                if (view !is MyViewGroup && isEventInView(event, view)) {
                    val tmpRetView = findEventSrcView(event, view)
                    if (tmpRetView != null) {
                        return tmpRetView
                    }
                }
            }
        } else if (isEventInView(event, srcView)) {
            return srcView
        }
        return null
    }

    /**
     * 判断是否在view的rect范围内
     * @param event
     * @param srcView
     * @return
     */
    private fun isEventInView(event: MotionEvent, srcView: View): Boolean {
        val currentViewRect = Rect()
        if (srcView.getGlobalVisibleRect(currentViewRect)) {
            val rectF = RectF(currentViewRect)
            if (rectF.contains(event.rawX, event.rawY)) {
                return true
            }
        }
        return false
    }
}