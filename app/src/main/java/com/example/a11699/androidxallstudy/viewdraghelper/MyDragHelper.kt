package com.example.a11699.androidxallstudy.viewdraghelper

import android.view.View
import androidx.customview.widget.ViewDragHelper

/**
 *Create time 2020/7/11
 *Create Yu
 *description:
 */
class MyDragHelper : ViewDragHelper.Callback() {

    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
        //注意这里一定要返回true，否则后续的拖拽回调是不会生效的
        return true
    }
    /**
     * 当状态改变的时候回调
     * STATE_IDLE 闲置状态
     * STATE_DRAGGING 正在拖动
     * STATE_SETTLING 放置到某个位置
     */
    override fun onViewDragStateChanged(state: Int) {
        super.onViewDragStateChanged(state)
    }
    /**
     * 竖直拖拽的时候回调的方法
     * 参数1：拖拽的View
     * 参数2：距离左边的距离
     * 参数3：变化量
     */
    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        return left
    }

    /**
     * 水平拖拽的时候回调的方法
     * 参数1：拖拽的View
     * 参数2：距离左边的距离
     * 参数3：变化量
     */
    override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
        return top
    }



}