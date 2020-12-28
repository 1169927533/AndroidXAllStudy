package com.pince.customview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @Author Yu
 * @Date 2020/12/28 14:52
 * @Description 扫描view
 */
class RadarView : View {
    lateinit var scanShader: Shader
    private var mWidth = 0
    private var mHeight = 0
      val radarScanAnimal by lazy {
        ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
            duration = 2000
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
        }
    }
    private val mPaintScan by lazy {
        Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            shader = scanShader
            isAntiAlias = true
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(widthMeasureSpec))
        mWidth = measuredWidth
        mHeight = measuredHeight
        mWidth = mWidth.coerceAtMost(mHeight)
        mHeight = mWidth
        scanShader = SweepGradient((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), intArrayOf(Color.parseColor("#460201FF"), Color.parseColor("#4DF716FF")), null)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawCircle(mWidth / 2.toFloat(), mHeight / 2.toFloat(), mWidth * 5 / 13f, mPaintScan)
    }

    private fun measureSize(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = 300
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }


}