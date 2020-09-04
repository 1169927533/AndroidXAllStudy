package com.example.a11699.lib_im.chatprovider

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.lib_im.bean.MessageInfo

/**
 *Create time 2020/9/1
 *Create Yu
 *description:
 */
interface IChatProvider {
    fun getDataSource(): List<MessageInfo> // 获取聊天消息数据
    fun addMessageList(messages: List<MessageInfo>, front: Boolean): Boolean//front: 添加的位置 前or 尾
    fun deleteMessageList(messages: List<MessageInfo>): Boolean //删除聊天消息
    fun updateMessageList(messages: List<MessageInfo>) //更新消息

    fun setAdapter(adapter: BaseQuickAdapter<MessageInfo, BaseViewHolder>)//设置适配器
}