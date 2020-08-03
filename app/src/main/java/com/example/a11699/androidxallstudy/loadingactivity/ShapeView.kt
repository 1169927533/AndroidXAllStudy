package com.example.a11699.androidxallstudy.loadingactivity

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/8/2
 *Create Yu
 *description:
 */
class ShapeView : View {
    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    enum class Shape{
        Circle,
        Square,
        
    }

}