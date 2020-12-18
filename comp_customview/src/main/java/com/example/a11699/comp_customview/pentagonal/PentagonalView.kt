package com.example.a11699.comp_customview.pentagonal

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
    var sixStroke = 0f //线条宽度
    var sixXColor = Color.BLACK //线条颜色
    var sixAngle = 0f   //线条弧度
    var parentWidth = 0 //最终整个imageview的宽高
    var isFill = false  //是否填充
    private var isLoadUrlError = false //加载网络图片是否成功
    private var isLoadNetImg = false //是否是加载网络图片
    var mDrawable: Drawable? = null //网络加载失败时候 需要展示的图片资源
    private lateinit var mCanvas: Canvas
    private val mPaint by lazy {
        Paint().apply {
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
        isFill = typedArray.getBoolean(R.styleable.hexagon_isFill, isFill)
        isLoadNetImg = typedArray.getBoolean(R.styleable.hexagon_loadNetImg, isLoadNetImg)
        typedArray.recycle()

        mPaint.strokeWidth = sixStroke
        mPaint.color = sixXColor
        mPaint.pathEffect = CornerPathEffect(sixAngle) //设置了这个才能让我们的线条画的更加圆润
        if (isFill) {
            mPaint.style = Paint.Style.FILL
        } else {
            mPaint.style = Paint.Style.STROKE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(handleMeasure(widthMeasureSpec), handleMeasure(heightMeasureSpec))
    }

    /**
     * 绘制的逻辑是：
     *  首先判断是不是加载网络图片 loadNetImg 是的话就不走绘制六边形的逻辑 绘制六边形的逻辑交给glide去做
     *
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //不是加载网络图片的话就正常绘制一个普通的六边形
        if (drawable != null) {
            mDrawable = drawable
        }
        if (!isLoadNetImg || isLoadUrlError) {
            if (canvas != null) {
                mCanvas = canvas
            }
            var circlePointX = parentWidth / 2
            var radian60 = 60 * Math.PI / 180
            var xLength = circlePointX * sin(radian60)
            var yLength = circlePointX * cos(radian60)
            path.reset()
            path.moveTo(circlePointX.toFloat(), sixStroke / 2)
            path.lineTo(circlePointX.toFloat() + xLength.toFloat(), yLength.toFloat())
            path.lineTo(
                    circlePointX.toFloat() + xLength.toFloat(),
                    circlePointX + yLength.toFloat()
            )
            path.lineTo(circlePointX.toFloat(), parentWidth.toFloat() - sixStroke / 2)
            path.lineTo(
                    circlePointX.toFloat() - xLength.toFloat(),
                    circlePointX + yLength.toFloat()
            )
            path.lineTo(circlePointX.toFloat() - xLength.toFloat(), yLength.toFloat())
            path.close()
            mCanvas.drawPath(path, mPaint)
        }
    }

    /**
     * 图片加载错误的时候加载 这个方法需要我们进行一个手动调用
     */
    fun setDefaultDrawable() {
        if (mDrawable != null) {
            isLoadUrlError = true
            var bitamp = drawableToBitmap(mDrawable!!)
            //初始化BitmapShader，传入bitmap对象
            val bitmapShader = BitmapShader(bitamp, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR)
            //计算缩放比例
            var mScale = parentWidth.toFloat() / bitamp.width.toFloat()

            val matrix = Matrix()
            matrix.setScale(mScale, mScale)
            bitmapShader.setLocalMatrix(matrix)
            mPaint.shader = bitmapShader
            invalidate()
        }
    }

    /**
     * 处理宽高
     */
    private fun handleMeasure(measureSpec: Int): Int {
        var specMode: Int = MeasureSpec.getMode(measureSpec)
        var specSize: Int = MeasureSpec.getSize(measureSpec)
        var result = 100
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


    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            val bd = drawable as BitmapDrawable
            return bd.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    fun loadUrlImg(url: String) {
        post{
            Glide.with(this).load(url)
                    //  .apply(RequestOptions.bitmapTransform(PentagonaltTransform()))
                    .transform(PentagonaltTransform(parentWidth.toFloat()))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                            setDefaultDrawable()//当遇到网络图片加载错的时候 去加载默认的图片
                            return false
                        }

                        override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(this)
        }

    }
}