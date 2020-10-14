package com.example.a11699.module_flop.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/10/14
 *Create Yu
 *description:
 */
class ShellView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {
    var location = IntArray(2)
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        getLocationInWindow(location)
    }
}