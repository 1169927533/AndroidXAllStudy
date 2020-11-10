package com.uppack.lksmall.baseyu.weight.util

import android.content.Context
import android.content.res.Resources

/**
 *Create time 2020/9/11
 *Create Yu
 *description:
 * 1. dip 2 px
 * 2. px 2 dip
 */
object ViewUtil {
    /**
     * 描述：dip转换为px
     */
    fun dip2px(dipValue: Float): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 描述：px转换为dip
     */
    fun px2dip(pxValue: Float): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        return getSizeByReflection(context, "status_bar_height")
    }

    fun getSizeByReflection(context: Context, field: String?): Int {
        var size = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField(field!!)[`object`].toString().toInt()
            size = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

}