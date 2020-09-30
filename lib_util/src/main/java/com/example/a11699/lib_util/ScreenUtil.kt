package com.example.a11699.lib_util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 *Create time 2020/9/29
 *Create Yu
 *description:
 */
object ScreenUtil {
    fun getScreenWidth(context: Activity): Int {
        val manager: WindowManager = context.windowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
        val height2: Int = outMetrics.heightPixels
    }

    fun getScreenHeight(context: Activity): Int {
        val manager: WindowManager = context.windowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}