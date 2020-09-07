package com.example.a11699.lib_im.messageutil

import android.util.Log
import com.example.a11699.comp_base.Util
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.chatprovider.ChatProvider
import com.example.a11699.lib_im.util.MessageInfoUtil
import com.tencent.imsdk.v2.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *Create time 2020/8/30
 *Create Yu
 *description:消息管理类
 */
class ChatManagerKit : V2TIMAdvancedMsgListener() {
    var mCurrentProvider: ChatProvider? = null

    companion object {
        var onePageCount = 20 //拉取历史消息时 一页的数据量
    }


    init {
        V2TIMManager.getMessageManager().addAdvancedMsgListener(this)
    }

    //发送消息
    fun sendMessage(message: MessageInfo, chatInfo: ChatInfo, retry: Boolean, callBack: IUIKitCallBack) {
        var isGroup = false
        var userID = ""
        var groupID = ""

        if (message.timMessage.elemType == V2TIMConversation.V2TIM_GROUP) {
            groupID = chatInfo.id
            isGroup = true
        } else {
            userID = chatInfo.id
            isGroup = false
        }
        val v2TIMMessage = message.timMessage

        var msgID = V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, if (isGroup) null else userID, if (isGroup) groupID else null,
                V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, object : V2TIMSendCallback<V2TIMMessage?> {
            override fun onProgress(progress: Int) {}
            override fun onError(code: Int, desc: String) {
                Util.printLog("sendMessage fail:$code=$desc")
                callBack?.onError("", code, desc)
            }

            override fun onSuccess(p0: V2TIMMessage?) {
                callBack?.onSuccess(p0)
            }
        })

        message.id = msgID
        if (message.msgType < MessageInfo.MSG_TYPE_TIPS) {
            message.status = MessageInfo.MSG_STATUS_SENDING
        }

        if (retry) {

        } else {

        }


    }


    /**
     * 获取当前会话的历史消息 分：c2c 和 群组
     * chatInfo : 里面包含当前要聊天的基础信息
     * lastMsg :  如果为null 则表示拉取最新聊天的历史消息
     */
    fun getHistoryMessage(chatInfo: ChatInfo, lastMsg: V2TIMMessage?, callBack: IUIKitCallBack, last: ((lastMsg: V2TIMMessage?) -> Unit)) {
        when (chatInfo.messageType) {
            V2TIMConversation.V2TIM_C2C -> {
                V2TIMManager.getMessageManager().getC2CHistoryMessageList(chatInfo.id.toString(), onePageCount, lastMsg, object : V2TIMValueCallback<List<V2TIMMessage>> {
                    override fun onSuccess(p0: List<V2TIMMessage>?) {
                        Log.i("Zkc", "onSuccess: " + p0!!.size)

                        if (p0 != null) {
                            if (p0.isNotEmpty()) {
                                last.invoke(p0[p0.size - 1])
                            } else {
                                last.invoke(null)
                            }
                            dealWithHistoryMessage(p0, chatInfo, callBack)
                        }
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Util.printLog("拉取c2c消息： 姓名: ${chatInfo.chatName + "  id: " + chatInfo.id} 的历史消息失败了！")
                    }
                })
            }
            V2TIMConversation.V2TIM_GROUP -> {
                V2TIMManager.getMessageManager().getGroupHistoryMessageList(chatInfo.id.toString(), onePageCount, lastMsg, object : V2TIMValueCallback<List<V2TIMMessage>> {
                    override fun onSuccess(p0: List<V2TIMMessage>?) {
                        if (p0 != null) {
                            dealWithHistoryMessage(p0, chatInfo, callBack)
                            if (p0.isNotEmpty()) {
                                last.invoke(p0[p0.size - 1])
                            } else {
                                last.invoke(null)
                            }
                        }
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Util.printLog("拉取群组消息： 群组名: ${chatInfo.chatName + "  id: " + chatInfo.id} 的历史消息失败了！")
                    }
                })
            }
        }
    }

    fun dealWithHistoryMessage(v2TIMMessage: List<V2TIMMessage>, chatInfo: ChatInfo, callBack: IUIKitCallBack) {
        var isGroupMessage = false
        //这一步是为了将数据置为已读
        when (chatInfo.messageType) {
            V2TIMConversation.V2TIM_C2C -> {
                isGroupMessage = false
                V2TIMManager.getMessageManager().markC2CMessageAsRead(chatInfo.id, object : V2TIMCallback {
                    override fun onSuccess() {
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
            }

            V2TIMConversation.V2TIM_GROUP -> {
                isGroupMessage = true
                V2TIMManager.getMessageManager().markGroupMessageAsRead(chatInfo.id, object : V2TIMCallback {
                    override fun onSuccess() {

                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
            }
        }

        Collections.reverse(v2TIMMessage)
        var msgInfors = MessageInfoUtil.TIMMessages2MessageInfos(v2TIMMessage, isGroupMessage)
        callBack.onSuccess(msgInfors)
    }


    //收到消息撤回的通知
    override fun onRecvMessageRevoked(msgID: String?) {
        super.onRecvMessageRevoked(msgID)
    }

    //收到新消息
    override fun onRecvNewMessage(msg: V2TIMMessage?) {
        super.onRecvNewMessage(msg)
        val info = MessageInfo()
        info.extra = msg!!.textElem.text
        info.isSelf = false
        info.timMessage = msg
        info.fromUser = V2TIMManager.getInstance().loginUser
        info.msgType = MessageInfo.MSG_TYPE_TEXT
        var list = ArrayList<MessageInfo>()
        list.add(info)

        mCurrentProvider!!.updateMessageList(list)
    }

    //收到C2C消息已读回执
    override fun onRecvC2CReadReceipt(receiptList: MutableList<V2TIMMessageReceipt>?) {
        super.onRecvC2CReadReceipt(receiptList)
    }

}