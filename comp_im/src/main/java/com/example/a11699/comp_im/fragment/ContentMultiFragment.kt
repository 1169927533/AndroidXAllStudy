package com.example.a11699.comp_im.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.Util
import com.example.a11699.comp_im.adapter.ChatMultiAdapter
import com.example.a11699.comp_im.weight.BottomInputView
import com.example.a11699.comp_im.weight.InputViewInterface
import com.example.a11699.lib_im.Constants
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.chatprovider.ChatProvider
import com.example.a11699.lib_im.messageutil.ChatManagerKit
import com.example.a11699.lib_im.messageutil.IUIKitCallBack
import com.example.a11699.module_smartrecycleview.base.fragme.BaseMultiRecycleViewFragment
import com.tencent.imsdk.v2.V2TIMMessage

/**
 *Create time 2020/8/27
 *Create Yu
 *description:支持展示不同item样式
 */
class ContentMultiFragment(private val bottomInputView: BottomInputView) : BaseMultiRecycleViewFragment<MessageInfo>() {
    var listMessage: ArrayList<MessageInfo> = ArrayList()
    var chatManager: ChatManagerKit? = null
    private var chatInfo: ChatInfo? = null
    private var lastMessage: V2TIMMessage? = null

    override val baseAdapter: BaseMultiItemQuickAdapter<MessageInfo, BaseViewHolder> by lazy {
        chatManager = ChatManagerKit()
        ChatMultiAdapter(listMessage)
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
        addInputViewStateListener() //添加软键盘弹起监听
        smartRefreshUtil.dropDownFunction = false  //下拉功能换成加载更多

        chatManager!!.mCurrentProvider = ChatProvider().apply {
            setAdapter(baseAdapter, this@ContentMultiFragment.recycleview)
        }

        var bundle = arguments as Bundle
        chatInfo = bundle!!.getSerializable(Constants.CHAT_INFO) as ChatInfo
        baseAdapter.setEnableLoadMore(false)//禁掉他的上拉加载

      /*  bottomInputView.apply {
            inputViewInterface = object : InputViewInterface {
                override fun clickSendMessage(msg: MessageInfo, messageInputView: View) {
                    msg.isSelf = true
                    msg.isRead = true
                    if (msg.msgType == MessageInfo.MSG_TYPE_TEXT) {
                        var textElem = msg.timMessage.textElem
                        baseAdapter.addData(msg)
                        realSendMessage(msg)
                        (messageInputView as EditText).text.clear()
                        recycleview!!.scrollToPosition(baseAdapter.itemCount - 1)
                    }
                }

                override fun clickShowMore(showMore: Boolean) {

                }
            }
        }*/


    }

    override fun observeLiveData() {

    }

    fun sendMessage(msg: MessageInfo, messageInputView: View) {
        msg.isSelf = true
        msg.isRead = true
        if (msg.msgType == MessageInfo.MSG_TYPE_TEXT) {
            var textElem = msg.timMessage.textElem
            baseAdapter.addData(msg)
            realSendMessage(msg)
            (messageInputView as EditText).text.clear()
        }
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


    private fun addInputViewStateListener() {
        var normalHeight = 0
        var hasBounce = false
        activity?.let { ac ->
            var mRoot = ac.window.decorView
            mRoot.viewTreeObserver.addOnGlobalLayoutListener {
                var rect = Rect()
                mRoot.getWindowVisibleDisplayFrame(rect)
                var rootInvisibleHeight = mRoot.rootView.height - rect.height()

                if (normalHeight == 0) {
                    normalHeight = rootInvisibleHeight
                }

                if (rootInvisibleHeight > normalHeight && !hasBounce) {
                    hasBounce = true
                    recycleview!!.scrollToPosition(baseAdapter.itemCount - 1)
                }
                if (rootInvisibleHeight == normalHeight) {
                    hasBounce = false
                }
            }
        }
    }

}