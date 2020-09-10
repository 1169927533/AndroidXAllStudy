package com.example.a11699.lib_im.util

import android.graphics.Rect
import android.view.View
import android.view.Window
import com.example.a11699.comp_base.Util

/**
 *Create time 2020/9/9
 *Create Yu
 *description:
 */
object ScreenUtil {
    /**
     * 获取ToolBar高度（包含状态栏高度）
     *
     * @return
     */
    fun getTitleBarHeight(view: View, mHasStatusBar: Boolean): Int {
        val rootView: View = view.rootView
        if (rootView != null) {
            val window = rootView.findViewById<View>(Window.ID_ANDROID_CONTENT)
            if (window != null) {
                return window.top + if (mHasStatusBar) getStatusBarHeight(view) else 0
            }
        }
        return if (mHasStatusBar) getStatusBarHeight(view) else 0
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public fun getStatusBarHeight(view: View): Int {
        val outRect = Rect()
        view.getWindowVisibleDisplayFrame(outRect)
        return outRect.top
    }


    /**
     * 判断当前软键盘是否显示
     */
    public fun isSoftShowing(view: View): Boolean {
        Util.printLog(" getScreenTotalHeight(): ${getScreenTotalHeight(view)}   getVisibleHeight():${getVisibleHeight(view)}")
        return getScreenTotalHeight(view) - getVisibleHeight(view) != 0
    }


    /**
     * 获取窗口可见区域高度
     */
    fun getVisibleHeight(view: View): Int {
        var outRect = Rect()
        view.getWindowVisibleDisplayFrame(outRect)
        return outRect.bottom
    }

    /**
     * 获取屏幕高度
     */
    public fun getScreenTotalHeight(view: View): Int {
        return view.resources.displayMetrics.heightPixels
    }
}

