package com.example.a11699.androidxallstudy.recycleviewstudy

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.item_layout0.view.*

/**
 * @Author Yu
 * @Date 2020/12/20 9:14
 * @Description TODO
 */
class RecycleViewAdapter(data: ArrayList<DataBean>) : BaseMultiItemQuickAdapter<DataBean, BaseViewHolder>(data) {
    init {
        addItemType(0, R.layout.item_layout0)
        addItemType(1, R.layout.item_layout)
    }

    override fun convert(holder: BaseViewHolder, p1: DataBean) {
        if (holder.itemViewType == 1) {
            holder.itemView.te.text = p1.mName
        } else {


            var layoutParams_one =  holder.itemView.ll.layoutParams
            layoutParams_one.height = ViewUtil.dip2px(174f)
            holder.itemView.ll.layoutParams = layoutParams_one


        }
    }
}