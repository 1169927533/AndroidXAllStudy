package com.example.a11699.comp_im.activity

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_im.adapter.ConversationListAdapter
import com.example.a11699.lib_im.bean.ConversationInfo
import com.example.a11699.lib_im.messageutil.ConversationManager
import com.example.a11699.lib_im.messageutil.IUIKitCallBack
import com.example.a11699.module_smartrecycleview.base.activity.BaseRecycleViewActivity

/**
 *Create time 2020/9/2
 *Create Yu
 *description:接通im 展示消息列表
 */
class MessageActivity : BaseRecycleViewActivity<ConversationInfo>(), ConversationManager.RefreshConversationListener {
    override val baseAdapter: BaseQuickAdapter<ConversationInfo, BaseViewHolder> by lazy {
        ConversationListAdapter(this)
    }

    override fun initViewData() {
        super.initViewData()
    }

    var startNum = 0L
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


    }

    override fun observeLiveData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ConversationManager.getInstance().deleteRefreshConversationListener(this)
    }


    override fun callBackRefreshListener(infos: ArrayList<ConversationInfo> , dataSource: (list: ArrayList<ConversationInfo>) -> Unit) {
        dataSource.invoke(baseAdapter.data as ArrayList<ConversationInfo>)
        baseAdapter.notifyDataSetChanged()
    }
}