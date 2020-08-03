package com.example.a11699.androidxallstudy.customtab.custonview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.example.a11699.androidxallstudy.R

/**
 *Create time 2020/8/2
 *Create Yu
 *description:
 */
class ColorTrackTextView : androidx.appcompat.widget.AppCompatTextView {
    val mOriginPaint: Paint by lazy {
        Paint()
    }
    val mChangePaint: Paint by lazy {
        Paint()
    }

    var mCurrentProcess = 0.0f
    var mDirection: Direction = Direction.LEFT_TO_RIGHT

    enum class Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initPaint(context, attributeSet!!)
    }


    private fun initPaint(context: Context, attributeSet: AttributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ColorTrackTextView)
        var originColor = typeArray.getColor(R.styleable.ColorTrackTextView_originColor, textColors.defaultColor)
        var changeColor = typeArray.getColor(R.styleable.ColorTrackTextView_changeColor, textColors.defaultColor)
        getPaintByColor(mOriginPaint, originColor)
        getPaintByColor(mChangePaint, changeColor)

        typeArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        var middle = mCurrentProcess * width

        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas!!, mChangePaint, 0, middle.toInt())
            drawText(canvas!!, mOriginPaint, middle.toInt(), width.toInt())
        } else {
            drawText(canvas!!, mChangePaint, (width - middle).toInt(), width)
            drawText(canvas!!, mOriginPaint, 0, (width - middle).toInt())
        }

    }

    private fun drawText(canvas: Canvas, paint: Paint, start: Int, end: Int) {
        canvas?.save()
        //绘制不变色的
        var clipRect = Rect(start, 0, end, height)
        canvas?.clipRect(clipRect)//这个api实现裁剪画布 必须在画之前调用


        var textString = text.toString()
        //获取字体宽度
        var bounds = Rect()
        paint.getTextBounds(textString, 0, textString.length, bounds)
        var x = width / 2 - bounds.width() / 2

        var fontMetrics = paint.fontMetricsInt
        var dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        var baseLine = height / 2 + dy
        canvas?.drawText(textString, x.toFloat(), baseLine.toFloat(), paint)
        canvas?.restore()
    }

    private fun getPaintByColor(paint: Paint, color: Int): Paint {
        paint.color = color
        paint.isAntiAlias = true //抗锯齿
        paint.isDither = true    //仿抖动
        paint.textSize = textSize
        return paint
    }

    fun initOriginPaint(textSize: Float, originColor: Int, changeColor: Int) {
        mOriginPaint.isAntiAlias = true //抗锯齿
        mOriginPaint.isDither = true    //仿抖动
        mOriginPaint.textSize = textSize
        mOriginPaint.color = originColor

        mChangePaint.isAntiAlias = true //抗锯齿
        mChangePaint.isDither = true    //仿抖动
        mChangePaint.textSize = textSize
        mChangePaint.color = changeColor

    }

    fun setDirection(direction: Direction) {
        mDirection = direction
       // invalidate()
    }

    fun setCurrentProcess(process: Float) {
        mCurrentProcess = process
        invalidate()
    }


}