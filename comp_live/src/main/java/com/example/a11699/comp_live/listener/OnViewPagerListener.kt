package com.example.a11699.comp_live.listener

import android.view.View
import android.view.ViewGroup

/**
 *Create time 2020/7/30
 *Create Yu
 *description:
 */
interface OnViewPagerListener {
    fun onInitComplete(view: ViewGroup) //第一次进入直播间的初始化
    fun onPageRelease(view: ViewGroup)//释放
    fun onPageSelected(position: Int, isBottom:Boolean,view: ViewGroup)//选中
}