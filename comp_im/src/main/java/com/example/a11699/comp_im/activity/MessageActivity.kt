package com.example.a11699.comp_im.activity

import android.content.Intent
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.adapter.ConversationListAdapter
import com.example.a11699.lib_im.Constants
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.ConversationInfo
import com.example.a11699.lib_im.messageutil.ConversationManager
import com.example.a11699.lib_im.messageutil.IUIKitCallBack

import com.example.a11699.module_smartrecycleview.base.activity.BaseRecycleViewActivity
import kotlinx.android.synthetic.main.activity_messagee.*

/**
 *Create time 2020/9/2
 *Create Yu
 *description:接通im 展示消息列表
 */
class MessageActivity : BaseRecycleViewActivity<ConversationInfo>(), ConversationManager.RefreshConversationListener, ConversationManager.UpdateUnReadMessageNumListener {
    var startNum = 0L //分页查询的下一页id

    override val baseAdapter: BaseQuickAdapter<ConversationInfo, BaseViewHolder> by lazy {
        ConversationListAdapter(this).apply {
            setOnItemChildClickListener { _, _, position ->
                var chatInfo = ChatInfo()
                chatInfo.chatName = data[position].title
                chatInfo.id = data[position].id // c2c为对方id group为群组id
                chatInfo.messageType = data[position].type //聊天类型

                var intent = Intent(this@MessageActivity,ImActivity::class.java)
                intent.putExtra(Constants.CHAT_INFO, chatInfo)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_messagee
    }

    override val onFetchListener: (page: Int) -> Unit = {
        if (it == 0) {
            startNum = 0L
        }

        ConversationManager.getInstance().getConversationList(startNum, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                var dataList = data as ArrayList<ConversationInfo>

                if (dataList.size == 0 && baseAdapter.data.size == 0) {
                    finishGetDataError()
                } else {
                    finishGetDataSuccess()
                }

                smartRefreshUtil.onFetchFinish(data = dataList, goneIfNoData = false)
                baseAdapter.setEnableLoadMore(false)
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
                finishGetDataError()
            }
        }) {
            startNum = it
        }

        //添加消息更新的监听
        ConversationManager.getInstance().addRefreshConversationListener(this)
        //添加未读消息数量监听
        ConversationManager.getInstance().addRefreshNoReadNumListener(this)
    }

    override fun observeLiveData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ConversationManager.getInstance().deleteRefreshConversationListener(this)
    }

    /**
     * 第一个
     */
    override fun callBackRefreshListener(infos: ArrayList<ConversationInfo>, dataSource: (list: ArrayList<ConversationInfo>) -> Unit) {
        dataSource.invoke(baseAdapter.data as ArrayList<ConversationInfo>)
        baseAdapter.notifyDataSetChanged()
    }

    override fun updateUnReadMessageNum(count: Int) {
        if (count > 0) {
            tv_unread_num.visibility = View.VISIBLE
            tv_unread_num.text = count.toString()
        } else {
            tv_unread_num.visibility = View.GONE
        }
    }
}