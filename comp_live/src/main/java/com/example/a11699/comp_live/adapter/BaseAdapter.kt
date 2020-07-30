package com.example.a11699.comp_live.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_live.R
import kotlinx.android.synthetic.main.activity_main_live.*
import kotlinx.android.synthetic.main.item_tv.view.*

/**
 *Create time 2020/7/30
 *Create Yu
 *description:
 */
class BaseAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_tv) {
    override fun convert(helper: BaseViewHolder, item: Int?) {
        if (item != null) {
            helper.itemView.tv_item.setImageResource(item)
        }
    }
}