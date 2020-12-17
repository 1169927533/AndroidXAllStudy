package com.example.a11699.comp_customview.pentagonal

import android.R.attr.radius
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.example.a11699.comp_customview.tF
import java.lang.Math.cos
import java.lang.Math.sin
import java.security.MessageDigest


/**
 * @Author Yu
 * @Date 2020/12/16 13:31
 * @Description 自定义glide的Transfor实现加载六边性转的图片
 */
class PentagonaltTransform(val width: Float) : BitmapTransformation() {
    val path = Path()
    lateinit var mCanvas: Canvas
    val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            strokeWidth = 10f
            strokeCap = Paint.Cap.ROUND
            pathEffect = CornerPathEffect(10f)
        }
    }


    override fun updateDiskCacheKey(p0: MessageDigest) {

    }

    override fun transform(
            pool: BitmapPool,
            source: Bitmap,
            outWidth: Int,
            outHeight: Int
    ): Bitmap {
        var result: Bitmap? = pool[width.toInt(), width.toInt(), Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(width.toInt(), width.toInt(), Bitmap.Config.ARGB_8888)
        }
        mCanvas = Canvas(result!!)
        var circlePointX = width / 2
        var radian60 = 60 * Math.PI / 180
        var xLength = circlePointX * kotlin.math.sin(radian60)
        var yLength = circlePointX * kotlin.math.cos(radian60)
        path.reset()
        path.moveTo(circlePointX.toFloat(), 0f)
        path.lineTo(circlePointX.toFloat() + xLength.toFloat(), yLength.toFloat())
        path.lineTo(circlePointX.toFloat() + xLength.toFloat(), circlePointX + yLength.toFloat())
        path.lineTo(circlePointX.toFloat(), circlePointX.toFloat() * 2)
        path.lineTo(circlePointX.toFloat() - xLength.toFloat(), circlePointX + yLength.toFloat())
        path.lineTo(circlePointX.toFloat() - xLength.toFloat(), yLength.toFloat())
        path.close()

        mPaint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mPaint.isAntiAlias = true
        mCanvas!!.drawPath(path, mPaint)
        return result!!
    }
}