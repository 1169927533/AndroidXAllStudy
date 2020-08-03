package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.view.View

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
interface BaseAdapter {
    public fun getCount(): Int  //获取总个数
    public fun getView(context: Context, position: Int): View //获取指定view
    public fun getAllTargetView(): List<View> //获取全部指定View
    public fun changItemState(position: Int) //当某个item被选中 该变状态
}