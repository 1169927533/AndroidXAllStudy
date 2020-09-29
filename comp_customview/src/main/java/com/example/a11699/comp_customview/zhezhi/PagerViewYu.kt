package com.example.a11699.comp_customview.zhezhi

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import com.example.a11699.comp_customview.R
import com.example.a11699.lib_util.BitMapUtil
import java.lang.IndexOutOfBoundsException
import java.nio.channels.FileLock
import kotlin.math.cos

/**
 *Create time 2020/9/27
 *Create Yu
 *description:
 */
class PagerViewYu : FrameLayout {
    private var mPaint: Paint = Paint()
    private var status = STATUS_SMALL
    private var bgColor: Int = Color.WHITE //这个属性还不知道啥意思
    private var animalDuration: Long = 4000 //折叠动画的执行时间
    private lateinit var smallChildView: View
    private lateinit var largeChildView: View

    init {
        setWillNotDraw(false)//由于我需要重写onDraw方法 所以我需要将这个flag设置为false
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
    }

    companion object {

        private val STATUS_SMALL = 1
        private val STATUS_LARGE = 2
        private val STATUS_S_TO_L = 3
        private val STATUS_L_TO_S = 4

        private fun Bitmap.reverseY(): Bitmap {
            val m = Matrix()
            m.setScale(1F, -1F)
            return Bitmap.createBitmap(this, 0, 0, this.width, this.height, m, false)
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initAttrs(attributeSet)
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PaperView)
        val color = typeArray.getColor(R.styleable.PaperView_paper_bg_color, Color.WHITE)
        val duration = typeArray.getInt(R.styleable.PaperView_paper_duration, animalDuration.toInt())
        typeArray.recycle()
        this.animalDuration = duration.toLong()
        this.bgColor = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != 2) {
            throw IndexOutOfBoundsException("我的父布局必须包含两个子view")
        }
        for (index in 0 until childCount) {
            measureChild(getChildAt(index), widthMeasureSpec, heightMeasureSpec)
        }
        val width = MeasureSpec.getSize(widthMeasureSpec)
        smallChildView = getChildAt(0)
        largeChildView = getChildAt(1)

        if (animating) {
            setMeasuredDimension(width, (paddingTop + paddingBottom + childRequireHeight).toInt())
            return
        }

