package com.example.a11699.comp_im.fragment

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.Util
import com.example.a11699.comp_im.adapter.ChatAdapter
import com.example.a11699.comp_im.weight.BottomInputView
import com.example.a11699.comp_im.weight.InputViewInterface
import com.example.a11699.lib_im.Constants
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.ConversationInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.chatprovider.ChatProvider
import com.example.a11699.lib_im.messageutil.ChatManagerKit
import com.example.a11699.lib_im.messageutil.ConversationManager
import com.example.a11699.lib_im.messageutil.IUIKitCallBack
import com.example.a11699.module_smartrecycleview.base.fragme.BaseRecycleViewFragment
import com.tencent.imsdk.v2.V2TIMMessage

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ContentFragment(private val bottomInputView: BottomInputView) : BaseRecycleViewFragment<MessageInfo>() {
    var chatManager: ChatManagerKit? = null
    private var chatInfo: ChatInfo? = null
    private var lastMessage: V2TIMMessage? = null

    override val baseAdapter: BaseQuickAdapter<MessageInfo, BaseViewHolder> by lazy {
        chatManager = ChatManagerKit()
        ChatAdapter()
    }


    override val onFetchListener: (page: Int) -> Unit = { page ->
        chatManager!!.getHistoryMessage(chatInfo!!, lastMessage, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                var messageList = data as ArrayList<MessageInfo>
                if (messageList.size > 0 || baseAdapter.itemCount > 0) {
                    finishGetDataSuccess()
                } else {
                    finishGetDataError()
                }
                smartRefreshUtil.onFetchFinish(messageList, true)
                baseAdapter.setEnableLoadMore(false)
                if (page == 0) {//防止每次上拉加载更多都导致recycleView滚动到底部
                    recycleview.scrollToPosition(baseAdapter.itemCount - 1)
                }
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
            }
        }) { v2LastMessage ->
            lastMessage = v2LastMessage
            if (lastMessage == null) {
                smartRefreshUtil.setSmartRefreshLayoutCanRefresh(false)
            }
        }


    }
    override val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun initViewData() {
        super.initViewData()
        smartRefreshUtil.dropDownFunction = false  //下拉功能换成加载更多

        chatManager!!.mCurrentProvider = ChatProvider().apply {
            setAdapter(baseAdapter, this@ContentFragment.recycleview)
        }

        var bundle = arguments as Bundle
        chatInfo = bundle!!.getSerializable(Constants.CHAT_INFO) as ChatInfo
        baseAdapter.setEnableLoadMore(false)

        bottomInputView.apply {
            inputViewInterface = object : InputViewInterface {
                override fun clickSendMessage(msg: MessageInfo) {
                    msg.isSelf = true
                    msg.isRead = true
                    if (msg.msgType == MessageInfo.MSG_TYPE_TEXT) {
                        var textElem = msg.timMessage.textElem
                        baseAdapter.addData(msg)
                        realSendMessage(msg)
                    }
                }
            }
        }


    }

    override fun observeLiveData() {

    }

    private fun realSendMessage(msg: MessageInfo) {
        chatInfo?.let {
            chatManager!!.sendMessage(msg, it, false, object : IUIKitCallBack {
                override fun onSuccess(data: Any?) {
                    var message = data as V2TIMMessage
                    Util.printLog(message.textElem.text)
                }

                override fun onError(module: String?, errCode: Int, errMsg: String?) {
                }
            })
        }
    }


}