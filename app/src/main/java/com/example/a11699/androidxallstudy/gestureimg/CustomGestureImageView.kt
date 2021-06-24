package com.example.a11699.androidxallstudy.gestureimg

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.a11699.androidxallstudy.R
import com.example.a11699.lib_util.dp

/**
 * @Author Yu
 * @Date 2021/1/20 10:06
 * @Description 可以手势 缩放 移动的 imageview
 */
private val IMAGE_SIZE = 900.dp.toInt()
class CustomGestureImageView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources,IMAGE_SIZE)
    private var offsetX = 0f
    private var offsetY = 0f
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (width - IMAGE_SIZE).toFloat()/2f
        offsetY = (height - IMAGE_SIZE).toFloat()/2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    private fun getAvatar(res: Resources, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}