package com.example.a11699.comp_customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/6/26
 *Create Yu
 *description:
 */
class MyTextView : View {
    constructor(context: Context) : super(context) {
    }
    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet) {
    }
    constructor(context: Context,attributeSet: AttributeSet,detStyleAttr:Int) : super(context,attributeSet,detStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        when(widthMode){
            MeasureSpec.AT_MOST->{
                //对应 wrap_content
            }
            MeasureSpec.EXACTLY->{
                //指定确切值 100dp match_parent fill_parent
            }
            MeasureSpec.UNSPECIFIED->{
                //尽可能的大 很少用到 ListView ScrollView会用到
           }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}