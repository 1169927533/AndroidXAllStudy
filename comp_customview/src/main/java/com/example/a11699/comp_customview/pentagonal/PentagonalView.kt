package com.example.a11699.comp_customview.pentagonal

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.example.a11699.comp_customview.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * @Author Yu
 * @Date 2020/12/16 11:01
 * @Description 自定义view实现六边形图片
 */
class PentagonalView : androidx.appcompat.widget.AppCompatImageView {
    var result = 100
    var sixStroke = 0f
    var sixXColor = Color.BLACK
    var sixAngle = 0f
    var parentWidth = 0
    val mPaint by lazy {
        Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND

        }
    }
    val path by lazy {
        Path()
    }

    constructor(context: Context) : super(context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        var typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.hexagon, 0, 0)
        sixStroke = typedArray.getDimension(R.styleable.hexagon_sixStroke, sixStroke)
        sixAngle = typedArray.getDimension(R.styleable.hexagon_sixAngle, sixAngle)
        sixXColor = typedArray.getColor(R.styleable.hexagon_sixXColor, sixXColor)
        typedArray.recycle()
        mPaint.strokeWidth = sixStroke
        mPaint.color = sixXColor
        mPaint.pathEffect = CornerPathEffect(sixAngle)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(handleMeasure(widthMeasureSpec), handleMeasure(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var circlePointX = parentWidth / 2
        var radian60 = 60 * Math.PI / 180
        var xLength = circlePointX * sin(radian60)
        var yLength = circlePointX * cos(radian60)
        path.reset()
        path.moveTo(circlePointX.toFloat(), sixStroke / 2)
        path.lineTo(circlePointX.toFloat() + xLength.toFloat(), yLength.toFloat())
        path.lineTo(circlePointX.toFloat() + xLength.toFloat(), circlePointX + yLength.toFloat())
        path.lineTo(circlePointX.toFloat(), parentWidth.toFloat() - sixStroke / 2)
        path.lineTo(circlePointX.toFloat() - xLength.toFloat(), circlePointX + yLength.toFloat())
        path.lineTo(circlePointX.toFloat() - xLength.toFloat(), yLength.toFloat())
        path.close()

        canvas!!.drawPath(path, mPaint)
    }


    /**
     * 处理宽高
     */
    private fun handleMeasure(measureSpec: Int): Int {
        var specMode: Int = MeasureSpec.getMode(measureSpec)
        var specSize: Int = MeasureSpec.getSize(measureSpec)
        result = when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            else -> {
                min(result, specSize)
            }
        }
        parentWidth = result + sixStroke.toInt() * 2
        return parentWidth
    }

    //写一个drawble转BitMap的方法
    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }
}