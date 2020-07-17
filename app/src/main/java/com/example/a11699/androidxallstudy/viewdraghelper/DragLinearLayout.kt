package com.example.a11699.androidxallstudy.viewdraghelper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper

/**
 *Create time 2020/7/11
 *Create Yu
 *description:
 */
class DragLinearLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    lateinit var mViewDragHelper: ViewDragHelper
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mViewDragHelper!!.shouldInterceptTouchEvent(ev!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mViewDragHelper!!.processTouchEvent(event!!)
        return true
    }

    override fun computeScroll() {
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    init {
        mViewDragHelper = ViewDragHelper.create(this, object : ViewDragHelper.Callback() {
            private var mLeft = 0
            private var mTop = 0
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return true
            }

            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)
                mLeft = capturedChild.left
                mTop = capturedChild.top
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                mViewDragHelper.settleCapturedViewAt(mLeft, mTop)
                invalidate()
            }
        })
    }
}