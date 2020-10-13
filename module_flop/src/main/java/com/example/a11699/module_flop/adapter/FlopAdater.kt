package com.example.a11699.module_flop.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.module_flop.R
import com.example.a11699.module_flop.bean.FlopBean
import kotlinx.android.synthetic.main.item_flop.view.*

/**
 *Create time 2020/10/13
 *Create Yu
 *description:
 */
class FlopAdater(context: Context) : BaseQuickAdapter<FlopBean, BaseViewHolder>(R.layout.item_flop, ArrayList<FlopBean>()) {
    override fun convert(helper: BaseViewHolder, item: FlopBean?) {
        helper.itemView.flopview?.let { itt ->
            itt.mFontBg = item?.frontSrc!!
            itt.setOnClickListener {
                itt.startAnimal()
            }
        }
    }
}