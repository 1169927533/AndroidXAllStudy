package com.example.a11699.comp_chat.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_chat.R
import com.example.a11699.comp_chat.bean.ChatBean

import java.util.ArrayList

/**
 *Create time 2020/6/22
 *Create Yu
 *description:单机版聊天
 */
class SendMessageAdapter(data: MutableList<ChatBean>, context: Context) : BaseMultiItemQuickAdapter<ChatBean, BaseViewHolder>(data) {
    var mConText: Context = context

    companion object {
        const val TYPE_TOP = 0
        const val TYPE_LEFT = 1
        const val TYPE_RIGHT = 2
    }

    init {
        addItemType(TYPE_TOP, R.layout.message_top)
        addItemType(TYPE_LEFT, R.layout.message_left)
        addItemType(TYPE_RIGHT, R.layout.message_right)
    }

    override fun convert(helper: BaseViewHolder, item: ChatBean) {
        helper?.let { helper ->
            when (helper.itemViewType) {
                TYPE_TOP -> {
                    helper.getView<TextView>(R.id.tv_sendtime).text = item?.receivetime
                }
                TYPE_LEFT -> {
                    helper.getView<TextView>(R.id.tv_leftmessage).text = (item?.chatcontent)
                  /*  ImageLoader.with(mConText)
                            .url(item?.sendimg)
                            .placeHolder(R.drawable.chat_defult_crop)
                            .error(R.drawable.chat_defult_crop)
                            .into(helper.getView<TextView>(R.id.img_anchor))*/
                }
                TYPE_RIGHT -> {

                    helper.getView<TextView>(R.id.tv_rightmessage).setText(item?.chatcontent)
                    /*ImageLoader.with(mConText)
                            .url(item?.sendimg)
                            .placeHolder(R.drawable.chat_defult_crop)
                            .error(R.drawable.chat_defult_crop)
                            .into(helper.getView<TextView>(R.id.img_anchor))*/
                }
            }


        }

    }



}