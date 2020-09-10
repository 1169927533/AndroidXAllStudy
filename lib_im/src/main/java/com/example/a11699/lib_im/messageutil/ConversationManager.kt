package com.example.a11699.lib_im.messageutil

import android.text.TextUtils
import android.util.Log
import com.example.a11699.comp_base.Util
import com.example.a11699.lib_im.R
import com.example.a11699.lib_im.bean.ConversationInfo
import com.example.a11699.lib_im.util.MessageInfoUtil
import com.tencent.imsdk.v2.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *Create time 2020/9/2
 *Create Yu
 *description:消息列表管理类：
 * 1. 获取会话列表
 * 2. 接收列表数据更新
 */
class ConversationManager {
    var mUnreadTotal: Int = 0//未读消息总数
    private val mTopLinkedList = LinkedList<ConversationInfo>()//被选中置顶的消息列表

    private var conversationManagerListListener: ArrayList<RefreshConversationListener> = ArrayList() //所有注册接收消息更新回调
    private var updateUnReadMessageNumListListener: ArrayList<UpdateUnReadMessageNumListener> = ArrayList() //所有注册未读消息数量的监听器

    companion object {
        private var conversationManager: ConversationManager = ConversationManager()
        fun getInstance(): ConversationManager {
            return conversationManager
        }
    }


    interface RefreshConversationListener {
        //监听新消息
        fun callBackRefreshListener(conversationInfo: ArrayList<ConversationInfo>, dataSource: (list: ArrayList<ConversationInfo>) -> Unit)

    }

    interface UpdateUnReadMessageNumListener {
        //监听会话未读计数
        fun updateUnReadMessageNum(count: Int)
    }


    /**
     * 获取会话列表
     */
    fun getConversationList(startNum: Long, iuiKitCallBack: IUIKitCallBack, getConversationList: ((startNum: Long) -> Unit)) {
        mUnreadTotal = 0
        V2TIMManager.getConversationManager().getConversationList(startNum, 100, object : V2TIMValueCallback<V2TIMConversationResult> {
            override fun onSuccess(p0: V2TIMConversationResult?) {
                var infos: ArrayList<ConversationInfo> = ArrayList()
                var v2TIMConversationList = p0!!.conversationList
                for (value in v2TIMConversationList) {
                    var conversationInfo = tIMConversation2ConversationInfo(value)
                    if (conversationInfo != null) {
                        mUnreadTotal += conversationInfo.unRead
                        conversationInfo.type = ConversationInfo.TYPE_COMMON
                        infos.add(conversationInfo)
                    }
                }
                var dataList = sortConversations(infos)//对数据进行排序 将标记置顶的数据抽离出来放到顶部
                updateUnreadTotal(mUnreadTotal)
                iuiKitCallBack.onSuccess(dataList)
                getConversationList.invoke(p0.nextSeq)
            }

            override fun onError(p0: Int, p1: String?) {
                Util.printLog("获取会话列表失败了")
                iuiKitCallBack.onError("", 0, "获取列表数据失败了")
            }
        })

    }


    /**
     * 更新数据 收到新的消息
     */
    fun onRefreshConversationList(v2TIMConversationList: MutableList<V2TIMConversation>?) {
        var infos = ArrayList<ConversationInfo>()
        for (i in v2TIMConversationList!!.indices) {
            val v2TIMConversation: V2TIMConversation = v2TIMConversationList[i]
            val conversationInfo: ConversationInfo? = tIMConversation2ConversationInfo(v2TIMConversation)
            if (conversationInfo != null) {
                infos.add(conversationInfo)
            }
        }
        if (infos.size == 0) {
            return
        }
        for (value in conversationManagerListListener) {
            value.callBackRefreshListener(infos) { dataSource ->
                var newDataList: ArrayList<ConversationInfo> = ArrayList()
                newDataList.addAll(dataSource)


                val exists: ArrayList<Any> = ArrayList()
                for (j in infos.indices) {
                    val update: ConversationInfo = infos[j]
                    var exist = false
                    for (i in newDataList.indices) {
                        val cacheInfo: ConversationInfo = newDataList[i]
                        //单个会话刷新时找到老的会话数据，替换，这里需要增加群组类型的判断，防止好友id与群组id相同
                        if (cacheInfo.id == (update.id) && cacheInfo.isGroup == update.isGroup) {
                            newDataList.removeAt(i)
                            newDataList.add(i, update)
                            exists.add(update)
                            //需同步更新未读计数
                            mUnreadTotal = mUnreadTotal - cacheInfo.unRead + update.unRead
                            exist = true
                            break
                        }
                    }
                    if (!exist) {
                        mUnreadTotal += update.unRead
                    }
                }
                updateUnreadTotal(mUnreadTotal)
                infos.removeAll(exists)
                if (infos.size > 0) {
                    newDataList.addAll(infos)
                }
                dataSource.clear()
                dataSource.addAll(newDataList)
                sortConversations(dataSource)
            }
        }

    }


