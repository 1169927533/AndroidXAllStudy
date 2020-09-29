package com.example.a11699.androidxallstudy.funnyeffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.Util

/**
 *Create time 2020/9/18
 *Create Yu
 *description:
 */
class MatrixStudyView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    var mBitmapMatrix: Bitmap? = null
    var mPaint: Paint? = null

    init {
        mBitmapMatrix = BitmapFactory.decodeResource(resources, R.drawable.hzw_a)
        mPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var imgWidth = mBitmapMatrix!!.width
        var imgHeight = mBitmapMatrix!!.height
        Util.printLog("图片的宽度：$imgWidth 图片的高度：${imgHeight}")

        var parenWidth = width
        var parentHeight = height
        Util.printLog("父类宽度：$width 父类高度：${height}")

        var scaleX = (parenWidth.toFloat() / imgWidth.toFloat()).toFloat()
        var scaleY = (parentHeight.toFloat() / imgHeight.toFloat()).toFloat()
        Util.printLog("缩放比里：$scaleX   $scaleY")

        var matrix = Matrix()
        matrix.preRotate(45f, 0f, 0f)
        matrix.preScale(scaleX, scaleY)
        matrix.postRotate(45f, parenWidth / 2.toFloat(), parenWidth / 2.toFloat())
        matrix.postTranslate(10f,10f)
        canvas!!.concat(matrix)

        canvas.drawColor(Color.LTGRAY)
        canvas!!.drawBitmap(mBitmapMatrix!!, 0f, 0f, mPaint)
    }
}