package com.example.a11699.lib_im.chatprovider

import androidx.recyclerview.widget.RecyclerView
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
    var recycleview: RecyclerView? = null
    override fun getDataSource(): List<MessageInfo>? {
        mAdapter?.let {
            return it.data
        }
        return null
    }

    override fun addMessageList(messages: List<MessageInfo>, front: Boolean): Boolean {
        return true
    }

    override fun deleteMessageList(messages: List<MessageInfo>): Boolean {
        return true

    }

    override fun updateMessageList(messages: List<MessageInfo>) {
        mAdapter?.let {
            it.addData(messages)
            recycleview!!.scrollToPosition(it.itemCount - 1)
        }
    }


    override fun setAdapter(adapter: BaseQuickAdapter<MessageInfo, BaseViewHolder>, recyclevieww: RecyclerView?) {
        mAdapter = adapter
        this.recycleview = recyclevieww
    }


}