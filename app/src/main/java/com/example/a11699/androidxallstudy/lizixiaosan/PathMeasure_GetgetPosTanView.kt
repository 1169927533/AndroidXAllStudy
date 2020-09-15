package com.example.a11699.androidxallstudy.lizixiaosan

import android.R
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


/**
 *Create time 2020/9/15
 *Create Yu
 *description:PathMeasure
 *使用到api: getPosTan 实现获取Path上每个点的坐标值
 */
class PathMeasure_GetgetPosTanView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    var paintOut: Paint = Paint()
    var paintCircle: Paint = Paint()//绘制裁剪时候的路径
    var pathSource = Path()//源路径
    var targetRect = Rect()//移动的点
    var pathMeasure = PathMeasure()
    var pos = FloatArray(2)


    init {
        paintOut.color = Color.BLACK
        paintOut.style = Paint.Style.STROKE
        paintOut.strokeWidth = 20f

        paintCircle.color = Color.BLUE
        paintCircle.style = Paint.Style.FILL
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pathSource.addRect(RectF(50f, 50f, 400f, 400f), Path.Direction.CCW)
        pathMeasure.setPath(pathSource, false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(pathSource, paintOut)
        canvas!!.drawCircle(pos[0], pos[1], 10f, paintCircle)
    }

    fun startAnimal() {
        var valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
        valueAnimator.duration = 3 * 1000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatCount=-1
        valueAnimator.addUpdateListener {
            val distance = it.animatedValue as Float
            pathMeasure.getPosTan(distance, pos, null)
            postInvalidate()
        }
        valueAnimator.start()
    }
}