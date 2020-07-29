package com.example.a11699.androidxallstudy.soul

import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.*
import com.example.a11699.androidxallstudy.soul.adapter.BaseAdapter
import com.example.a11699.androidxallstudy.soul.bean.PointModel
import kotlin.math.abs

/**
 *Create time 2020/7/28
 *Create Yu
 *description:这里用来添加每个子view 并将它分布在圆形位置上
 */
class SoulViewGroup : ViewGroup, Runnable {
    private lateinit var mAdapter: BaseAdapter
    private var minSize = 0 //viewGroup的最小宽高
    var centerX: Float = 0f //球的中心坐标
    var centerY: Float = 0f //球的中心坐标
    var itemClick: ((point: PointModel) -> Unit)? = null
    private var mAngleX = 0.5f
    private var mAngleY = 0.5f
    var planetCalculator = PlanetCalculator() //计算器 计算每个view的分布
    private var handlerr: Handler = Handler()
    private var radius = 0f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        planetCalculator.planetModelCloud.clear()

        /**
         * 获取屏幕宽高
         */
        val wm = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val screenWidth: Int = point.x
        val screenHeight: Int = point.y
        minSize = if (screenHeight < screenWidth) screenHeight else screenWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var contentWidthSpec = MeasureSpec.getSize(widthMeasureSpec)
        var contentHeightSpec = MeasureSpec.getSize(heightMeasureSpec)

        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var contentWidth = if (widthMode == MeasureSpec.EXACTLY) contentWidthSpec else minSize
        var contentHeight = if (heightMode == MeasureSpec.EXACTLY) contentHeightSpec else minSize

        setMeasuredDimension(contentWidth, contentHeight)
        measureChildren(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.i("Zkc", "onLayout")
        for (index in 0 until childCount) {
            var child = getChildAt(index)
            var pointModel = planetCalculator.planetModelCloud[index]
            child.alpha = pointModel.scale
            var left = centerX + pointModel.locX_2 - child.measuredWidth / 2
            var top = centerY + pointModel.locY_2 - child.measuredHeight / 2
            child.tag = intArrayOf(left.toInt(), top.toInt())
            child.layout(left.toInt(), top.toInt(), (left + child.measuredWidth).toInt(), (top + child.measuredHeight).toInt())
        }
    }


    fun setAdapter(adapter: BaseAdapter) {
        mAdapter = adapter
        removeAllViews()
        this.post {//当view完成了onMeasure onlayout后才会调用
            //this.post回调两次onMeasue 和一次 onlayout结束时调用   post执行完onLayout又会在执行一次
            Log.i("Zkc", "setAdapter")
            centerX = width / 2f
            centerY = height / 2f
            // 半径
            // 半径
            radius = Math.min(centerX, centerY) * 0.8f
            planetCalculator.radius = (centerX * 0.8).toInt()
            for (value in 0 until mAdapter.getCount()) {
                var pointModel = PointModel("name" + value)
                pointModel.view = mAdapter.getView(context, value)

                pointModel.view.setOnClickListener {
                    itemClick?.let {
                        it.invoke(pointModel)
                    }
                }
                planetCalculator.add(pointModel)
                addView(pointModel.view)
            }
            planetCalculator.create(true)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("Zjc", "onTOuchEvent x: " + event?.x)
        Log.i("Zjc", "onTOuchEvent y: " + event?.y)
        if (event != null) {
            handleTouchEvent(event)
        }
        return true
    }

    //鼠标点击屏幕才会调用
    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        Log.i("Zjc", "onTrackballEvent x: " + event?.x)
        Log.i("Zjc", "onTrackballEvent y: " + event?.y)

        return true
    }

    private var isOnTouch = false
    private var downX = 0f
    private var downY: kotlin.Float = 0f
    private var startX = 0f
    private var startY = 0f
    private var startDistance = 0f
    private var multiplePointer = false
    private val TOUCH_SCALE_FACTOR = 1f
    private val TRACKBALL_SCALE_FACTOR = 10f
    val MAX_SCALE = 1.3f
    val MINI_SCALE = 1f

