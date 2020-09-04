package com.example.a11699.comp_im.fragment

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.Util
import com.example.a11699.comp_im.adapter.ChatAdapter
import com.example.a11699.comp_im.bean.BaseChatBean
import com.example.a11699.comp_im.weight.BottomInputView
import com.example.a11699.comp_im.weight.InputViewInterface
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.messageutil.ChatManagerKit
import com.example.a11699.lib_im.messageutil.IUIKitCallBack
import com.example.a11699.module_smartrecycleview.base.fragme.BaseRecycleViewFragment
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.imsdk.v2.V2TIMMessage

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ContentFragment(private val bottomInputView: BottomInputView) : BaseRecycleViewFragment<BaseChatBean>() {
    var chatManager: ChatManagerKit? = null

    override val baseAdapter: BaseQuickAdapter<BaseChatBean, BaseViewHolder> by lazy {
        chatManager = ChatManagerKit()
        ChatAdapter()
    }
    override val onFetchListener: (page: Int) -> Unit = {
        if (baseAdapter.data.size != 0) {
            smartRefreshUtil.onFetchFinish(null, true)
            finishGetDataSuccess()
        }
    }
    override val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun initViewData() {
        super.initViewData()
        baseAdapter.setEnableLoadMore(false)
        bottomInputView.apply {
            inputViewInterface = object : InputViewInterface {
                override fun clickSendMessage(msg: MessageInfo) {
                    if (msg.msgType == MessageInfo.MSG_TYPE_TEXT) {
                        var textElem = msg.timMessage.textElem
                        var baseChatBean = BaseChatBean(textElem.text)
                        baseAdapter.addData(baseChatBean)
                        realSendMessage(msg)
                    }
                }
            }
        }
    }

    override fun observeLiveData() {

    }


    private fun realSendMessage(msg: MessageInfo) {
        var chatInfo = ChatInfo()
        chatInfo.type = V2TIMConversation.V2TIM_C2C
        chatInfo.id = "yu2"
        chatInfo.chatName = "sa"

        chatManager!!.sendMessage(msg, chatInfo, false, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                var message = data as V2TIMMessage
                Util.printLog(message.textElem.text)
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
            }
        })
    }
}