    /**
     * 将获取的TIMConversation转换为ConversationInfo
     */
    fun tIMConversation2ConversationInfo(conversation: V2TIMConversation): ConversationInfo? {

        var message: V2TIMMessage? = conversation.lastMessage ?: return null

        var conversationInfo = ConversationInfo()

        var type = conversation.type //获取会话类型
        if (type != V2TIMConversation.V2TIM_C2C && type != V2TIMConversation.V2TIM_GROUP) {
            return null
        }
        var isGroup = type == V2TIMConversation.V2TIM_GROUP

        conversationInfo.lastMessageTime = message!!.timestamp
        var listMesssage = MessageInfoUtil.TIMMessage2MessageInfo(message)
        if (listMesssage != null && listMesssage.size > 0) {
            conversationInfo.lastMessage = listMesssage[listMesssage.size - 1]
        }
        conversationInfo.title = conversation.showName
        if (isGroup) {
            // TODO: 2020/9/2 处理群组消息
        } else {
            var faceList = ArrayList<Any>()
            if (TextUtils.isEmpty(conversation.faceUrl)) {
                faceList.add(R.drawable.default_head)
            } else {
                faceList.add(conversation.faceUrl)
            }
            conversationInfo.iconUrlList.addAll(faceList)
        }
        if (isGroup) {
            conversationInfo.id = conversation.groupID
        } else {
            conversationInfo.id = conversation.userID
        }
        conversationInfo.conversationId = conversation.conversationID
        conversationInfo.isGroup = isGroup
        conversationInfo.unRead = conversation.unreadCount
        conversationInfo.type = conversation.type
        return conversationInfo
    }


    /**
     * 对数据进行一个排序 新消息应该在上面
     */
    fun sortConversations(sources: List<ConversationInfo>): ArrayList<ConversationInfo> {
        val conversationInfos = ArrayList<ConversationInfo>()
        val normalConversations: MutableList<ConversationInfo> = ArrayList()
        val topConversations: MutableList<ConversationInfo> = ArrayList()
        for (element in sources) {
            if (isTop(element.id)) {
                element.isTop = true
                topConversations.add(element)
            } else {
                normalConversations.add(element)
            }
        }
        mTopLinkedList.clear()
        mTopLinkedList.addAll(topConversations)
        topConversations.sort() // 置顶会话列表页也需要按照最后一条时间排序，由新到旧，如果旧会话收到新消息，会排序到前面
        conversationInfos.addAll(topConversations)
        normalConversations.sort() // 正常会话也是按照最后一条消息时间排序，由新到旧
        conversationInfos.addAll(normalConversations)
        return conversationInfos
    }

    private fun isTop(id: String): Boolean {
        if (mTopLinkedList == null || mTopLinkedList.size == 0) {
            return false
        }
        for (info in mTopLinkedList) {
            if (TextUtils.equals(info.id, id)) {
                return true
            }
        }
        return false
    }

    /**
     * 更新会话未读计数
     *
     * @param unreadTotal
     */
    private fun updateUnreadTotal(unreadTotal: Int) {

        for (update in updateUnReadMessageNumListListener) {
            update.updateUnReadMessageNum(unreadTotal)
        }
    }


    //添加消息列表监听
    fun addRefreshConversationListener(refresh: RefreshConversationListener) {
        if(!conversationManagerListListener.contains(refresh)){
            conversationManagerListListener.add(refresh)
        }
    }

    //移除消息列表监听
    fun deleteRefreshConversationListener(refresh: RefreshConversationListener) {
        conversationManagerListListener.add(refresh)
    }

    //移除消息更新数监听
    fun deleteRefreshNoReadNumListener(updateUnReadMessageNumListener: UpdateUnReadMessageNumListener) {
        updateUnReadMessageNumListListener.remove(updateUnReadMessageNumListener)
    }

    //添加消息数据更新监听
    fun addRefreshNoReadNumListener(updateUnReadMessageNumListener: UpdateUnReadMessageNumListener) {
        if(!updateUnReadMessageNumListListener.contains(updateUnReadMessageNumListener)){
            updateUnReadMessageNumListListener.add(updateUnReadMessageNumListener)
        }
    }
}