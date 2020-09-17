package com.example.a11699.androidxallstudy.funnyeffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.a11699.comp_base.Util

/**
 *Create time 2020/9/16
 *Create Yu
 *description:跟手的3d环绕效果
 */
class FunnyEffectOneView : View {
    lateinit var mWhitePaint: Paint
    lateinit var mCirclePaint: Paint
    var mCircleStrokeWidth = 2
    var mMaxRadius = 300f

    //Camera旋转的最大角度
    var mMaxCameraRotate = 15f

    lateinit var mMatrix: Matrix
    lateinit var mCamera: Camera

    //camera绕x轴 旋转角度
    var mCameraRotateX = 0f
    var mCameraRotateY = 0f

    init {
        mMatrix = Matrix()
        mCamera = Camera()
        //白色圆形的画笔
        mWhitePaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = mCircleStrokeWidth.toFloat()
            color = Color.WHITE
        }
        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = mCircleStrokeWidth.toFloat()
            color = Color.GRAY
        }

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let { canvas ->
            setCameraRotate(canvas)
            mWhitePaint?.let { canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mMaxRadius, it) };
            mCirclePaint?.let { canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mMaxRadius / 6 * 5, it) };
            mCirclePaint?.let { canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mMaxRadius / 6 * 4, it) };
            mCirclePaint?.let { canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mMaxRadius / 6 * 3, it) };
            mCirclePaint?.let { canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mMaxRadius / 6 * 2, it) };

        }
    }

    private fun setCameraRotate(canvas: Canvas) {
        mMatrix.reset()
        mCamera.save()
        mCamera.rotateX(mCameraRotateX) //绕x轴旋转
        mCamera.rotateY(mCameraRotateY) //绕y轴旋转
        mCamera.getMatrix(mMatrix) //计算对于当前变换的矩阵，并将其赋值到传入的mMarix中
        mCamera.restore()

        /**
         * Camera默认位于视图的左上角，故生成的矩阵默认也是以其左上角为旋转中心，
         * 所以在动作之前调用preTranslate将mMatrix向左移动getWidth()/2个长度，
         * 向上移动getHeight()/2个长度，
         * 使旋转中心位于矩阵的中心位置，动作之后再post回到原位
         */
        mMatrix.preTranslate((-width / 2).toFloat(), (-height / 2).toFloat())

         mMatrix.postTranslate(width / 2.toFloat(), height / 2.toFloat())
        canvas.concat(mMatrix)//将mMatrix与canvas中当前的Matrix相关联

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                //根据手指坐标计算Camera应该旋转的角度
                getCameraRotate(event)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                getCameraRotate(event)
                invalidate()
            }
        }
        return true
    }

    private fun getCameraRotate(event: MotionEvent) {
        val rotateX = -(event.y - height / 2)
        val rotateY = event.x - width / 2
        /**
         * 为什么旋转角度要这样计算：
         * 当Camera.rotateX(x)的x为正时，图像围绕X轴，上半部分向里下半部分向外，进行旋转，
         * 也就是手指触摸点要往上移。这个x就会与event.getY()的值有关，x越大，绕X轴旋转角度越大，
         * 以圆心为基准，手指往上移动，event.getY() - getHeight() / 2的值为负，
         * 故 float rotateX = -(event.getY() - getHeight() / 2)
         * 同理，
         * 当Camera.rotateY(y)的y为正时，图像围绕Y轴，右半部分向里左半部分向外，进行旋转，
         * 也就是手指触摸点要往右移。这个y就会与event.getX()的值有关，y越大，绕Y轴旋转角度越大，
         * 以圆心为基准，手指往右移动，event.getX() - getWidth() / 2的值为正，
         * 故 float rotateY = event.getX() - getWidth() / 2
         */
        /**
         * 此时得到的rotateX、rotateY 其实是以圆心为基准，手指移动的距离，
         * 这个值很大，不能用来作为旋转的角度，
         * 所以还需要继续处理
         */

        //求出移动距离与半径之比。mMaxRadius为白色大圆的半径
        var percentX = rotateX / mMaxRadius
        var percentY = rotateY / mMaxRadius
        if (percentX > 1) {
            percentX = 1f
        } else if (percentX < -1) {
            percentX = -1f
        }
        if (percentY > 1) {
            percentY = 1f
        } else if (percentY < -1) {
            percentY = -1f
        }

        //将最终的旋转角度控制在一定的范围内，这里mMaxCameraRotate的值为15，效果比较好
        mCameraRotateX = percentX * mMaxCameraRotate
        mCameraRotateY = percentY * mMaxCameraRotate
    }
}