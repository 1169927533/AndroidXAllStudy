package com.example.a11699.comp_im.weight

import android.view.View
import android.widget.EditText
import com.example.a11699.lib_im.bean.MessageInfo


/**
 *Create time 2020/8/30
 *Create Yu
 *description:消息发送界面的事件回调
 */
interface InputViewInterface {
    fun clickSendMessage(msg: MessageInfo, messageInputView: View)
    fun clickShowMore(showMore: Int, editText: EditText)//0没点过 1展示更多 2不展示更多
}