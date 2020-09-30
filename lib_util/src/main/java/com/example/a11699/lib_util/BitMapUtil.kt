package com.example.a11699.lib_util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 *Create time 2020/9/27
 *Create Yu
 *description:
 */
object BitMapUtil {
    /**
     * 将 view 转换成 bitmap
     */
    fun getView2BitMap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    /**
     * width 图得宽度
     * height 图的高度
     * bgColor 图得颜色
     */
    fun generateBackgroundBitmap(width: Int, height: Int, bgColor: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        canvas.drawColor(bgColor)
        return bitmap
    }


}