package com.example.a11699.comp_im.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.bean.BaseChatBean
import com.example.a11699.lib_im.bean.MessageInfo
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ChatAdapter : BaseQuickAdapter<MessageInfo, BaseViewHolder>(R.layout.item_chat) {
   var recycleview:RecyclerView?=null
    override fun convert(helper: BaseViewHolder, item: MessageInfo?) {
        helper?.let {
            it.itemView.tv_item_content.text = item!!.timMessage.textElem.text
            it.itemView.img_item_head.loadCircleUrl(
                    item.timMessage.faceUrl, RequestOptions()
                    .placeholder(R.drawable.default_circle_small)
                    .error(R.drawable.default_circle_small)
            )
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recycleview = recyclerView
    }
}