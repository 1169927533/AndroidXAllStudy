package com.example.a11699.androidxallstudy.soul.bean

import android.graphics.Color
import android.view.View

/**
 *Create time 2020/7/28
 *Create Yu
 *description:球上的一个点
 */
class PointModel(name: String) {
    var name = name
    var locX_3 = 0f
    var locY_3 = 0f
    var locZ_3 = 0f
    var color = Color.BLACK
    var locX_2 = 0f
    var locY_2 = 0f
    var alpha = 0f
    var scale = 0f
    lateinit var view: View

}