package com.example.a11699.comp_im.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.bean.BaseChatBean
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ChatAdapter : BaseQuickAdapter<BaseChatBean, BaseViewHolder>(R.layout.item_chat) {
    override fun convert(helper: BaseViewHolder, item: BaseChatBean?) {
        helper?.let {
            it.itemView.tv_item_content.text = item!!.content
        }
    }
}