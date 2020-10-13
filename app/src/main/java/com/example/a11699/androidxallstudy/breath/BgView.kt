package com.example.a11699.androidxallstudy.breath

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.example.a11699.androidxallstudy.R
import com.example.a11699.lib_util.dp

/**
 *Create time 2020/10/10
 *Create Yu
 *description:
 */
class BgView(mContext: Context, attributeSet: AttributeSet) : View(mContext, attributeSet) {
    private var parentWidth = 0
    private var parentHeight = 0
    private var mPaint = Paint().apply {
        color = mContext.resources.getColor(R.color.color_FFD7DD)
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(10f.dp, BlurMaskFilter.Blur.OUTER)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = measuredWidth
        parentHeight = measuredHeight
        setMeasuredDimension(parentWidth, parentHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(paddingLeft.toFloat(), paddingTop.toFloat(), parentWidth.toFloat() - paddingRight, parentHeight.toFloat() - paddingBottom, mPaint)
    }

}