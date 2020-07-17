package com.example.a11699.comp_customview.stepview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.view.marginRight
import com.example.a11699.comp_customview.R
import java.util.jar.Attributes


/**
 *Create time 2020/7/5
 *Create Yu
 *description:仿qq步数
 */
class QQStepView : View {
    var mInnerColor: Int = 0
    var mOuterColor: Int = 0
    var mStepTextColor: Int = 0
    var mBorderWidth: Int = 0
    var mStepTextSize: Int = 0
    lateinit var mOutPaint: Paint
    lateinit var mInnerPaint: Paint
    lateinit var mTextPaint: Paint
    var mStepMax = 100f
    var mCurrentStep = 50f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var array = context.obtainStyledAttributes(attributeSet, R.styleable.QQStepView)
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, Color.BLACK)
        mOuterColor = array.getColor(R.styleable.QQStepView_outColor, Color.BLACK)
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, Color.BLACK)
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)
        mBorderWidth = array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth.toFloat()).toInt()
        array.recycle()

        mOutPaint = Paint()
        mOutPaint.color = mOuterColor
        mOutPaint.isAntiAlias = true
        mOutPaint.strokeWidth = mBorderWidth.toFloat()
        mOutPaint.strokeCap = Paint.Cap.ROUND
        mOutPaint.style = Paint.Style.STROKE

        mInnerPaint = Paint()
        mInnerPaint.color = mInnerColor
        mInnerPaint.isAntiAlias = true
        mInnerPaint.strokeCap = Paint.Cap.ROUND
        mInnerPaint.strokeWidth = mBorderWidth.toFloat()
        mInnerPaint.style = Paint.Style.STROKE

        mTextPaint = Paint()
        mTextPaint.color = mStepTextColor
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mStepTextSize.toFloat()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //宽度高度不一致给一个正方形
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(if (width > height) {
            height
        } else {
            width
        }, if (width > height) {
            height
        } else {
            width
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //1.画外弧
        var rectF: RectF = RectF((mBorderWidth / 2).toFloat(), (mBorderWidth / 2).toFloat(), (width - mBorderWidth / 2).toFloat(), (height - mBorderWidth / 2).toFloat())
        //usetCenter表示是否闭合
        canvas?.drawArc(rectF, 135f, 270f, false, mOutPaint)

        //2.画内
        if (mStepMax == 0f) {
            return
        }
        var sweepAngle = (mCurrentStep / mStepMax)
        canvas?.drawArc(rectF, 135f, sweepAngle * 270, false, mInnerPaint)

        //3.画文字
        var stepText = mCurrentStep.toInt().toString()
        var rectText = Rect()
        mTextPaint.getTextBounds(stepText, 0, stepText.length, rectText)
        var dx = width / 2 - rectText.width() / 2
        var dy = (mTextPaint.getFontMetricsInt().bottom - mTextPaint.getFontMetricsInt().top) / 2 - mTextPaint.getFontMetricsInt().bottom
        var baseLine = height / 2 + dy
        canvas?.drawText(stepText, dx.toFloat(), baseLine.toFloat(), mTextPaint)
    }

    fun setCurrentStep(current:Float){
        this.mCurrentStep = current
        invalidate()
    }
}