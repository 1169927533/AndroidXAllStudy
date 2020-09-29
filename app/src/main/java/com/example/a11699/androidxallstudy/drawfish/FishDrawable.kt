package com.example.a11699.androidxallstudy.drawfish

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

/**
 *Create time 2020/9/28
 *Create Yu
 *description:
 */
class FishDrawable : Drawable {
    var mPath = Path()
    lateinit var mPaint: Paint

    private final var OTHER_ALPHA = 110
    private final var BODY_ALPHA = 160
    private lateinit var middlePoint: PointF
    private var fishMainAngle = 90f
    final var Head_radius = 100

    var BODY_LENGTH = 3.2f * Head_radius
    var FIND_FINS_LENGTH = 0.9f * Head_radius

    //鱼鳍长度
    var FINS_LENGTH = 1.3f * Head_radius

    constructor() : super() {
        init()
    }

    fun init() {
        mPath = Path()
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71)
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        middlePoint = PointF(4.19f * Head_radius, 4.19f * Head_radius)
    }

    override fun draw(canvas: Canvas) {
        var fishAngle = fishMainAngle
        //绘制鱼头
        var headPoint = calculaPoint(middlePoint, BODY_LENGTH / 2, fishAngle)
        canvas.drawCircle(headPoint.x, headPoint.y, Head_radius.toFloat(), mPaint)

        //绘制右鱼鳍
        var rightFinsPoint = calculaPoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110)
        makeFins(canvas, rightFinsPoint, fishAngle, true)
        //绘制左鱼鳍
        var leftFinsPoint = calculaPoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110)
        makeFins(canvas, leftFinsPoint, fishAngle, false)


    }

    private fun makeSegment() {

    }

    private fun makeFins(canvas: Canvas, startPointF: PointF, fishAngele: Float, isRightFins: Boolean) {
        var confrolAngle = 115
        var endpoint = calculaPoint(startPointF, FINS_LENGTH, fishAngele - 180)
        var controlPoint = calculaPoint(startPointF, 1.8f * FINS_LENGTH, if (isRightFins) {
            fishAngele - confrolAngle
        } else {
            fishAngele + confrolAngle
        })
        mPath.reset()
        mPath.moveTo(startPointF.x, startPointF.y)
        mPath.quadTo(controlPoint.x, controlPoint.y, endpoint.x, endpoint.y)
    }

    /**
     * startPoint ： 起始点
     * length ： 两点的距离
     * angle ： 两点连线与x轴的夹角
     */
    public fun calculaPoint(startPoint: PointF, length: Float, angle: Float): PointF {
        //cosa * c
        var deltaX = cos(Math.toRadians(angle.toDouble())) * length
        var deltaY = sin(-Math.toRadians(angle.toDouble())) * length
        return PointF((startPoint.x + deltaX).toFloat(), (startPoint.y + deltaY).toFloat())
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * Head_radius).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * Head_radius).toInt()
    }

}