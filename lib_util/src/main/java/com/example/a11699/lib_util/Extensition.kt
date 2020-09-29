package com.example.a11699.lib_util

import android.content.res.Resources
import android.util.TypedValue

/**
 *Create time 2020/9/27
 *Create Yu
 *description:
 */
val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.dp
    get() = this.toFloat().dp