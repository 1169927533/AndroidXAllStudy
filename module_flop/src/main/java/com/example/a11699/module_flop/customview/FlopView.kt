package com.example.a11699.module_flop.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.a11699.lib_util.dp
import com.example.a11699.module_flop.R

/**
 *Create time 2020/10/13
 *Create Yu
 *description:一个卡牌 点击会旋转
 */
private val divideTempFloat = FloatArray(9)

class FlopView : View {
    private var flipScale = 0.2f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var m = Matrix().apply {
        setScale(-1F, 1F)
    }

    var mBackBitMap: Bitmap? = null
        set(value) {
            field = value?.let {
                m.postScale(80f.dp / it.width.toFloat(), 160f.dp / it.height.toFloat())
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, m, false)
            }
            invalidate()
        }

    private var mFontBitMap: Bitmap? = null
    private var mCamera = Camera()
    private var mMatrix = Matrix()
    private var rotateAngle = 0f//旋转角度
    var mFontBg = 0
    var location = IntArray(2)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mCamera.setLocation(0f, 0f, -10f)
        flipScale = resources.displayMetrics.density
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        if (mFontBg != 0) {
            mFontBitMap = getAvatar(measuredWidth, mFontBg)
        }


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        getLocationInWindow(location)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mFontBitMap != null) {
            mCamera.save()
            mCamera.rotateY(rotateAngle)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.getValues(divideTempFloat)
            divideTempFloat[6] = divideTempFloat[6] / flipScale
            divideTempFloat[7] = divideTempFloat[7] / flipScale
            mMatrix.setValues(divideTempFloat)

            mMatrix.preTranslate(-(mFontBitMap?.width!! / 2.toFloat()), -0f)
            mMatrix.postTranslate(mFontBitMap?.width!! / 2.toFloat(), 0f)

            if (rotateAngle > 90f) {
                canvas.drawBitmap(mBackBitMap!!, mMatrix, paint)
            } else {
                if (mFontBitMap != null) {
                    canvas.drawBitmap(mFontBitMap!!, mMatrix, paint)
                }
            }
        }
    }


    private fun setRotate(rotate: Float) {
        this.rotateAngle = rotate
        invalidate()
    }


    fun startAnimal() {
        val animal = ValueAnimator.ofFloat(0f, 180f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                setRotate((it.animatedValue) as Float)
            }
        }
        animal.start()
    }


    private fun getAvatar(width: Int, bg: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, bg, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, bg, options)
    }

}