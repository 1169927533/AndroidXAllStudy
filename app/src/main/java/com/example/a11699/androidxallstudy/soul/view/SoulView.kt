package com.example.a11699.androidxallstudy.soul.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
class SoulView : View {
    val paint by lazy {
        Paint()
    }
    constructor(context: Context) : this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val contentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val contentHeight = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val dimensionX = if (widthMode == MeasureSpec.EXACTLY) contentWidth else 50
        val dimensionY = if (heightMode == MeasureSpec.EXACTLY) contentHeight else 50

        setMeasuredDimension(dimensionX, dimensionY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(20 / 2.toFloat(), 20 / 2.toFloat(), 10f, paint)
    }
}