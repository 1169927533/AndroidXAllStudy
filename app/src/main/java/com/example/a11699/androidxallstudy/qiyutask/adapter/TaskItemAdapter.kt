package com.example.a11699.androidxallstudy.qiyutask.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.androidxallstudy.qiyutask.bean.DailyTask
import com.example.a11699.comp_chat.R
import com.example.a11699.comp_chat.bean.ChatBean

/**
 * @Author Yu
 * @Date 2020/12/3 14:24
 * @Description TODO
 */
class TaskItemAdapter : BaseQuickAdapter<DailyTask.DailyTaskBean, BaseViewHolder>(R.layout.item_people, ArrayList<DailyTask.DailyTaskBean>()) {
    override fun convert(p0: BaseViewHolder, p1: DailyTask.DailyTaskBean?) {

    }
}