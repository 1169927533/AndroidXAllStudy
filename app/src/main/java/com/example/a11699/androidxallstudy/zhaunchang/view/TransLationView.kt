package com.example.a11699.androidxallstudy.zhaunchang.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator

/**
 *Create time 2020/10/5
 *Create Yu
 *description:
 */
class TransLationView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    var animatorAlpha: ValueAnimator? = null
    var animaor_Close: ValueAnimator? = null

    var mPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    var mRadius: Float = 0f


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mMaxScale = Math.ceil(Math.sqrt(measuredWidth * measuredWidth + measuredHeight * measuredHeight.toDouble())).toInt()
        prePareAnimator(mMaxScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2.toFloat(), height.toFloat() - 120f, mRadius, mPaint)
    }

    private fun prePareAnimator(width: Int) {
        if (animatorAlpha == null) {
            animatorAlpha = ValueAnimator.ofFloat(0f, width.toFloat()).apply {
                duration = 500
                interpolator = AccelerateInterpolator()

                addUpdateListener {
                    mRadius = (it.animatedValue as Float)
                    invalidate()
                }
            }
        }
        if (animaor_Close == null) {
            animaor_Close = ValueAnimator.ofFloat(width.toFloat(), 0f).apply {
                duration = 500
                interpolator = AccelerateInterpolator()

                addUpdateListener {
                    mRadius = (it.animatedValue as Float)
                    invalidate()
                }

            }
        }
    }

    fun startAnimal() {
        animatorAlpha!!.start()
    }

    fun startCloseAnimal() {
        animaor_Close!!.start()
    }

    fun stopAnimal() {
        animatorAlpha!!.cancel()
        animaor_Close!!.cancel()

    }
}