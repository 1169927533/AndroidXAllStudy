package com.example.a11699.androidxallstudy.soul.view

import android.content.res.Resources
import android.util.TypedValue

/**
 *Create time 2020/9/23
 *Create Yu
 *description:
 */
//dp 2 px
val Float.px: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)