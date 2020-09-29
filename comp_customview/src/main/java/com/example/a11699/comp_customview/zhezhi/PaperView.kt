package com.example.a11699.comp_customview.zhezhi

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.a11699.comp_customview.R
import java.lang.IndexOutOfBoundsException
import kotlin.math.cos

/**
 *Create time 2020/9/19
 *Create Yu
 *description:
 */
class PaperView : FrameLayout {
    private val divideTempFloat = FloatArray(9)
    private var flipScale = 0.2f

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

    private var paint = Paint()

    private val angleEnd = 0F
    private val angleStart = 180F
    private var status = STATUS_SMALL
    private var duration = 4000L //动画持续时间
    private var bgColor = Color.WHITE //翻折时的卡片背景色
    private var contentChanged = false //标记本次动画是否需要重新获取view Bitmap

    private var animating = false //是否正在进行动画 因为在进行动画的时候我会动态修改控件的高度 在onMeasure里面进行的操作
    private var animatorSet: AnimatorSet? = null
    private var childRequireHeight = -1f//需要绘制的高度 这个是根据动画执行的时候动态修改的


    private var smallChild: View? = null
    private var largeChild: View? = null
    private var paperList: MutableList<PaperInfo> = ArrayList<PaperInfo>()


    private val divideMatrix = Matrix()
    private val divideCamera = Camera()
    private val interpolator = AccelerateInterpolator()

    init {
        setWillNotDraw(false) //在 viewgroup里面你重写onDraw方法时候 如果你不给viewgroup添加一个背景 或者 设置 setWillNotDraw(false) 那么你重写的onDraw方法将不会被执行
        paint.color = Color.BLACK
        paint.isAntiAlias = true
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initAttrs(attributeSet)
    }

