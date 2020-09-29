package com.example.a11699.androidxallstudy.funnyeffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.a11699.androidxallstudy.R

/**
 *Create time 2020/9/27
 *Create Yu
 *description:
 */
class Camera3d : View {
    var sourceBitMap: Bitmap? = null
    var mPaint = Paint()
    var mCamera = Camera()
    var mCameraMatrix = Matrix()//3d旋转对应的矩阵
    var paintLine = Paint()
    private var flipScale = 0.2F
    var rotateAngle = 0f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        sourceBitMap = BitmapFactory.decodeResource(resources, R.drawable.hzw_a)
        paintLine.color = Color.BLACK
        paintLine.style = Paint.Style.FILL
        paintLine.strokeWidth = 4f

        // 获取手机像素密度 （即dp与px的比例）
        flipScale = resources.displayMetrics.density
    }

    private val divideTempFloat = FloatArray(9)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        //**************************************************************
        //由于我的图片太大 现在对他先进性一个缩放
        var scaleX = (width.toFloat() / sourceBitMap!!.width.toFloat())
        var matrix = Matrix()
        matrix.setScale(scaleX, scaleX)
        canvas!!.drawBitmap(sourceBitMap!!, matrix, mPaint)//这个绘制的是原图
        //****************************************************************

        //尝试3d旋转
        mCamera.save()
        mCamera.rotateX(rotateAngle)
        mCamera.getMatrix(mCameraMatrix)
        mCamera.restore()

        // 修正旋转时的透视 MPERSP_0
        mCameraMatrix.getValues(divideTempFloat)
        divideTempFloat[6] = divideTempFloat[6] / flipScale
        divideTempFloat[7] = divideTempFloat[7] / flipScale
        mCameraMatrix.setValues(divideTempFloat)

        mCameraMatrix.preTranslate((-sourceBitMap!!.width * scaleX / 2), -(sourceBitMap!!.height * scaleX))
        mCameraMatrix.postTranslate(sourceBitMap!!.width * scaleX / 2.toFloat(), sourceBitMap!!.height * scaleX)
        mCameraMatrix.preScale(scaleX, scaleX)

        canvas!!.drawBitmap(sourceBitMap!!, mCameraMatrix, mPaint)
        canvas.drawLine(0f, sourceBitMap!!.height * scaleX.toFloat(), width.toFloat(), sourceBitMap!!.height * scaleX.toFloat(), paintLine)//他的选咋混
        canvas.restore()
    }

    fun setRotate(rotate: Float) {
        this.rotateAngle = rotate
        invalidate()
    }
}