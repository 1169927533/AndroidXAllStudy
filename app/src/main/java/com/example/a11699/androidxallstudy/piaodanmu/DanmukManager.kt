package com.example.a11699.androidxallstudy.piaodanmu

import android.util.Log


class DanmukManager<T> {


    private val trackViews: ArrayList<IDanmukView<T>> = ArrayList<IDanmukView<T>>()

    fun addView(danmukView: IDanmukView<T>) {
        trackViews.add(danmukView)
    }


    fun reset() {
        trackViews.clear()
    }

    private var next = 0

    fun onDanmukArrived(danmuke: T) {
        trackViews[next].onNewModel(danmuke)
        next++
        if (next > trackViews.size - 1) {
            next = 0
        }
    }


}