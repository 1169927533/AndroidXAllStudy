package com.example.a11699.lib_im.messageutil

import com.example.a11699.comp_base.Util
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.chatprovider.ChatProvider
import com.tencent.imsdk.v2.*

/**
 *Create time 2020/8/30
 *Create Yu
 *description:消息管理类
 */
class ChatManagerKit : V2TIMAdvancedMsgListener() {
    var mCurrentProvider: ChatProvider? = null

    init {
        V2TIMManager.getMessageManager().addAdvancedMsgListener(this)
    }

    fun sendMessage(message: MessageInfo, chatInfo: ChatInfo, retry: Boolean, callBack: IUIKitCallBack) {
        var isGroup = false
        var userID = ""
        var groupID = ""

        if (message.timMessage.elemType == V2TIMConversation.V2TIM_GROUP) {
            groupID = chatInfo.id
            isGroup = true
        } else {
            userID = chatInfo.id
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

    }

    //收到消息撤回的通知
    override fun onRecvMessageRevoked(msgID: String?) {
        super.onRecvMessageRevoked(msgID)
    }

    //收到新消息
    override fun onRecvNewMessage(msg: V2TIMMessage?) {
        super.onRecvNewMessage(msg)
    }

    //收到C2C消息已读回执
    override fun onRecvC2CReadReceipt(receiptList: MutableList<V2TIMMessageReceipt>?) {
        super.onRecvC2CReadReceipt(receiptList)
    }
}