    private fun initAttrs(attributeSet: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, (R.styleable.PaperView))
        val color = typeArray.getColor(R.styleable.PaperView_paper_bg_color, Color.WHITE)
        val duration = typeArray.getInt(R.styleable.PaperView_paper_duration, duration.toInt())
        typeArray.recycle()
        this.duration = duration.toLong()
        this.bgColor = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != 2) {
            throw IndexOutOfBoundsException("我这个view只能包含两个2View")
        }
        val myWidth = MeasureSpec.getSize(widthMeasureSpec)//父的宽度
        if (animating) {
            setMeasuredDimension(myWidth, (paddingTop + paddingBottom + childRequireHeight).toInt())
            return
        }
        //这里有个不好的效果 如果你在布局里面把两个子view的位置放反了那不行了 要出问题了
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.visibility = View.VISIBLE
            measureChild(child, widthMeasureSpec, heightMeasureSpec)//测量子view
            if (smallChild == null) {
                smallChild = child
            } else if (smallChild!!.measuredHeight > child.measuredHeight) {
                largeChild = smallChild
                smallChild = child
            }
        }
        if (smallChild == null || largeChild == null) {
            return
        }
        val smallChild = this.smallChild!!
        val largeChild = this.largeChild!!
        //根据不同状态计算需要的尺寸
        when (status) {
            STATUS_SMALL -> {
                smallChild.visibility = View.VISIBLE
                largeChild.visibility = GONE

                setMeasuredDimension(myWidth, smallChild.measuredHeight + paddingTop + paddingBottom)
            }
            STATUS_S_TO_L -> {
                setMeasuredDimension(myWidth, smallChild.measuredHeight + paddingTop + paddingBottom)
                animateLargeImpl()
                smallChild.visibility = GONE
                largeChild.visibility = GONE
            }
            STATUS_LARGE -> {
                smallChild.visibility = GONE
                largeChild.visibility = View.VISIBLE
                setMeasuredDimension(myWidth, largeChild.measuredHeight + paddingTop + paddingBottom)
            }
            STATUS_L_TO_S -> {
                setMeasuredDimension(myWidth, largeChild.measuredHeight + paddingTop + paddingBottom)
                animateSmallImpl()
                smallChild.visibility = GONE
                largeChild.visibility = GONE
            }
        }
    }
    /**
     * 展开
     * @param animator 是否执行动画
     * @param changed 内容是否发生变化
     */
    fun unfold(animator: Boolean = true, changed: Boolean = false) {
        status = if (animator) STATUS_S_TO_L else STATUS_LARGE
        contentChanged = changed
        requestLayout()
    }

    /**
     * 收起
     * @param animator 是否执行动画
     * @param changed 内容是否发生变化
     */
    fun fold(animator: Boolean = true, changed: Boolean = false) {
        status = if (animator) STATUS_L_TO_S else STATUS_SMALL
        contentChanged = changed
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        if (status == STATUS_SMALL || status == STATUS_LARGE) {
            return
        }
        if (paperList == null)
            return
        canvas!!.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        childRequireHeight = 0f
        paperList.forEach {
            val itemHeight = flipBitmap(canvas, it)
            if (itemHeight > 0f) {
                childRequireHeight += itemHeight
            }
        }
        canvas.restore()
        requestLayout()
    }
    /**
     * 绘制每个单个view
     */
    private fun flipBitmap(canvas: Canvas, store: PaperInfo): Float {
        if (!store.visible) {
            return 0f
        }
        val angle = store.angle
        val x = store.x
        val y = store.y

        val centerX = store.fg.width / 2.0f
        val centerY = store.fg.height / 2.0f
        divideMatrix.reset()

        divideCamera.save()
        divideCamera.rotate(angle, 0.0f, 0.0f)//进行三维的旋转
        divideCamera.getMatrix(divideMatrix)//将矩阵 赋值到 新建的矩阵参数里面
        divideCamera.restore()

        /**
         * 修正旋转时的透视 MPERSP_0
         */
        divideMatrix.getValues(divideTempFloat)
        divideTempFloat[6] = divideTempFloat[6] * flipScale
        divideTempFloat[7] = divideTempFloat[7] * flipScale
        divideMatrix.setValues(divideTempFloat)

        //将旋转的轴点调到（-centerX , 0） 的位置
        divideMatrix.preTranslate(-centerX, 0.0f)
        // 旋转完之后再回到原来的位置
        divideMatrix.postTranslate(centerX, 0.0F)
        // 移动到指定位置
        divideMatrix.postTranslate(x, y)

        val bitmap = getProperBitmap(store)
        val amount = (Math.sin((Math.toRadians(angle.toDouble())))).toFloat() * (-255F / 4)
        // 调整亮度
        adjustBrightness(amount)
        canvas.drawBitmap(bitmap, divideMatrix, paint)
        return (bitmap.height * cos(Math.toRadians(angle.toDouble()))).toFloat()
    }

    private fun adjustBrightness(amount: Float) {
        val colorMatrix = ColorMatrix(floatArrayOf(
                1F, 0F, 0F, 0F, amount,
                0F, 1F, 0F, 0F, amount,
                0F, 0F, 1F, 0F, amount,
                0F, 0F, 0F, 1F, 0F
        ))
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixFilter
    }

    private fun getProperBitmap(store: PaperInfo): Bitmap {
        val angle = store.angle
        if (isForeground(angle)) {
            // 根据角度计算要显示前面，但是由于前面有遮挡物
            // 这个遮挡物就是下一个折叠的背面
            if (store.next != null
                    && store.next!!.angle == angleStart) {
                if (store.next!!.bg.height < store.bg.height) {
                    return store.bg
                } else {
                    return store.next!!.bg
                }
            } else {
                return store.fg
            }
        } else {
            // 背部同理，可能有前一个折叠的背面遮挡
            if (store.prev != null
                    && store.prev!!.bg.height > store.bg.height) {
                return store.prev!!.bg
            } else {
                return store.bg
            }
        }
    }
    /**
     * 展开动画
     */
    private fun animateLargeImpl() {
        preAnimal()//动画资源的准备
        largeReset()
        val set = AnimatorSet()
        val list = ArrayList<Animator>()
        val eachDuration = duration / paperList!!.size
        paperList?.forEachIndexed { index, it ->
            // 第一个不做动画
            if (index != 0)
                list.add(animate(it, angleStart, angleEnd, true, eachDuration))
        }
        set.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                status = STATUS_SMALL
                animating = false
                requestLayout()

            }
        })
        set.playSequentially(list)//当上一个动画结束后 会一次执行下一个
        startAnimator(set)
    }

    private fun animate(store: PaperInfo,
                        from: Float,
                        to: Float,
                        visibleOnEnd: Boolean,
                        duration: Long): Animator {
        val animator = ValueAnimator.ofFloat(from, to)
        animator.duration = duration
        animator.addUpdateListener { value ->
            store.angle = value.animatedValue as Float
            invalidate()
        }
        animator.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator?) {
                store.visible = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                store.visible = visibleOnEnd
            }
        })
        return animator
    }

    private fun startAnimator(set: AnimatorSet) {
        set.interpolator = interpolator
        set.start()
        animatorSet = set
        animating = true
    }

    private fun largeReset() {
        paperList?.forEach { nextStore ->
            nextStore.angle = angleStart
            nextStore.visible = false
        }
        paperList?.first()?.visible = true
        paperList?.first()?.angle = angleEnd
    }

    private open class SimpleAnimatorListener : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {

        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationStart(animation: Animator?) {

        }
    }

    /**
     * 收起动画
     */
    private fun animateSmallImpl() {
        preAnimal()
        smallReset()
        val set = AnimatorSet()
        val list = ArrayList<Animator>()
        val eachDuration = duration / paperList!!.size
        // 缩小动画是倒着执行的
        for (index in (paperList!!.size - 1) downTo 0) {
            val store = paperList!![index]
            // 第一个不做动画
            if (index != 0)
                list.add(animate(store, angleEnd, angleStart, false, eachDuration))
        }
        set.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                status = STATUS_SMALL
                animating = false
                requestLayout()

            }
        })
        set.playSequentially(list)
        startAnimator(set)
    }

    private fun smallReset() {
        paperList?.forEach { nextStore ->
            nextStore.angle = angleEnd
            nextStore.visible = true
        }
        paperList?.first()?.angle = angleEnd
    }

    /**
     * 准备动画资源
     */
    private fun preAnimal() {
        if (animating && animatorSet != null && animatorSet!!.isRunning) {
            //当动画还在进行的时候 我们需要将他先停掉
            animatorSet?.end()
            animatorSet = null
        }
        animating = false
        if (paperList.isEmpty() || contentChanged) {
            contentChanged = false
            paperList.clear()
            paperList = getDividedBitmap(getSmallBitmap().reverseY(), getLargeBitmap())//拆分整个打view
        }
    }


    /**
     * 拆分整个view
     */
    private fun getDividedBitmap(smallBitmap: Bitmap, largeBitmap: Bitmap): MutableList<PaperInfo> {
        val desireWidth = largeBitmap.width
        val desireHeight = largeBitmap.height

        val list = ArrayList<PaperInfo>()

        val x = 0
        val divideItemWidth = smallBitmap.width
        val divideItemHeight = smallBitmap.height
        var nextDividerItemHeight = divideItemHeight.toFloat()
        var divideYOffset = 0F
        val count = desireHeight / divideItemHeight + if (desireHeight % divideItemHeight == 0) 0 else 1
        var prevStore: PaperInfo? = null
        for (i in 0 until count) {
            if (divideYOffset + nextDividerItemHeight > desireHeight) {
                nextDividerItemHeight = desireHeight - divideYOffset
            }
            val fg = Bitmap.createBitmap(largeBitmap, x, divideYOffset.toInt(), divideItemWidth, nextDividerItemHeight.toInt())
            val bg = if (i == 1) smallBitmap else generateBackgroundBitmap(fg.width, fg.height)
            val store = PaperInfo(false, x.toFloat(), divideYOffset, 180F, fg, bg, prevStore, null)
            list.add(store)
            prevStore?.next = store
            prevStore = store
            divideYOffset += divideItemHeight
        }
        return list
    }







    private fun isForeground(angle: Float) = ((angle - 1) % 180) <= 90


    private fun getSmallBitmap(): Bitmap {
        return getBitmap(smallChild!!)
    }

    private fun getLargeBitmap(): Bitmap {
        return getBitmap(largeChild!!)
    }

    /**
     * 将view转换成bitmap
     */
    private fun getBitmap(child: View): Bitmap {
        val bitmap = Bitmap.createBitmap(child.measuredWidth, child.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        child.layout(0, 0, child.measuredWidth, child.measuredHeight)
        child.draw(canvas)
        return bitmap
    }


    private fun generateBackgroundBitmap(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        canvas.drawColor(bgColor)
        return bitmap
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        paperList?.clear()
        animatorSet?.end()

        animatorSet = null

        smallChild = null
        largeChild = null
    }

}