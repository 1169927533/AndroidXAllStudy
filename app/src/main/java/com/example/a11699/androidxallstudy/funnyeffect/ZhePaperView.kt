package com.example.a11699.androidxallstudy.funnyeffect

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.funnyeffect.bean.ZheDieBean
import com.example.a11699.comp_customview.zhezhi.PaperInfo
import com.example.a11699.lib_util.BitMapUtil
import com.example.a11699.lib_util.dp

/**
 *Create time 2020/9/28
 *Create Yu
 *description:自己思考的折纸
 */
private val BITMAP_SIZE = 200f.dp//原始图片的大小
private val divideTempFloat = FloatArray(9)
lateinit var sourceBitmap: Bitmap //原图像
private val cliCpCount = 4//图片被裁切几份
var label = Label.fold //纸得状态 折叠or展开

enum class Label {
    fold, unfold
}

class ZhePaperView : View {
    private var flipScale = 0.2f
    private var mAnimalSet: AnimatorSet = AnimatorSet()//动画集合
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var listBitMap = ArrayList<ZheDieBean>()
    private val listAnimal = ArrayList<Animator>()//每一个item的动画集合
    private val interpolator = AccelerateInterpolator() //动画插值器
    var mCamera = Camera()
    var mMatrix = Matrix()
    var mFgMatrix = Matrix()
    private var startAngle = 360f //选转的起始角度
    private var endAngle = 180f   //旋转的结束角度
    private var lastBitmap: Bitmap? = null //在做展开的时候记录最后一个图片

    //对图片进行一个对称变换
    private var matrixScale = Matrix().apply {
        setScale(1f, -1f)
    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        sourceBitmap = getAvatar(BITMAP_SIZE.toInt())
        flipScale = resources.displayMetrics.density
        initData()
    }

    lateinit var fg: Bitmap
    lateinit var bg: Bitmap
    private fun initData() {
        listBitMap.clear()
        var itemBitmapHeight = sourceBitmap.height / cliCpCount //一个item高度
        for (index in 0 until cliCpCount) {
            if (itemBitmapHeight * index + itemBitmapHeight > sourceBitmap.height) {
                itemBitmapHeight = sourceBitmap.height - itemBitmapHeight * index
            }
            fg = Bitmap.createBitmap(sourceBitmap, 0, itemBitmapHeight * index, sourceBitmap.width, itemBitmapHeight)
            bg = BitMapUtil.generateBackgroundBitmap(fg.width, fg.height, Color.WHITE)
            var zheDieBean = ZheDieBean(0, itemBitmapHeight * index, 0f, false, fg, bg)
            if (index == 0) {
                zheDieBean.canSee = true //默认第一个可见
            }
            listBitMap.add(zheDieBean)
        }
    }


    override fun onDraw(canvas: Canvas) {
        if (label == Label.unfold) {
            unFoldDraw(canvas)
        } else {
            foldDraw(canvas)
        }
    }

    //开始展开动画
    fun startUnfoldAnimal() {
        initData()
        label = Label.unfold
        startAngle = 360f
        endAngle = 180f

        listAnimal.clear()
        for (index in 0 until listBitMap.size - 1) {//减1是为了不给最后一个view添加动画
            listAnimal.add(prepareAnimal(listBitMap[index], listBitMap[index + 1]))
        }
        mAnimalSet.playSequentially(listAnimal)//一个动画接一个动画的执行
        mAnimalSet.interpolator = interpolator
        mAnimalSet.start()
    }

    //开始折叠动画
    fun startFoldAnimal() {
        initData()
        prePareFoldData()
        label = Label.fold
        startAngle = 0f
        endAngle = 180f

        listAnimal.clear()
        for (index in listBitMap.size - 1 downTo 1) {//不给第一个添加动画
            listAnimal.add(prepareAnimal(listBitMap[index], listBitMap[index - 1]))
        }
        mAnimalSet.playSequentially(listAnimal)//一个动画接一个动画的执行
        mAnimalSet.interpolator = interpolator
        mAnimalSet.start()
    }

