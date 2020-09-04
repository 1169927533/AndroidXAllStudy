package com.example.a11699.lib_im.chatprovider

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.lib_im.bean.MessageInfo

/**
 *Create time 2020/9/1
 *Create Yu
 *description:
 */
class ChatProvider : IChatProvider {
    var mAdapter: BaseQuickAdapter<MessageInfo, BaseViewHolder>? = null
    var mDataSource: ArrayList<MessageInfo> = ArrayList()

    override fun getDataSource(): List<MessageInfo> {
        return mDataSource
    }

    override fun addMessageList(messages: List<MessageInfo>, front: Boolean): Boolean {
        return true
    }

    override fun deleteMessageList(messages: List<MessageInfo>): Boolean {
        return true

    }

    override fun updateMessageList(messages: List<MessageInfo>) {

    }

    override fun setAdapter(adapter: BaseQuickAdapter<MessageInfo, BaseViewHolder>) {
        mAdapter = adapter
    }


}