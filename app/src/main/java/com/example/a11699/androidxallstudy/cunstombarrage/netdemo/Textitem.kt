package com.example.a11699.androidxallstudy.cunstombarrage.netdemo

/**
 *Create time 2020/6/20
 *Create Yu
 *description:
 */
class Textitem(content: String?, fx: Float, fy: Float, perstep: Float, textcolor: Int) {
    val content: String? = content
    var fx = fx
    var fy = fy
    var perstep = perstep
    var textcolor = textcolor

    fun setPersted() {
        fx -= perstep
    }

}