    /**
     * 处理触摸事件
     */
    private fun handleTouchEvent(event: MotionEvent): Boolean {
        // 触摸点个数
        val pointerCount = event.pointerCount
        if (pointerCount > 1) {
            multiplePointer = true
            Log.i("zjc", "触摸点数" + pointerCount)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isOnTouch = true
                downX = event.x
                downY = event.y
                startX = downX
                startY = downY
            }
            MotionEvent.ACTION_POINTER_DOWN -> if (event.actionIndex == 1) {
                // 第二个触摸点
                scaleX = scaleX
                startDistance = distance(event.getX(0) - event.getX(1),
                        event.getY(0) - event.getY(1))
                return true
            }

            MotionEvent.ACTION_MOVE -> if (pointerCount == 1 && !multiplePointer) {
                // 单点触摸，旋转星球
                val dx: Float = event.x - downX
                val dy: Float = event.y - downY
                if (isValidMove(dx, dy)) {
                    mAngleX = dy / radius * 4f * TOUCH_SCALE_FACTOR
                    mAngleY = -dx / radius * 4f * TOUCH_SCALE_FACTOR
                    processTouch()
                    downX = event.x
                    downY = event.y
                }
                return isValidMove(downX - startX, downY - startY)
            } else if (pointerCount == 2) {
                // 双点触摸，缩放
                val endDistance: Float = distance(event.getX(0) - event.getX(1),
                        event.getY(0) - event.getY(1))
                // 缩放比例
                var scale: Float = ((endDistance - startDistance) / (endDistance * 2) + 1) * scaleX
                if (scale > MAX_SCALE) {
                    scale = MAX_SCALE
                }
                if (scale < MINI_SCALE) {
                    scale = MINI_SCALE
                }
                scaleX = scale
                scaleY = scale
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                multiplePointer = false
                isOnTouch = false
            }
            else -> {
            }
        }
        return false
    }

    /**
     * 两点之间的距离
     *
     * @param x x轴距离
     * @param y y轴距离
     * @return 两点之间的距离
     */
    private fun distance(x: Float, y: Float): Float {
        return Math.sqrt(x * x + y * y.toDouble()).toFloat()
    }

    /**
     * 是否是有效移动
     *
     * @param dx x轴位移
     * @param dy y轴位移
     * @return 是否是有效移动
     */
    private fun isValidMove(dx: Float, dy: Float): Boolean {
        val minDistance = ViewConfiguration.get(context).scaledTouchSlop
        return Math.abs(dx) > minDistance || Math.abs(dy) > minDistance
    }

    override fun run() {
        if (!isOnTouch) {
            if (abs(mAngleX) > 0.2f) {
                mAngleX -= mAngleX * 0.1f
            }
            if (abs(mAngleY) > 0.2f) {
                mAngleY -= mAngleY * 0.1f
            }

            planetCalculator.mAngleX = mAngleX
            planetCalculator.mAngleY = mAngleY
            planetCalculator.updateByout()

            for (i in 0 until childCount) {
                val planetModel: PointModel = planetCalculator.planetModelCloud[i]
                val child: View = planetModel.view
                // 更新每一个ChildView
                if (child != null && child.visibility != View.GONE) {
                    // 设置透明度
                    child.alpha = planetModel.scale
                    val left = (centerX + planetModel.locX_2) - child.measuredWidth / 2
                    val top = (centerY + planetModel.locY_2) - child.measuredHeight / 2
                    // 从View的Tag里取出位置之前的位置信息，平移新旧位置差值
                    if (child.tag != null) {
                        val originLocation = child.tag as IntArray
                        if (originLocation != null && originLocation.isNotEmpty()) {
                            child.translationX = (left - originLocation[0]).toFloat()
                            child.translationY = (top - originLocation[1]).toFloat()
                            // 小于移动速度，刷新
                            child.invalidate()
                        }
                    }
                }
            }
        }


        handlerr.removeCallbacksAndMessages(null)
        handlerr.postDelayed(this, 30)


    }

    /**
     * 更新视图
     */
    private fun processTouch() {
        // 设置旋转的X,Y
        if (planetCalculator != null) {
            planetCalculator.mAngleX =(mAngleX)
            planetCalculator.mAngleY = (mAngleY)
            planetCalculator.updateByout()
        }
        for (i in 0 until childCount) {
            val planetModel: PointModel = planetCalculator.planetModelCloud[i]
            val child: View = planetModel.view
            // 更新每一个ChildView
            if (child != null && child.visibility != View.GONE) {

                // 缩放小于1的设置不可点击
                if (planetModel.scale < 1.0f) {
                    child.scaleX = planetModel.scale
                    child.scaleY = planetModel.scale
                    child.isClickable = false
                } else {
                    child.isClickable = true
                }
                // 设置透明度
                child.alpha = planetModel.scale
                val left = (centerX + planetModel.locX_2) - child.measuredWidth / 2
                val top = (centerY + planetModel.locY_2)   - child.measuredHeight / 2
                // 从View的Tag里取出位置之前的位置信息，平移新旧位置差值
                val originLocation = child.tag as IntArray
                if (originLocation != null && originLocation.isNotEmpty()) {
                    child.translationX = (left - originLocation[0]).toFloat()
                    child.translationY = (top - originLocation[1]).toFloat()
                    // 小于移动速度，刷新
                    if (abs(mAngleX) <= 4f && abs(mAngleY) <= 4f) {
                        child.invalidate()
                    }
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handlerr.post(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handlerr.removeCallbacksAndMessages(null)
    }

}