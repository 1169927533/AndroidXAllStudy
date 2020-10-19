package com.example.a11699.androidxallstudy.bitmap

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import com.example.a11699.lib_util.dp

/**
 *Create time 2020/10/17
 *Create Yu
 *description:
 */
class DrawableView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val drawable = ColorDrawable(Color.RED)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(50.dp.toInt(),80.dp.toInt(),width,height)
        drawable.draw(canvas) 
    }
}