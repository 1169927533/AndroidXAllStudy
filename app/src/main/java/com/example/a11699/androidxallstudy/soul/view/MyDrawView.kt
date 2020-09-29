package com.example.a11699.androidxallstudy.soul.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 *Create time 2020/7/29
 *Create Yu
 *description:
 */
class MyDrawView : View {
    val paint by lazy { Paint() }
    var downX = 0f
    var downY = 0f

    var moveX = 0f
    var moveY = 0f
    var list = ArrayList<DrawPath>()

    /**
     * 画笔颜色数组
     */
    private val COLOR_ARRAY = intArrayOf(-0x15bccb, -0xbd7a0c,
            -0x443fb, -0xcb57ad, -0xbd42e9, -0x6f42f2, -0xe74273,
            -0xd84253, -0xdf6743, -0x569043, -0x794643, -0xc2425c)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dip2px(context, 5f).toFloat()
        paint.color = COLOR_ARRAY[0]
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    var isUp = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                performClick()
                downX = event.x
                downY = event.y
                var path = Path()
                path.moveTo(downX, downY)
                path.lineTo(downX, downY)
                list.add(DrawPath(path))

                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (list.size > 0) {
                    if (isUp == list.size - 1) {
                        for (index in isUp until list.size) {
                            readPointList(event, 0)?.let { addPath(it, list[index].path) }
                        }
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                isUp += 1
            }
            MotionEvent.ACTION_CANCEL -> {

            }
        }
        return true
    }

    private fun readPointList(event: MotionEvent, pointerIndex: Int): List<PointF>? {
        val list: MutableList<PointF> = java.util.ArrayList()
        for (j in 0 until event.historySize) {
            list.add(PointF(event.getHistoricalX(pointerIndex, j), event.getHistoricalY(pointerIndex, j)))
        }
        return list
    }

    private fun addPath(list: List<PointF>, path: Path) {
        for (item in list) {
            path.lineTo(item.x, item.y)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (value in list) {
            canvas?.drawPath(value.path, paint)
        }

    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    private fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue.toDouble() * scale.toDouble() + 0.5).toInt()
    }

    /**
     * dp 2 px 的另外一种方法
     */
    private fun dip22px(dipValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().displayMetrics);
    }

    data class Point(var x: Int, var y: Int)

    class DrawPath(path: Path) {

        /**
         * 曲线路径
         */
        var path: Path = path

    }
}