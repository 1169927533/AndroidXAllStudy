package com.example.a11699.androidxallstudy.soul.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.a11699.androidxallstudy.soul.view.PlanetView
import com.example.a11699.androidxallstudy.soul.view.SizeUtils
import com.example.a11699.androidxallstudy.soul.view.SoulView

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
class SoulAdapter : BaseAdapter() {
    override fun getCount(): Int {
        return 50
    }

    override fun getView(context: Context, position: Int): View {
        return SoulView(context)
    }
}