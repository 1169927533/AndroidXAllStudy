package com.example.a11699.androidxallstudy.batteryview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil

/**
 *Create time 2020/9/12
 *Create Yu
 *description:自定义电池
 */
class BatteryView : View {
    //画电池 顶部帽子
    var mTopPaint: Paint? = null

    var topLineColor: Int = 0//电池顶部线条颜色
    var topSideWidth: Float = 0f//电池顶部线条宽度
    var topLineWidth = 0f //电池头宽度
    var topLineHeight = 0f//电池头高度
    var topRectArc = 0f//电池头的弧度


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.batteryView)
        topLineColor = typeArray.getColor(R.styleable.batteryView_batteryTopColor, Color.BLACK)
        topSideWidth = typeArray.getDimension(R.styleable.batteryView_batteryTopWidth, ViewUtil.dip2px(4f).toFloat())
        topLineWidth = typeArray.getDimension(R.styleable.batteryView_batteryTopLineWidth, ViewUtil.dip2px(14f).toFloat())
        topLineHeight = typeArray.getDimension(R.styleable.batteryView_batteryTopLineHeight, ViewUtil.dip2px(14f).toFloat())
        topRectArc = typeArray.getDimension(R.styleable.batteryView_batteryTopLineArc, ViewUtil.dip2px(4f).toFloat())
        typeArray.recycle()

        initTopView(topLineColor, topSideWidth)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawTopView(canvas)
    }

    //初始化 电池顶部view
    private fun initTopView(lineColor: Int, topSideWidth: Float) {
        mTopPaint = Paint()?.apply {
            isAntiAlias = true //抗锯齿
            color = lineColor
            strokeWidth = topSideWidth
        }
    }

    //绘制电池顶部的正极按钮
    private fun drawTopView(canvas: Canvas?) {
        canvas?.let { mCanvas ->
            var rect = RectF(width / 2 - topLineWidth / 2, 0f, width / 2 + topLineWidth / 2, topLineHeight)
            mCanvas.drawRoundRect(rect, topRectArc, topRectArc, mTopPaint!!)
        }
    }
}