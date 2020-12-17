package com.example.a11699.androidxallstudy.qiyutask.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.adapter.BaseTabAdapter
import com.uppack.lksmall.baseyu.weight.util.ViewUtil

/**
 * @Author Yu
 * @Date 2020/12/3 14:01
 * @Description TODO
 */
class TaskTabAdapter(val list: List<String>, val viewpager: ViewPager) : BaseTabAdapter(list, viewpager) {
    private var mContext: Context? = null

    override fun getView(context: Context, position: Int): View {
        if (mContext == null) {
            mContext = context
        }
        var tv = TextView(mContext)

        var layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewUtil.dip2px(22f))
        tv.setPadding(
                ViewUtil.dip2px(20f),
                ViewUtil.dip2px(2f),
                ViewUtil.dip2px(20f),
                ViewUtil.dip2px(2f)
        )
        tv.gravity = Gravity.CENTER
        layoutParams.weight = 1f
        layoutParams.gravity = Gravity.CENTER
        tv.layoutParams = layoutParams
        tv.text = list[position]
        tv.setTextColor(context.resources.getColor(R.color.colorFFFFFF))
        tv.textSize = 16f
        tv.setOnClickListener {
            clickItem(position)
        }
        targetListView.add(tv)
        return tv
    }

    override fun onPageSelected(position: Int) {
        for ((index, value) in targetListView.withIndex()) {
            if (value is TextView) {
                if (index == position) {
                    value.textSize = 16f
                    value.setTextColor(mContext!!.resources.getColor(R.color.colorFFFFFF))
                } else {
                    value.textSize = 14f
                    value.setTextColor(mContext!!.resources.getColor(R.color.color80FFFFFF))
                }
            }
        }
    }
}