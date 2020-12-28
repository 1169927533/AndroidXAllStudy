package com.pince.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.pince.module_heart.R
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import java.util.*
import kotlin.collections.ArrayList


/**
 * @Author Yu
 * @Date 2020/12/28 15:09
 * @Description 扩散view
 */

class SpreadView : View {
    private var mParentWidth = 0
    private var startRadius = 0//其实圆半径
    private var distance = 5f //圆的间距
    private var alphaMulList = ArrayList<Int>() //透明度
    private var radiusMulList = ArrayList<Int>()//每次半径增加的值
    private val mCirclePaint by lazy {
        Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            alpha = 255
            isAntiAlias = true
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        startRadius = ViewUtil.dip2px(100f)
        alphaMulList.add(255)
        radiusMulList.add(0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(widthMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for ((index, value) in radiusMulList.withIndex()) {
            val nowAlpha = alphaMulList[index]
            mCirclePaint.alpha = nowAlpha
            var nowWidth = value
            canvas?.drawCircle(mParentWidth / 2.toFloat(), mParentWidth / 2.toFloat(), (startRadius + nowWidth).toFloat(), mCirclePaint)
            if (nowAlpha > 0 && nowWidth < 300) {


            }
        }

    }

    private fun measureSize(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        mParentWidth = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = mParentWidth
        } else {
            result = 300
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, mParentWidth)
            }
        }
        return result
    }
}