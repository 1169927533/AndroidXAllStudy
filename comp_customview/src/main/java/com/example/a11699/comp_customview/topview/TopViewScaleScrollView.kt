package com.example.a11699.comp_customview.topview

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView

/**
 * @Author Yu
 * @Date 2020/11/24 16:18
 * @Description 当页面下拉的时候 tag标记为 topview 的viewgroup 或者 view 会实现放大效果。然后松手后 页面会回弹
 */
class TopViewScaleScrollView : NestedScrollView {
    private var isInit = false //是否已经初始化过
    private var topViewHeight = 0 //顶部要缩放的view的原始高度
    private lateinit var mTopView: View //会随着页面的滑动做出响应的view

    constructor(context: Context) : super(context)
    constructor(context: Context, attribute: AttributeSet) : super(context, attribute)


    /**
     * 这一步骤是为了拿到我们需要操作的view 和 拿到 操作的view的起始高度
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isInit) {
            isInit = true
            var viewe = getChildAt(0) as ViewGroup
            for (value in 0 until viewe.childCount) {
                var targetView = viewe.getChildAt(value)
                if (targetView.tag == "topview") {
                    mTopView = targetView
                    break
                }
            }
        }
        /*
        A BoringLayout is a very simple Layout implementation for text that fits on a single line and is all left-to-right characters. You will probably never want to make one of these yourself; if you do, be sure to call isBoring(CharSequence, TextPaint) first to make sure the text meets the criteria.

This class is used by widgets to control text layout. You should not need to use this class directly unless you are implementing your own widget or custom display object, in which case you are encouraged to use a Layout instead of calling Canvas.drawText() directly.
         */
        if (topViewHeight == 0) {
            measureChildren(widthMeasureSpec, heightMeasureSpec)
         //   measureChild(mTopView, widthMeasureSpec, heightMeasureSpec)
            topViewHeight = mTopView.measuredHeight
        }
    }

    private var mCurrentOffset = 0 //滑动的距离
    private var isTouchOne = false //是否是一次连续的move
    private var startY = 0f
    private var distance = 0f //拖动的距离
    private var isBig = false //是都执行放大
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_MOVE -> {
                if (mCurrentOffset <= 0) {
                    if (!isTouchOne) {
                        startY = ev.y
                        isTouchOne = true
                    }
                    distance = ev.y - startY
                    if (distance > 0) {
                        isBig = true
                        setTransLation((-distance / 4).toInt())
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isBig) {
                    isBig = false
                    resectViewState()
                }
                isTouchOne = false
            }
        }
        return super.onTouchEvent(ev)
    }


    /**
     * 缩放动画
     */
    private var scaleAnimal: ObjectAnimator?   = null
    /**
     * 重置view的状态
     */
    private fun resectViewState() {
        if (scaleAnimal != null && scaleAnimal!!.isRunning()) {
            return;
        }
        scaleAnimal = ObjectAnimator.ofInt(this, "transLation", (-distance / 4).toInt(), 0);
        scaleAnimal!!.duration = 150;
        scaleAnimal!!.start()
    }
    /**
     * 偏移动画
     */
    private fun setTransLation(dDistance:Int){
        scrollTo(0,0)
        if(dDistance<0){
            mTopView!!.layoutParams.height = topViewHeight - dDistance
            mTopView!!.requestLayout()
        }
    }

    /**
         l  Current horizontal scroll origin.
         t  Current vertical scroll origin.
         oldl  Previous horizontal scroll origin.
         oldt  Previous vertical scroll origin.
     */
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mCurrentOffset = t //右边滑动标签相对于顶端的偏移量
        //当手势上滑，则右侧滚动条下滑，下滑的高度小于TopView的高度，则让TopView的上滑速度小于DownView的上滑速度
        //DownView的上滑速度是滚动条的速度，也就是滚动的距离是右侧滚动条的距离
        //则TopView的速度要小，只需要将右侧滚动条的偏移量也就是t缩小一定倍数就行了。我这里除以2速度减小1倍
        if (t <= topViewHeight && t >= 0 && !isBig) {
            //   mTopView!!.translationY = t / 2.toFloat() //使得TopView滑动的速度小于滚轮滚动的速度
        }
        if (isBig) {
            scrollTo(0, 0)
        }
    }
}