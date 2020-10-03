package com.example.a11699.androidxallstudy.breath

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.example.a11699.androidxallstudy.R

/**
 *Create time 2020/10/3
 *Create Yu
 *description:光炫效果
 * 方案1 ： 使用BlurMaskFilter 来绘制阴影 通过动态控制阴影半径来实现动画
 */
class GloriousView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var lightPaint: Paint? = null
    private var centerX = 0
    private var centerY: Int = 0

    /** 发光范围  */
    private var radioRadius = 70f
    lateinit var animator: ValueAnimator

    init {
        lightPaint = Paint()
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        lightPaint!!.color = Color.parseColor("#EC3E3E")
        animator = ValueAnimator.ofFloat(90f, 150f).apply {
            duration = 1000
            interpolator = AccelerateInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener {
                radioRadius = it.animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        centerX = left + measuredWidth / 2
        centerY = top + measuredHeight / 2
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lightPaint!!.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.OUTER)
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), 170f, lightPaint!!)

    }

    fun setBlurType(blurType: Int) {
        when (blurType) {
            0 -> lightPaint!!.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.INNER)
            1 -> lightPaint!!.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.NORMAL)
            2 -> lightPaint!!.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.SOLID)
            3 -> lightPaint!!.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.OUTER)
        }
        invalidate()
    }

    fun startAnimal() {
        animator.start()
    }

    fun stopAnimal() {
        animator.cancel()
    }
}