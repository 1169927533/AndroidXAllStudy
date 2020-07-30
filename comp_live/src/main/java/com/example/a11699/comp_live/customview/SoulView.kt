package com.example.a11699.comp_live.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.a11699.comp_live.R

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
class SoulView : LinearLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.itme_bg, this,true)
    }


}