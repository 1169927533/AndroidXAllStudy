package com.example.a11699.lib_im.imloginutil

import android.content.Context
import android.util.Log
import com.example.a11699.comp_base.Util
import com.example.a11699.lib_im.messageutil.ConversationManager
import com.tencent.imsdk.v2.*


/**
 *Create time 2020/8/28
 *Create Yu
 *description:初始化im工具类
 */
object ImHelper {
    var Tag: String = javaClass.name
    var mContext: Context? = null

    fun initImSdk(context: Context, sdkAppId: Int) {
        mContext = context
        var config = V2TIMSDKConfig()
        // 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO)
        V2TIMManager.getInstance().initSDK(context, sdkAppId, config, object : V2TIMSDKListener() {
            override fun onConnectFailed(code: Int, error: String?) {
                super.onConnectFailed(code, error)
                Log.i(Tag, "initSDK: 连接失败")
            }

            override fun onConnectSuccess() {
                super.onConnectSuccess()
                //已经成功连接到腾讯云服务器
                Log.i(Tag, "initSDK: 连接成功")
            }

            override fun onConnecting() {
                super.onConnecting()
                //正在连接到腾讯云服务器
                Log.i(Tag, "initSDK: 正在连接到腾讯云服务器")

            }

            override fun onKickedOffline() {
                super.onKickedOffline()
                //当前用户被踢下线
                Log.i(Tag, "initSDK: 你被踢下线了")

            }

            override fun onUserSigExpired() {
                super.onUserSigExpired()
                //登录票据已经过期
                Log.i(Tag, "initSDK: 登录票据过期了")

            }

            override fun onSelfInfoUpdated(info: V2TIMUserFullInfo?) {
                super.onSelfInfoUpdated(info)
                //当前用户的资料发生了更新
                Log.i(Tag, "initSDK: 用户资料发生了更新")

            }
        })


        /**
         * 更新会话列表
         */
        V2TIMManager.getConversationManager().setConversationListener(object : V2TIMConversationListener() {
            override fun onSyncServerStart() {
                super.onSyncServerStart()
                Util.printLog("同步服务器会话开始，SDK 会在登录成功或者断网重连后自动同步服务器会话")
            }

            override fun onSyncServerFailed() {
                super.onSyncServerFailed()
                Util.printLog("同步服务器会话失败")
            }

            override fun onSyncServerFinish() {
                super.onSyncServerFinish()
                Util.printLog("同步服务器会话完成，如果会话有变更，会通过 onNewConversation " +
                        "| onConversationChanged 回调告知客户")
            }

            override fun onNewConversation(conversationList: MutableList<V2TIMConversation>?) {
                super.onNewConversation(conversationList)

                Util.printLog("有新的会话（比如收到一个新同事发来的单聊消息、或者被拉入了一个新的群组中）" +
                        "，可以根据会话的 lastMessage -> timestamp 重新对会话列表做排序")
                ConversationManager.getInstance().onRefreshConversationList(conversationList)
            }

            override fun onConversationChanged(conversationList: MutableList<V2TIMConversation>?) {
                super.onConversationChanged(conversationList)
                Util.printLog("某些会话的关键信息发生变化（未读计数发生变化、最后一条消息被更新等等）" +
                        "，可以根据会话的 lastMessage -> timestamp 重新对会话列表做排序")
                ConversationManager.getInstance().onRefreshConversationList(conversationList)
            }
        })
    }

    fun loginIm(userNumBer: String, success: () -> Unit, error: () -> Unit) {
        // 获取userSig函数
        val userSig: String = GenerateTestUserSig.genTestUserSig(userNumBer)
        V2TIMManager.getInstance().login(userNumBer, userSig, object : V2TIMCallback {
            override fun onError(code: Int, desc: String) {
                error.invoke()
            }

            override fun onSuccess() {
                success.invoke()
            }
        })
    }

    fun getAppContext(): Context? {
        return mContext
    }
}