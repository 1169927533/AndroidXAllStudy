package com.example.a11699.androidxallstudy.cunstombarrage.me

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.a11699.androidxallstudy.R
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 *Create time 2020/6/20 9:30
 *Create Yu
 *description:
 * 实现思路：
 * 1.在屏幕首先绘制两个一模一样的view
 *  目的：当第一组view运行到 到快出屏幕的时候 第二组view立马跟上 实现在循环播放的效果
 *       两组view的动画执行依次衔接
 *
 * 2.每一组view里面的子view他们的移动路程必须一样。
 *  目的：如果某个view的距离比其他短 那么 他执行一组动画的时间要比其他短 这样会出现超车的情况出现view的重合
 *
 * 3.下一组动画执行条件：第一个view出屏幕 最后一个view刚进入屏幕
 *
 */
class MyBarrageRelayout : ConstraintLayout {
    lateinit var mContext: Context
    lateinit var layoutParams: ConstraintLayout.LayoutParams
    private var textViewList = ArrayList<ItemViewBean>()    //Textview 1数组
    private var textViewList_two = ArrayList<ItemViewBean>()//Textview 2数组

    private var parentWidth: Int = 0//父控件的宽度
    private var topMarginLeft = 0
    private var centerMarginLeft = 0
    private var bottomMarginLeft = 0
    private var themaxChildWidth = 0
    var startAnimal = false //在onMeasure标记动画只执行一次

    var initSpeed: Int = 200 //播放速度 这个可以自己调整
    var textViewItemClick: ((content: String) -> Unit)? = null //item的点击事件

