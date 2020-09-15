package com.example.a11699.androidxallstudy.lizixiaosan

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/9/15
 *Create Yu
 *description:PathMeasure
 *使用到api: getSegment 实现贪吃蛇的动态效果
 */
class PathMeasure_GetSegmentView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    var paintOut: Paint = Paint()
    var paintIn: Paint = Paint()//绘制裁剪时候的路径

    var pathSource = Path()//原路径
    var pathClicp: Path = Path()//每次裁剪矩形时候的路径
    var pathMeasure = PathMeasure()
    var workLength = 30f//每次运行行走的距离
    var totalLength = 0f //源路径的全部长度

    init {
        paintOut.color = Color.BLACK
        paintOut.style = Paint.Style.STROKE
        paintOut.strokeWidth = 40f

        paintIn.color = Color.BLUE
        paintIn.style = Paint.Style.STROKE
        paintIn.strokeWidth = 40f
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pathSource.addRect(RectF(50f, 50f, 400f, 400f), Path.Direction.CCW)
        pathClicp.reset()
        pathMeasure.setPath(pathSource, false)
        totalLength = pathMeasure.length
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(pathSource, paintOut)
        if (workLength >= totalLength) {
            workLength = 30f
        }
        pathClicp.reset() //这里每次都要清空path上一次的记录值
        pathMeasure.getSegment(workLength - 30, workLength, pathClicp, true)
        canvas!!.drawPath(pathClicp, paintIn)
    }

    fun startAnimal() {
        workLength += 30
        postInvalidate()
    }
}