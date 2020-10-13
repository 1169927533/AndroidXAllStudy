package com.example.a11699.androidxallstudy.breath

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.example.a11699.androidxallstudy.R
import com.example.a11699.lib_util.dp

/**
 *Create time 2020/10/3
 *Create Yu
 *description:仿照鲱鱼罐头实现的呼吸灯
 */
class BreathView2(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    var inColor = Color.BLUE
    var parentWidth = 0
    var inTextContent: String = ""//展示的文字内容
    var distance2singline: Float = 0f//内部圆形距离边线的距离
    var inTextContentSize = 0f//展示文字大小
    var shadowColor: Int = Color.BLACK
    private var radioRadius = 1f
    var animatorRadius: ValueAnimator? = null
    var animatorAlpha: ValueAnimator? = null

    var paintM: Paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    } //绘制里面的圆

    var paintText = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }//绘制文字

    private var paintRadiusShadow = Paint().apply {
        style = Paint.Style.FILL
    }//绘制 通过控制半径控制 阴影
    private var paintAlphaShadow = Paint().apply {
        style = Paint.Style.FILL
    }//绘制 通过控制透明度控制 阴影

    init {
        val typedArray: TypedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.BreatheView2)
        inColor = typedArray.getColor(R.styleable.BreatheView2_incolor, inColor)
        distance2singline = typedArray.getDimension(R.styleable.BreatheView2_distance2singleline, distance2singline)
        inTextContent = typedArray.getString(R.styleable.BreatheView2_intext).toString()
        inTextContentSize = typedArray.getDimension(R.styleable.BreatheView2_intextSize, 4f)
        shadowColor = typedArray.getColor(R.styleable.BreatheView2_shadowcolor, shadowColor)
        typedArray.recycle()

        paintM.color = inColor
        paintText.textSize = inTextContentSize
        paintAlphaShadow.color = shadowColor
        paintAlphaShadow.maskFilter = BlurMaskFilter(distance2singline, BlurMaskFilter.Blur.OUTER)


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = measuredWidth
        setMeasuredDimension(parentWidth, parentWidth)
        if (animatorRadius == null) {
            animatorRadius = ValueAnimator.ofFloat(1f, distance2singline).apply {
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
        if (animatorAlpha == null) {
            animatorAlpha = ValueAnimator.ofFloat(0f, 255f).apply {
                duration = 1000
                interpolator = AccelerateInterpolator()
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE

                addUpdateListener {
                    paintAlphaShadow.alpha = (it.animatedValue as Float).toInt()
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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制圆
        drawCircle(canvas)
        //绘制文字内容
        drawTextContent(canvas)
        //绘制 通过控制半径控制 阴影
        drawCircleShade(canvas)
        //绘制 通过控制透明度控制 阴影
        drawAlphaShade(canvas)
    }

    //绘制底层圆形
    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(parentWidth / 2.toFloat(), parentWidth / 2.toFloat(), parentWidth / 2.toFloat() - distance2singline, paintM)
    }

    //绘制文字内容
    private fun drawTextContent(canvas: Canvas) {
        var rectText = Rect()
        paintText.getTextBounds(inTextContent, 0, inTextContent.length, rectText)
        var dx = parentWidth / 2 - rectText.width() / 2
        var dy = (paintText.fontMetricsInt.bottom - paintText.fontMetricsInt.top) / 2 - paintText.fontMetricsInt.bottom
        var baseLine = parentWidth / 2 + dy
        canvas?.drawText(inTextContent, dx.toFloat(), baseLine.toFloat(), paintText)
    }

    //绘制 通过控制半径控制 阴影
    private fun drawCircleShade(canvas: Canvas) {
        paintRadiusShadow.color = shadowColor
        paintRadiusShadow.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.OUTER)
        canvas.drawCircle(parentWidth / 2.toFloat(), parentWidth / 2.toFloat(), parentWidth / 2.toFloat() - distance2singline, paintRadiusShadow)
    }

    //绘制 通过控制透明度控制 阴影
    private fun drawAlphaShade(canvas: Canvas) {
        canvas.drawCircle(parentWidth / 2.toFloat(), parentWidth / 2.toFloat(), parentWidth / 2.toFloat() - distance2singline, paintAlphaShadow)
    }

    fun startAnimal() {
        animatorRadius!!.start()
        animatorAlpha!!.start()
    }

    fun stopAnimal() {
        animatorRadius!!.cancel()
        animatorAlpha!!.cancel()

    }
}