    private var textviewlistOneanimalIsstart = false //动画数组1是否在运行
    private var textviewlistTwoanimalIsstart = false //动画数组2是否在运行

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    //这个方法外部调用 只需要传入要显示的内容就好
    fun addBarrageView(contentList: List<String>) {
        textViewList.clear()
        textViewList_two.clear()
        for ((index, value) in contentList.withIndex()) {
            when ((index + 1) % 3) {
                1 -> {
                    initTextView(value, Location.TOP)
                }
                2 -> {
                    initTextView(value, Location.CENTER)
                }
                0 -> {
                    initTextView(value, Location.BOTTOM)
                }
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        Log.i("zjc", parentWidth.toString())
        /*以上是计算父控件的宽度*/
        /*以下是计算子控件的宽度*/
        var childCount = childCount
        topMarginLeft = 0
        centerMarginLeft = 0
        bottomMarginLeft = 0
        for (i in 0 until childCount / 2) {
            measureChild(getChildAt(i * 2), widthMeasureSpec, heightMeasureSpec)
            var childWidth = getChildAt(i * 2).measuredWidth
            if (themaxChildWidth <= childWidth) {
                themaxChildWidth = childWidth
            }
            var randomNumber = (Math.random() * (100 - 50) + 50).roundToInt().toInt()
            when ((i + 1) % 3) {
                1 -> {
                    textViewList[i].setLeftdistance(topMarginLeft)
                    textViewList_two[i].setLeftdistance(topMarginLeft)
                    topMarginLeft += childWidth + randomNumber
                }
                2 -> {
                    textViewList[i].setLeftdistance(centerMarginLeft)
                    textViewList_two[i].setLeftdistance(centerMarginLeft)
                    centerMarginLeft += childWidth + randomNumber
                }
                0 -> {
                    textViewList[i].setLeftdistance(bottomMarginLeft)
                    textViewList_two[i].setLeftdistance(bottomMarginLeft)
                    bottomMarginLeft += childWidth + randomNumber
                }
            }
            textViewList[i].setChildWidth(childWidth)
            textViewList_two[i].setChildWidth(childWidth)
        }
        if (!startAnimal) {
            startAnimal = true
            startAnimalList_One()
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }


    private fun initTextView(content: String, location: Location) {
        var textView: TextView = initTextViewStyle(content, location)
        textViewList.add(ItemViewBean(textView, content, 0, 0, null))
        var textView2: TextView = initTextViewStyle(content, location)
        textViewList_two.add(ItemViewBean(textView2, content, 0, 0, null))
        addView(textView)
        addView(textView2)
        textView.setOnClickListener {
            textViewItemClick?.let {
                it.invoke(textView.text.toString())
            }
        }
        textView2.setOnClickListener {
            textViewItemClick?.let {
                it.invoke(textView.text.toString())
            }
        }

    }

    private fun initTextViewStyle(content: String, location: Location): TextView {
        layoutParams = ConstraintLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        var textView = TextView(mContext)
        textView.setText(content)
        textView.setBackgroundResource(R.drawable.danmu_bg)
        textView.gravity = Gravity.CENTER
        textView.setPadding(dip2px(11), dip2px(5), dip2px(11), dip2px(5))
        textView.setTextColor(resources.getColor(R.color.colorFFFFFF))
        when (location) {
            Location.TOP -> {
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.leftToRight = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Location.CENTER -> {

                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.leftToRight = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Location.BOTTOM -> {
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.leftToRight = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
        textView.layoutParams = layoutParams
        return textView
    }

    //textViewList的动画
    private fun startAnimalList_One() {
        for (value in textViewList) {
            createDropAnimator(value, textViewList)
        }
        var vcango = false
        var animal2 = textViewList[0].objectAnimator //当地一个item以进跑到了屏幕的另一边 也就是看不到的地方 才能去执行textViewList的动画
        animal2.addUpdateListener { valueAnimator ->
            var ds = valueAnimator.animatedValue as Float
            if (Math.abs(ds) > parentWidth) {
                vcango = true
            }
        }
        var animal = textViewList[childCount / 2 - 1].objectAnimator //最后一个item 当最后一个item全部出现到屏幕的时候 才能去执行textViewList的动画
        animal.addUpdateListener { valueAnimator ->
            var hanRunDistance = valueAnimator.animatedValue as Float //已经移动的距离
            if (vcango && abs(hanRunDistance) > textViewList[childCount / 2 - 1].leftdistance + textViewList[childCount / 2 - 1].childWidth) {
                if (!textviewlistOneanimalIsstart) {
                    textviewlistOneanimalIsstart = true
                    textviewlistTwoanimalIsstart = false
                    vcango = false
                    startAnimalList_Two()
                }
            }
        }
    }

    //textViewList_two的动画
    private fun startAnimalList_Two() {
        for (value in textViewList_two) {
            createDropAnimator(value, textViewList_two)
        }
        var vango = false
        var animal2 = textViewList_two[0].objectAnimator
        animal2.addUpdateListener { valueAnimator ->
            var ds = valueAnimator.animatedValue as Float
            if (Math.abs(ds) > parentWidth) {
                vango = true
            }
        }
        var animal = textViewList_two[childCount / 2 - 1].objectAnimator
        animal.addUpdateListener { valueAnimator ->
            var hanRunDistance = valueAnimator.animatedValue as Float//已经行走的值
            if (vango && abs(hanRunDistance) > textViewList_two[childCount / 2 - 1].leftdistance + textViewList[childCount / 2 - 1].childWidth) {
                if (!textviewlistTwoanimalIsstart) {
                    vango = false
                    textviewlistTwoanimalIsstart = true
                    textviewlistOneanimalIsstart = false
                    startAnimalList_One()
                }
            }
        }
    }


    private fun createDropAnimator(itemViewBean: ItemViewBean, listcontent: List<ItemViewBean>) {
        //这个算的是一个view数组里面最后一个item走完全程的距离也就是从屏幕右边走到屏幕左边的距离
        //目的：让每个view在屏幕运行的速度是一样的 防止view间的重叠
        var runDistance = parentWidth.toFloat() + listcontent[childCount / 2 - 1].leftdistance + themaxChildWidth
        var translationX: ObjectAnimator = ObjectAnimator.ofFloat(itemViewBean.getView(), "translationX", 0f, -(parentWidth.toFloat() + listcontent[childCount / 2 - 1].leftdistance + themaxChildWidth))
        itemViewBean.objectAnimator = translationX
        var ll = itemViewBean.getView().layoutParams as LayoutParams
        ll.leftMargin = itemViewBean.leftdistance
        itemViewBean.getView().layoutParams = ll
        translationX.interpolator = LinearInterpolator()
        //translationX.repeatCount = Animation.INFINITE
        //动画时间 距离/速度  速度自己给
        translationX.setDuration((runDistance / initSpeed).toLong() * 1000)
        translationX.start()
    }

    fun dip2px(dpValue: Int): Int {
        var scale = mContext.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f).toInt()
    }
}