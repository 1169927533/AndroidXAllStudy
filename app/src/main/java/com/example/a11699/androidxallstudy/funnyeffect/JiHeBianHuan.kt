package com.example.a11699.androidxallstudy.funnyeffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.soul.view.px
import com.example.a11699.lib_util.dp


/**
 *Create time 2020/9/27
 *Create Yu
 *description:camera的应用
 */
private val BITMAP_SIZE = 200f.dp
private val BITMAP_PADDING = 100.dp
private val divideTempFloat = FloatArray(9)
private var flipScale = 0.2f

class JiHeBianHuan : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    var mCamera = Camera()

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mCamera.setLocation(0f, 0f, -10f)
        // 获取手机像素密度 （即dp与px的比例）
        flipScale = resources.displayMetrics.density
    }

    var mMatrix = Matrix()
    var rotateAngle = 0f
    fun setRotate(rotate: Float) {
        this.rotateAngle = rotate
        invalidate()

    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)

        mCamera.save()
        mCamera.rotateX(rotateAngle)
        mCamera.getMatrix(mMatrix)
        mCamera.restore()

        // 修正旋转时的透视 MPERSP_0
        mMatrix.getValues(divideTempFloat)
        divideTempFloat[6] = divideTempFloat[6] / flipScale
        divideTempFloat[7] = divideTempFloat[7] / flipScale
        mMatrix.setValues(divideTempFloat)

        mMatrix.preTranslate((-(bitmap.width / 2.toFloat())), 0f)
        mMatrix.getValues(divideTempFloat)

        mMatrix.postTranslate((bitmap.width / 2.toFloat()), 0f)
        mMatrix.postTranslate(((BITMAP_PADDING)), BITMAP_PADDING)
        canvas.drawBitmap(bitmap, mMatrix, paint)


    }


    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}