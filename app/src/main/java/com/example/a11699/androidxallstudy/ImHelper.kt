package com.example.a11699.androidxallstudy

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.tencent.imsdk.v2.*
import java.util.*

/**
 * @Author Yu
 * @Date 2021/5/27 17:09
 * @Description im 管理类
 */
class ImHelper {
    companion object {
        var imHelper: ImHelper? = null
        fun getInstance(): ImHelper {
            synchronized(ImHelper::class) {
                if (imHelper == null) {
                    imHelper = ImHelper()
                }
                return imHelper!!
            }

        }
    }

    var initSuccess = false // 腾讯im初始化完成状态
    var hasLogin = false   //是否已经登录
    var loginInImUserID = "" //登录进来得本人用户id

    // 初始化腾讯 im
    fun initImSdk(context: Context, sdkId: Int) {
        if (!initSuccess) {
            initSuccess = V2TIMManager.getInstance().initSDK(
                context,
                sdkId,
                V2TIMSDKConfig().apply { setLogLevel(V2TIMSDKConfig.V2TIM_LOG_ERROR) },
                object : V2TIMSDKListener() {
                    override fun onConnectSuccess() {
                        super.onConnectSuccess()
                        initSuccess = true
                    }

                    override fun onConnectFailed(code: Int, error: String?) {
                        super.onConnectFailed(code, error)
                        initSuccess = false
                        hasLogin = false
                        Toast.makeText(
                            context,
                            "初始化im失败：错误码：${code} 内容：${error}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onKickedOffline() {
                        super.onKickedOffline()
                        initSuccess = true
                        hasLogin = false
                        Toast.makeText(context, "在其他设备登录", Toast.LENGTH_SHORT).show()
                    }

                    override fun onUserSigExpired() {
                        super.onUserSigExpired()
                        initSuccess = true
                        hasLogin = false
                        Toast.makeText(context, "ImSdk过期", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        if (!initSuccess) {
            Toast.makeText(context, "初始化im失败", Toast.LENGTH_SHORT).show()
        }
    }

    // 登录im
    fun loginIm(
        context: Context,
        userId: String,
        userPassword: String,
        loginCallBack: (state: Boolean) -> Unit
    ) {
        if (initSuccess) {
            if (!hasLogin) {
                V2TIMManager.getInstance().login(userId, userPassword, object : V2TIMCallback {
                    override fun onSuccess() {
                        hasLogin = true
                        loginInImUserID = userId
                        loginCallBack(true)
                    }

                    override fun onError(p0: Int, p1: String?) {
                        hasLogin = false
                        loginCallBack(false)
                        Toast.makeText(context, "登录失败。code:${p0} reason:${p1}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            } else {
                Toast.makeText(context, "不能重复登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 登出im
    fun loginOutIm(context: Context, loginOutCallBack: (state: Boolean) -> Unit) {
        if (hasLogin) {
            V2TIMManager.getInstance().logout(object : V2TIMCallback {
                override fun onSuccess() {
                    hasLogin = false
                    loginOutCallBack.invoke(true)
                }

                override fun onError(p0: Int, p1: String?) {
                    Toast.makeText(context, "登出失败：code:${p0} reson:${p1}", Toast.LENGTH_SHORT)
                        .show()
                    loginOutCallBack.invoke(false)
                }
            })
        }
    }

    // 监听im消息
    fun initReceiptMessage() {
        // 监听新得消息
        V2TIMManager.getConversationManager().setConversationListener(object : V2TIMConversationListener() {
                // 收到新的会话
                override fun onNewConversation(conversationList: MutableList<V2TIMConversation>?) {
                    super.onNewConversation(conversationList)
                    Log.i("zjc", "收到新的会话")
                    if (conversationList != null) {
                        for (value in conversationList) {
                            var unreadCount = value.unreadCount // 未读数量
                            var lastMessage = value.lastMessage
                            Log.i(
                                "zjc",
                                "${unreadCount} " + lastMessage.nickName + " " + lastMessage.timestamp
                            )
                        }
                    }
                }

                // 会话跟新
                override fun onConversationChanged(conversationList: MutableList<V2TIMConversation>?) {
                    super.onConversationChanged(conversationList)
                    Log.i("zjc", "会话跟新")
                    if (conversationList != null) {
                        for (value in conversationList) {
                            var unreadCount = value.unreadCount // 未读数量
                            Log.i(
                                    "zjc",
                                "${unreadCount}" + value.showName + " " + value.unreadCount
                            )
                        }
                    }
                }

                override fun onSyncServerFinish() {
                    super.onSyncServerFinish()
                    Log.i("zjc", "同步服务器会话完成")

                }
                override fun onSyncServerFailed() {
                    super.onSyncServerFailed()
                    Log.i("zjc", "同步服务器会话失败")
                }
            })
    }

}