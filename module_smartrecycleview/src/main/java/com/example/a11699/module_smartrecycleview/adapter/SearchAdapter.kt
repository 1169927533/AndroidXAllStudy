package com.example.a11699.module_smartrecycleview.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.bean.DailyStudentBean
import com.example.a11699.comp_base.bean.SearchStudentBean
import com.example.a11699.module_smartrecycleview.R
import kotlinx.android.synthetic.main.item_search.view.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class SearchAdapter : BaseQuickAdapter<DailyStudentBean, BaseViewHolder>(R.layout.item_search) {
    override fun convert(helper: BaseViewHolder, item: DailyStudentBean) {
        helper.itemView?.let {
            it.tv_class.text = item.baseInfo
            it.search_stuName.text = item.sname
            if (item.sex == "0") {
                it.face.setImageResource(R.drawable.stuboy)
            } else {
                it.face.setImageResource(R.drawable.stugirl)
            }
            it.tv_telphone.text = item.phone
        }
    }
}