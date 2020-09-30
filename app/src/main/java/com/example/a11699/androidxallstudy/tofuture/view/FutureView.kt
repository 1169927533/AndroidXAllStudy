package com.example.a11699.androidxallstudy.tofuture.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.tofuture.bean.FutureBean
import com.example.a11699.lib_util.BitMapUtil
import com.example.a11699.lib_util.dp

/**
 *Create time 2020/9/29
 *Create Yu
 *description:给未来写封信
 */
private val CLICP_COUNT = 2 //将图片划分成几份
private val divideTempFloat = FloatArray(9)

class FutureView : View {
    var mCamera = Camera()
    private var flipScale = 0.2f

    lateinit var sourceBitMap: Bitmap
    lateinit var animator: Animator
    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var listBitMap = ArrayList<FutureBean>()//存取裁剪后的片段
    var mMatrix = Matrix()


    var foldState = FoldState.UNFOLD

    enum class FoldState {
        FOLD, UNFOLD
    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        animator = prePareAnimal()
        flipScale = resources.displayMetrics.density
        listBitMap.clear()
     //   myXmlView = LayoutInflater.from(context).inflate(R.layout.item_chat, null)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width: Int = getMySize(400f.dp.toInt(), widthMeasureSpec)
        var height: Int = getMySize(400f.dp.toInt(), heightMeasureSpec)
        if (listBitMap.size == 0) {
            sourceBitMap = getAvatar()
            var itemBitHeight = sourceBitMap.height / CLICP_COUNT
            var clipHeight = 0
            for (index in CLICP_COUNT - 1 downTo 0) {
                clipHeight = if (itemBitHeight * index + itemBitHeight > sourceBitMap.height) {
                    sourceBitMap.height - (itemBitHeight * index + itemBitHeight)
                } else {
                    itemBitHeight
                }
                var clipBitmap = Bitmap.createBitmap(sourceBitMap, 0, itemBitHeight * index, sourceBitMap.width, clipHeight)
                var futureBean = FutureBean(clipBitmap, 0, itemBitHeight * index , 0f)
                listBitMap.add(futureBean)
            }
        }
        width = (sourceBitMap.width * 1.2f).toInt()
        height = sourceBitMap.height.toInt()
        setMeasuredDimension(width, height)
    }

    private fun getMySize(defaultSize: Int, measureSpec: Int): Int {
        var mySize = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.UNSPECIFIED -> {
                //如果没有指定大小，就设置为默认大小
                mySize = defaultSize
            }
            MeasureSpec.AT_MOST -> {
                //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size
            }
            MeasureSpec.EXACTLY -> {
                //如果是固定的大小，那就不要去改变它
                mySize = size
            }
        }
        return mySize
    }

    override fun onDraw(canvas: Canvas) {
        if (foldState == FoldState.UNFOLD) {
            onDrawFold(canvas)
        } else {
            onDrawUnFold(canvas)
        }
    }

    var startAngle = 180f
    var endAngle = 360f
    var rotateXangle = 180f
    private fun prePareAnimal(): Animator {
        return ValueAnimator.ofFloat(startAngle, endAngle).apply {
            duration = 4000
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotateXangle = (it.animatedValue) as Float
                invalidate()
            }
        }
    }

    //得到指定大小的视图
    private fun getAvatar(): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true//这样设置是为了只获取bitmap的宽高 而不加载进入内存
        BitmapFactory.decodeResource(resources, R.drawable.u3, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = options.outWidth
        return BitmapFactory.decodeResource(resources, R.drawable.u3, options)
    }

    //折叠绘制
    fun onDrawFold(canvas: Canvas) {
        for (index in 0 until listBitMap.size) {
            var futureBean = listBitMap[index]
            if (index == 1) {
                mCamera.save()
                mCamera.rotateX(rotateXangle)
                mCamera.getMatrix(mMatrix)
                mCamera.restore()
                mMatrix.getValues(divideTempFloat)
                divideTempFloat[6] = divideTempFloat[6] / flipScale
                divideTempFloat[7] = divideTempFloat[7] / flipScale
                mMatrix.setValues(divideTempFloat)
                mMatrix.preTranslate(-futureBean.bitmap.width / 2.toFloat(), -(futureBean.bitmap.height).toFloat())
                mMatrix.postTranslate(futureBean.bitmap.width / 2.toFloat(), futureBean.bitmap.height.toFloat())
                mMatrix.postTranslate((width / 2 - sourceBitMap.width / 2).toFloat(), futureBean.y.toFloat())
            } else {
                mMatrix.setTranslate((width / 2 - sourceBitMap.width / 2).toFloat(), futureBean.y.toFloat())
            }

            if (index == 0) {
                canvas.drawBitmap(futureBean.bitmap, mMatrix, mPaint)
            }
            if (index == 1) {
                if (rotateXangle > 270f || rotateXangle == 0f) {
                    canvas.drawBitmap(futureBean.bitmap, mMatrix, mPaint)
                } else {
                    //绘制白色
                    var bg = BitMapUtil.generateBackgroundBitmap(sourceBitMap.width, sourceBitMap.height / CLICP_COUNT, Color.WHITE)
                    canvas.drawBitmap(bg, mMatrix, mPaint)
                }
            }
        }
    }

    //展开绘制
    fun onDrawUnFold(canvas: Canvas) {
        listBitMap.forEachIndexed { index, futureBean ->
            if (index == 1) {
                mCamera.save()
                mCamera.rotateX(rotateXangle)
                mCamera.getMatrix(mMatrix)
                mCamera.restore()
                mMatrix.getValues(divideTempFloat)
                divideTempFloat[6] = divideTempFloat[6] / flipScale
                divideTempFloat[7] = divideTempFloat[7] / flipScale
                mMatrix.setValues(divideTempFloat)
                mMatrix.preTranslate(-futureBean.bitmap.width / 2.toFloat(), -(futureBean.bitmap.height).toFloat())
                mMatrix.postTranslate(futureBean.bitmap.width / 2.toFloat(), futureBean.bitmap.height.toFloat())
                mMatrix.postTranslate((width / 2 - sourceBitMap.width / 2).toFloat(), futureBean.y.toFloat())
            } else {
                mMatrix.setTranslate((width / 2 - sourceBitMap.width / 2).toFloat(), futureBean.y.toFloat())
            }
            if (index == 1 && rotateXangle < 270) {
                //绘制白色
                var bg = BitMapUtil.generateBackgroundBitmap(sourceBitMap.width, sourceBitMap.height / CLICP_COUNT, Color.WHITE)
                canvas.drawBitmap(bg, mMatrix, mPaint)
            } else {
                canvas.drawBitmap(futureBean.bitmap, mMatrix, mPaint)
            }
        }
    }


    //准备折叠数据
    fun prePareUnFoldData() {
        foldState = FoldState.UNFOLD
        rotateXangle = 0f
        startAngle = 360f
        endAngle = 180f
        invalidate()
        animator = prePareAnimal()
    }

    //准备展开数据
    fun prePareFoldData() {
        foldState = FoldState.FOLD
        rotateXangle = 180f
        startAngle = 180f
        endAngle = 360f
        invalidate()
        animator = prePareAnimal()
    }
}