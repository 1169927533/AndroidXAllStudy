package com.example.a11699.module_flop.customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.example.a11699.lib_util.BitMapUtil

/**
 *Create time 2020/10/16
 *Create Yu
 *description:自定义实现可以反转的viewgroup
 */
class FlopViewGroup : FrameLayout {
    private var frontView: View? = null
    private var backView: View? = null
    private var frontBitmap: Bitmap? = null
    private var backBitmap: Bitmap? = null

    init {
        setWillNotDraw(false)
    }

    companion object {
        private fun Bitmap.reverseY(): Bitmap {
            val m = Matrix()
            m.setScale(-1F, 1F)
            return Bitmap.createBitmap(this, 0, 0, this.width, this.height, m, false)
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        mCamera.setLocation(0f, 0f, -10f)
        flipScale = resources.displayMetrics.density
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (childCount >= 2) {
            backView = getChildAt(0)
            frontView = getChildAt(1)
            frontBitmap = frontView?.let { BitMapUtil.getView2BitMap(it) }
            backBitmap = backView?.let { BitMapUtil.getView2BitMap(it).reverseY() }
        }
    }

    private var mCamera = Camera()
    private var mMatrix = Matrix()
    private var rotateAngle = 0f//旋转角度
    private val divideTempFloat = FloatArray(9)
    private var flipScale = 0.2f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (backBitmap != null) {
            mCamera.save()
            mCamera.rotateY(rotateAngle)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.getValues(divideTempFloat)
            divideTempFloat[6] = divideTempFloat[6] / flipScale
            divideTempFloat[7] = divideTempFloat[7] / flipScale
            mMatrix.setValues(divideTempFloat)

            mMatrix.preTranslate(-(backBitmap?.width!! / 2.toFloat()), -0f)
            mMatrix.postTranslate(backBitmap?.width!! / 2.toFloat(), 0f)

            if (rotateAngle > 90f) {
                canvas.drawBitmap(backBitmap!!, mMatrix, paint)
            } else {
                if (frontBitmap != null) {
                    canvas.drawBitmap(frontBitmap!!, mMatrix, paint)
                }
            }
        }
    }

    //开始反转动画
    fun startAnimal() {
        val animal = ValueAnimator.ofFloat(0f, 180f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                setRotate((it.animatedValue) as Float)
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    backView!!.visibility = View.VISIBLE

                }

                override fun onAnimationStart(animation: Animator?) {
                    frontView!!.visibility = GONE
                    backView!!.visibility = GONE
                }

                override fun onAnimationCancel(animation: Animator?) {

                }


            })
        }
        animal.start()
    }

    private fun setRotate(rotate: Float) {
        this.rotateAngle = rotate
        invalidate()
    }
}