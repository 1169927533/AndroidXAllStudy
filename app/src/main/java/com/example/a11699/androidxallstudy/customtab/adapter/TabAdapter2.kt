package com.example.a11699.androidxallstudy.customtab.adapter

import android.content.Context
import android.view.TextureView
import android.view.View
import android.widget.TextView

/**
 *Create time 2020/8/3
 *Create Yu
 *description:
 */
class TabAdapter2(list: List<String>) : BaseAdapter {
    var mList: List<String> = list
    var colorTvView: ArrayList<TextView> = ArrayList<TextView>()
    override fun getCount(): Int {
        return mList.size
    }

    override fun getView(context: Context, position: Int): View {
        var tv = TextView(context)
        tv.text = mList[position]
        colorTvView.add(tv)
        return tv
    }

    override fun getAllTargetView(): List<View> {
        return colorTvView
    }

    override fun changItemState(position: Int) {

    }
}