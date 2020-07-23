package com.example.a11699.androidxallstudy.sendgift.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.a11699.androidxallstudy.R

/**
 *Create time 2020/7/22
 *Create Yu
 *description:
 */
class CoutDownView : View {
    var radius = 0f //圆弧半径
    var paintWidth = 0f //画笔宽度
    var paintColor = Color.BLACK // 画笔颜色
    var paintInColor = Color.BLACK //内部实心圆的颜色

    var testColor = Color.WHITE //文字颜色
    var testIn = "" //内部文字
    var testTime = "3.0s" //倒计时显示
    var testInSize = 0f //文字大小
    var testTimeSize = 0f //倒计时文字大小
    val paintTest by lazy {
        Paint()
    }
    val paintTimeTest by lazy {
        Paint()
    }

    val paintCircle by lazy {
        Paint()
    }

    val paintInCircle by lazy {
        Paint()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.countdownview)
        radius = typeArray.getDimension(R.styleable.countdownview_radius, 10f)
        paintWidth = typeArray.getDimension(R.styleable.countdownview_paintWidth, 1f)
        paintColor = typeArray.getColor(R.styleable.countdownview_paintColor, Color.BLACK)
        paintInColor = typeArray.getColor(R.styleable.countdownview_paintInColor, Color.GRAY)
        testColor = typeArray.getColor(R.styleable.countdownview_testInColor, Color.WHITE)
        testIn = typeArray.getString(R.styleable.countdownview_testIn).toString()
        testInSize = typeArray.getDimension(R.styleable.countdownview_testSize, 1f)
        testTimeSize = typeArray.getDimension(R.styleable.countdownview_testTimeSize, 2f)
        typeArray.recycle()
        initPaint()
    }

    private fun initPaint() {
        //画的圆弧
        paintCircle.color = paintColor
        paintCircle.strokeWidth = paintWidth
        paintCircle.style = Paint.Style.STROKE
        paintCircle.isAntiAlias = true
        paintCircle.strokeCap = Paint.Cap.ROUND


        //内部圆画笔
        paintInCircle.color = paintInColor
        paintInCircle.strokeWidth = paintWidth
        paintInCircle.style = Paint.Style.FILL_AND_STROKE
        paintInCircle.isAntiAlias = true
        paintInCircle.strokeCap = Paint.Cap.ROUND

        //绘制文字
        paintTest.color = testColor
        paintTest.isAntiAlias = true//抗锯齿
        paintTest.isDither = true//防抖动
        paintTest.textSize = testInSize


        paintTimeTest.color = testColor
        paintTimeTest.isAntiAlias = true//抗锯齿
        paintTimeTest.isDither = true//防抖动
        paintTimeTest.textSize = testTimeSize
    }

    var haveRunRadius = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var rectF = RectF(paintWidth, paintWidth, (width - paintWidth).toFloat(), (height - paintWidth).toFloat())

        canvas?.drawArc(rectF, 0f, haveRunRadius.toFloat(), false, paintCircle)

        canvas?.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), (width / 2 - paintWidth * 2), paintInCircle)
        //绘制提示文字
        drawTest(canvas)
        //绘制倒计时文字
        drawTimeTest(canvas)

    }

    private fun drawTest(canvas: Canvas?) {
        //获取字体宽度
        var rect = Rect()
        paintTest.getTextBounds(testIn, 0, testIn.length, rect)
        var x = width / 2 - rect.width() / 2
        //获取基线baseLine
        var fontMetrice = paintTest.fontMetricsInt
        var dy = (fontMetrice.bottom - fontMetrice.top) / 2 - fontMetrice.bottom
        var baseLine = height / 2 + dy
        canvas?.drawText(testIn, x.toFloat(), baseLine.toFloat() - (width / 2 - paintWidth * 2) / 2, paintTest)
    }

    private fun drawTimeTest(canvas: Canvas?) {
        var rect = Rect()
        paintTimeTest.getTextBounds(testTime, 0, testTime.length, rect)
        var x = width / 2 - rect.width() / 2
        //获取基线baseLine
        var fontMetrice = paintTimeTest.fontMetricsInt
        var dy = (fontMetrice.bottom - fontMetrice.top) / 2 - fontMetrice.bottom
        var baseLine = height / 2 + dy
        canvas?.drawText(testTime, x.toFloat(), baseLine.toFloat(), paintTimeTest)
    }

    val timer by lazy {
        object : CountDownTimer(3000, 50) {
            override fun onFinish() {
                haveRunRadius = 0
                postInvalidate()
            }

            override fun onTick(millisUntilFinished: Long) {
                haveRunRadius = (360f * millisUntilFinished / 3000).toInt()
                testTime = "${((millisUntilFinished / 1000f).toString().substring(0, 3))}s"
                postInvalidate()
            }

        }
    }

    fun startClick() {
        timer.cancel()
        timer.start()
    }


}