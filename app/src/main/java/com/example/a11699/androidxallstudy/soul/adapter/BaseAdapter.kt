package com.example.a11699.androidxallstudy.soul.adapter

import android.content.Context
import android.view.View
import com.example.a11699.androidxallstudy.soul.bean.PointModel

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
abstract class BaseAdapter {
    public abstract fun getCount(): Int  //获取总个数
    public abstract fun getView(context: Context, position: Int): View //获取指定view
}