        when (status) {
            STATUS_SMALL -> {
                smallChildView.visibility = View.VISIBLE
                largeChildView.visibility = View.GONE
                setMeasuredDimension(width, smallChildView.measuredHeight + paddingTop + paddingBottom)
            }
            STATUS_S_TO_L -> {
                setMeasuredDimension(width, smallChildView.measuredHeight + paddingTop + paddingBottom)
                animateLargeImpl()
                smallChildView.visibility = GONE
                largeChildView.visibility = GONE
            }
            STATUS_L_TO_S -> {

            }
            STATUS_LARGE -> {
                largeChildView.visibility = View.VISIBLE
                smallChildView.visibility = View.GONE
                setMeasuredDimension(width, largeChildView.measuredHeight + paddingBottom + paddingTop)
            }
        }
    }


    /**
     * 展开动画
     */
    private fun animateLargeImpl() {
        //先准备动画资源
        prepareAnimal()
        //设置动画
        val mAnimalSet = AnimatorSet()
        val listAnimal = ArrayList<Animator>()

        val eachDuration = animalDuration / paperItemViewList!!.size
        paperItemViewList?.forEachIndexed { index, paperInfo ->
            //第一个不做动画
            if (index != 0) {
                listAnimal.add(prePareAnimal(paperInfo, 180f, 0f, true, eachDuration))
            }
        }
        mAnimalSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                status = STATUS_LARGE
                animating = false
                requestLayout()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        mAnimalSet.playSequentially(listAnimal)//一个动画接一个动画的执行
        mAnimalSet.interpolator = interpolator
        mAnimalSet.start()
        this.animatorSet = mAnimalSet
        animating = true
    }

    private val angleStart = 180F
    private var animating = false //是否正在进行动画
    private var animatorSet: AnimatorSet? = null
    private var paperItemViewList: MutableList<PaperInfo>? = null//一个大view被等分为几份
    private val interpolator = AccelerateInterpolator()

    /**
     * 准备动画资源
     */
    private fun prepareAnimal() {
        if (animating && animatorSet != null && animatorSet!!.isRunning) {
            animatorSet?.end()
            animatorSet = null
        }
        animating = false
        if (paperItemViewList == null || paperItemViewList!!.isEmpty()) {
            paperItemViewList = getDivideBitMap(BitMapUtil.getView2BitMap(smallChildView).reverseY(), BitMapUtil.getView2BitMap(largeChildView))
        }
        paperItemViewList?.forEach { nextStore ->
            nextStore.angle = angleStart
            nextStore.visible = false
        }
        //改边第一个展示view的装态为可见
        paperItemViewList?.first()?.visible = true
        paperItemViewList?.first()?.angle = 0f

    }

    /**
     * 拆分bigview
     */
    private fun getDivideBitMap(smallBitmap: Bitmap, largeBitmap: Bitmap): MutableList<PaperInfo> {
        var list = ArrayList<PaperInfo>()
        val largeViewWidth = largeBitmap.width
        val largeViewHeight = largeBitmap.height
        var nextDividerItemHeight = smallBitmap.height.toFloat()

        val divideCount = largeViewHeight / smallBitmap.height + if (largeViewHeight % smallBitmap.height == 0) 0 else 1
        var paperInfo: PaperInfo? = null
        var divideOffectY = 0f//裁剪图片的偏移量
        for (index in 0 until divideCount) {
            /**
             * 每次裁剪大图 fg是展示的部分
             */
            if (divideOffectY + smallBitmap.height > largeViewHeight) {
                nextDividerItemHeight = largeViewHeight - divideOffectY
            }
            val fg = Bitmap.createBitmap(largeBitmap, 0, divideOffectY.toInt(), largeViewWidth, nextDividerItemHeight.toInt())
            Log.i("hhq", "fg:${divideOffectY} ${nextDividerItemHeight}")
            val bg = if (index == 1) smallBitmap else BitMapUtil.generateBackgroundBitmap(fg.width, fg.height, Color.WHITE)
            Log.i("hhq", "bg:${fg.width} ${fg.height}")

            val store = PaperInfo(false, 0f, divideOffectY, 180f, fg, bg, paperInfo, null)
            list.add(store)
            paperInfo?.next = store
            paperInfo = store
            divideOffectY += smallBitmap.height
        }
        return list
    }

    /**
     * 准备动画
     */
    private fun prePareAnimal(paperInfo: PaperInfo, from: Float, to: Float, visibleOnEnd: Boolean,
                              mDuration: Long): Animator {
        return ValueAnimator.ofFloat(from, to).apply {
            duration = mDuration
            addUpdateListener { value ->
                paperInfo.angle = value.animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    paperInfo.visible = visibleOnEnd
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    paperInfo.visible = true
                }
            })
        }
    }

    private var childRequireHeight = 0f
    override fun onDraw(canvas: Canvas?) {
        if (status == STATUS_SMALL || status == STATUS_LARGE) {
            return
        }
        if (paperItemViewList == null) {
            return
        }
        canvas!!.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        childRequireHeight = 0f
        Log.i("hhq", "paperItemViewList的大小:${paperItemViewList?.size}")
        paperItemViewList?.forEachIndexed { index, paperInfo ->
            val itemHeight = drawItemView(canvas, paperInfo)
            if (itemHeight > 0) {
                childRequireHeight += itemHeight
            }
            Log.i("hhq", "========================${index}")

        }
        canvas.restore()
        requestLayout()
    }

    /**
     * 展开
     * @param animator 是否执行动画
     * @param changed 内容是否发生变化
     */
    fun unfold(animator: Boolean = true, changed: Boolean = false) {
        status = if (animator) STATUS_S_TO_L else STATUS_LARGE
        requestLayout()
    }

    private val divideMatrix = Matrix()
    private val divideCamera = Camera()
    private val divideTempFloat = FloatArray(9)
    private var flipScale = 0.2F

    //绘制单个item
    private fun drawItemView(mCanvas: Canvas, paperInfo: PaperInfo): Float {
        if (!paperInfo.visible) {
            return 0f
        }
        val angle = paperInfo.angle
        val x = paperInfo.x
        val y = paperInfo.y

        val centerX = paperInfo.fg.width / 2f
        divideMatrix.reset()
        divideCamera.save()

        divideCamera.rotateX(angle)
        divideCamera.getMatrix(divideMatrix)//得到旋转后的矩阵
        divideCamera.restore()

        //修正旋转后的透视
        divideMatrix.getValues(divideTempFloat)
        divideTempFloat[6] = divideTempFloat[6] * flipScale
        divideTempFloat[7] = divideTempFloat[7] * flipScale
        divideMatrix.setValues(divideTempFloat)
        // 将锚点调整到 (-centerX,0) 的位置
        divideMatrix.preTranslate(-centerX, 0.0F)
        // 旋转完之后再回到原来的位置
        divideMatrix.postTranslate(centerX, 0.0F)

        // 移动到指定位置
        divideMatrix.postTranslate(x, y)
        Log.i("hhq", "bitmap的高度：${x} ：${y}")

        val bitmap = getProperBitmap(paperInfo)
        mCanvas.drawBitmap(bitmap, divideMatrix, mPaint)
        return (bitmap.height * cos(Math.toRadians(angle.toDouble()))).toFloat()
    }

    private fun getProperBitmap(store: PaperInfo): Bitmap {
        val angle = store.angle
        //根据角度来计算 应该展示哪个面
        if (isForeground(angle)) {
            // 根据角度计算要显示前面，但是由于前面有遮挡物
            // 这个遮挡物就是下一个折叠的背面
            return if (store.next != null
                    && store.next!!.angle == angleStart) {
                if (store.next!!.bg.height < store.bg.height) {
                    Log.i("zjc","1: ")
                    store.bg
                } else {
                    Log.i("zjc","2: ")
                    store.next!!.bg
                }
            } else {
                Log.i("zjc","3: ")
                store.fg
            }
        } else {
            // 背部同理，可能有前一个折叠的背面遮挡
            return if (store.prev != null
                    && store.prev!!.bg.height > store.bg.height) {
                store.prev!!.bg
            } else {
                store.bg
            }
        }
    }

    private fun isForeground(angle: Float) = ((angle - 1) % 180) <= 90
}