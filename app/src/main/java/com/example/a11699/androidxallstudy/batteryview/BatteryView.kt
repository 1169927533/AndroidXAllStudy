package com.example.a11699.androidxallstudy.batteryview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import okhttp3.internal.notify

/**
 *Create time 2020/9/12
 *Create Yu
 *description:自定义电池
 */
class BatteryView : View {
    var index = 0 //绘制第几个电量 默认四个电量 index从0开始
    var thePowerMaxNum = 6//电池电量
    private var mTopPaint: Paint? = null    //画电池 顶部帽子
    private var mCenterPaint: Paint? = null //画身体
    private var powerPaint: Paint? = null //画电量

    private var top2centerDistance = 0f//电池帽距离身体的距离

    private var topLineColor: Int = 0//电池顶部线条颜色
    private var topSideWidth: Float = 0f//电池顶部线条宽度
    private var topLineWidth = 0f //电池头宽度
    private var topLineHeight = 0f//电池头高度
    private var topRectRad = 0f//电池头的弧度
    private var topRect = RectF()

    private var centerBodyLineColor: Int = 0//身体线条颜色
    private var centerBodySideWidth: Float = 0f//身体线条宽度
    private var centerBodyWidth: Float = 0f//身体宽度
    private var centerBodyHeight: Float = 0f//身体高度
    private var centerBodyRectRad: Float = 0f//身体弧度
    private var centerRect = RectF()

    private var powerColor: Int = 0//电量颜色
    private var powerWidth = 0f//电量宽度
    private var powerHeight = 0f//电量高度
    private var powerRectRad = 0f//电量弧度
    private var powerMarginTop = 0f//电量距离顶部的距离
    private var powerRect = RectF()


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.batteryView)
        top2centerDistance = typeArray.getDimension(R.styleable.batteryView_top2centerdistance, ViewUtil.dip2px(4f).toFloat())

        topLineColor = typeArray.getColor(R.styleable.batteryView_batteryTopColor, Color.BLACK)
        topSideWidth = typeArray.getDimension(R.styleable.batteryView_batteryTopWidth, ViewUtil.dip2px(4f).toFloat())
        topLineWidth = typeArray.getDimension(R.styleable.batteryView_batteryTopLineWidth, ViewUtil.dip2px(14f).toFloat())
        topLineHeight = typeArray.getDimension(R.styleable.batteryView_batteryTopLineHeight, ViewUtil.dip2px(14f).toFloat())
        topRectRad = typeArray.getDimension(R.styleable.batteryView_batteryTopLineArc, ViewUtil.dip2px(4f).toFloat())

        centerBodyLineColor = typeArray.getColor(R.styleable.batteryView_centerBodyColor, Color.BLACK)
        centerBodySideWidth = typeArray.getDimension(R.styleable.batteryView_centerBodySideWidth, ViewUtil.dip2px(4f).toFloat())
        centerBodyWidth = typeArray.getDimension(R.styleable.batteryView_centerBodyLineWidth, ViewUtil.dip2px(14f).toFloat())
        centerBodyHeight = typeArray.getDimension(R.styleable.batteryView_centerBodyLineHeight, ViewUtil.dip2px(14f).toFloat())
        centerBodyRectRad = typeArray.getDimension(R.styleable.batteryView_batteryTopLineArc, ViewUtil.dip2px(4f).toFloat())

        powerColor = typeArray.getColor(R.styleable.batteryView_powerColor, Color.BLACK)
        powerRectRad = typeArray.getDimension(R.styleable.batteryView_powerRectArc, ViewUtil.dip2px(4f).toFloat())
        powerMarginTop = typeArray.getDimension(R.styleable.batteryView_powerMarginTop, 0f)
        powerWidth = centerBodyWidth - centerBodySideWidth * 2
        typeArray.recycle()

        mTopPaint = Paint()
        mCenterPaint = Paint()
        powerPaint = Paint().apply {
            isAntiAlias = true //抗锯齿
            color = powerColor
            style = Paint.Style.FILL
        }
        initTopView(mTopPaint!!, topLineColor, topSideWidth, Paint.Style.FILL_AND_STROKE)
        initTopView(mCenterPaint!!, topLineColor, centerBodySideWidth, Paint.Style.STROKE)
        index = thePowerMaxNum
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawTopView(canvas)
    }

    //初始化 电池顶部view
    private fun initTopView(paint: Paint, lineColor: Int, topSideWidth: Float, mStyle: Paint.Style) {
        paint.apply {
            isAntiAlias = true //抗锯齿
            color = lineColor
            style = mStyle
            strokeWidth = topSideWidth
        }
    }

    //绘制电池顶部的正极按钮
    private fun drawTopView(canvas: Canvas?) {
        canvas?.let { mCanvas ->
            //画电池顶部
            setDateToRect(topRect, width / 2 - topLineWidth / 2, 0f, width / 2 + topLineWidth / 2, topLineHeight)
            mCanvas.drawRoundRect(topRect, topRectRad, topRectRad, mTopPaint!!)

            //画电池身体
            var centerTopDistance = top2centerDistance + topLineHeight + topSideWidth * 2 + centerBodySideWidth
            setDateToRect(centerRect, width / 2 - centerBodyWidth / 2, centerTopDistance, width / 2 + centerBodyWidth / 2, centerBodyHeight + centerTopDistance)
            mCanvas.drawRoundRect(centerRect, centerBodyRectRad, centerBodyRectRad, mCenterPaint!!)

            //画电量
            var itemHeight = (centerBodyHeight).toFloat() / thePowerMaxNum.toFloat() //一个item的高度

            var top = 0f
            var bottom = 0f
            bottom = ((centerBodyHeight + centerTopDistance) - 10f)

            if (thePowerMaxNum == index) {
                top = 0f
                bottom = 0f
            } else {
                top = centerBodyHeight + centerTopDistance - itemHeight * (thePowerMaxNum - index)
                if (top <= centerTopDistance) {
                    top = centerBodyHeight + centerTopDistance - itemHeight * (thePowerMaxNum - index) + 10 + powerPaint!!.strokeWidth
                }
            }

            setDateToRect(powerRect, width / 2 - powerWidth / 2, top, width / 2 + powerWidth / 2, bottom)
            mCanvas.drawRoundRect(powerRect, powerRectRad, powerRectRad, powerPaint!!)
        }
    }

    fun setPaintColor(mIndex: Int) {
        index = mIndex
        when (mIndex) {
            0, 1, 2 -> {
                setPaintColor(mTopPaint!!, powerPaint!!, mCenterPaint!!, color = R.color.color_00D088)
            }
            3, 4 -> {
                setPaintColor(mTopPaint!!, powerPaint!!, mCenterPaint!!, color = R.color.color_a0a0a0)
            }
            5, 6 -> {
                setPaintColor(mTopPaint!!, powerPaint!!, mCenterPaint!!, color = R.color.color_FF5757)
            }
            else -> {
                setPaintColor(mTopPaint!!, powerPaint!!, mCenterPaint!!, color = R.color.colorPrimaryDark)
            }
        }
    }

    private fun setDateToRect(rectF: RectF, left: Float, top: Float, right: Float, bottom: Float) {
        rectF.left = left
        rectF.top = top
        rectF.right = right
        rectF.bottom = bottom
    }

    private fun setPaintColor(vararg paint: Paint, color: Int) {

        paint.forEach {
            it.color = context.resources.getColor(color)
        }
        postInvalidate()
    }

}