    private fun prepareAnimal(zheDieBean: ZheDieBean, nextZheDieBean: ZheDieBean): Animator {
        return ValueAnimator.ofFloat(startAngle, endAngle).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                zheDieBean.angle = it.animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (label == Label.unfold) {
                        nextZheDieBean.canSee = true
                    } else {
                        zheDieBean?.let {
                            it.canSee = false
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
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


    //折叠绘制
    private fun foldDraw(canvas: Canvas) {
        canvas.save()
        for (index in 0 until listBitMap.size) {
            var indexx = index
            var zheDieBean = listBitMap[indexx]
            mCamera.save()
            mCamera.rotateX(zheDieBean.angle)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.getValues(divideTempFloat)
            divideTempFloat[6] = divideTempFloat[6] / flipScale
            divideTempFloat[7] = divideTempFloat[7] / flipScale
            mMatrix.setValues(divideTempFloat)

            mMatrix.preTranslate(-zheDieBean.fg.width / 2.toFloat(), 0f)
            mMatrix.postTranslate(zheDieBean.fg.width / 2.toFloat(), 0f)

            if (zheDieBean.canSee) {
                mMatrix.postTranslate(0f, zheDieBean.fg.height * index.toFloat())
                mFgMatrix.setTranslate(0f, zheDieBean.fg.height * (index).toFloat())
                if (zheDieBean.angle < 90) {
                    canvas.drawBitmap(zheDieBean.fg, mMatrix, mPaint)
                } else {
                    canvas.drawBitmap(zheDieBean.bg, mMatrix, mPaint)//白色的东西
                }
            }
            if (indexx + 1 < listBitMap.size) {
                if (!listBitMap[indexx + 1].canSee) {
                    canvas.drawBitmap(zheDieBean.bg, mMatrix, mPaint)//白色的东西
                }
            }
        }
        canvas.restore()

    }


    //展开绘制
    private fun unFoldDraw(canvas: Canvas) {
        canvas.save()
        for (index in 0 until listBitMap.size - 1) {
            var zheDieBean = listBitMap[index]
            mCamera.save()
            mCamera.rotateX(zheDieBean.angle)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.getValues(divideTempFloat)
            divideTempFloat[6] = divideTempFloat[6] / flipScale
            divideTempFloat[7] = divideTempFloat[7] / flipScale
            mMatrix.setValues(divideTempFloat)

            mMatrix.preTranslate(-zheDieBean.fg.width / 2.toFloat(), -zheDieBean.fg.height.toFloat())
            mMatrix.postTranslate(zheDieBean.fg.width / 2.toFloat(), zheDieBean.fg.height.toFloat())


            if (zheDieBean.canSee) {
                mMatrix.postTranslate(0f, zheDieBean.bg!!.height * (index).toFloat())
                mFgMatrix.setTranslate(0f, zheDieBean.fg.height * (index).toFloat())

                if (index == listBitMap.size - 2 && zheDieBean.angle != 0f && zheDieBean.angle < 270f) {
                    canvas.drawBitmap(zheDieBean.fg, mFgMatrix, mPaint)//绘制遮挡物体
                    if (lastBitmap == null) {
                        lastBitmap = Bitmap.createBitmap(listBitMap[index + 1].fg, 0, 0, sourceBitmap.width, listBitMap[index + 1].fg.height, matrixScale, false)
                    }
                    canvas.drawBitmap(lastBitmap!!, mMatrix, mPaint)//绘制图片真实内容
                } else {
                    canvas.drawBitmap(zheDieBean.fg, mFgMatrix, mPaint)//绘制遮挡物体
                    canvas.drawBitmap(zheDieBean.bg, mMatrix, mPaint)//绘制图片真实内容
                }
            }
        }
        canvas.restore()

    }

    //准备折叠的数据资源
    private fun prePareFoldData() {
        for (value in listBitMap) {
            value.canSee = true
            value.angle = 0f
        }
    }
}