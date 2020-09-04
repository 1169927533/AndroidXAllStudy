package com.example.a11699.comp_im.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.util.GlideImageView
import com.example.a11699.comp_im.R
import com.example.a11699.lib_im.bean.ConversationInfo
import com.example.a11699.lib_im.bean.MessageInfo
import com.example.a11699.lib_im.util.DateTimeUtil
import com.example.a11699.lib_im.util.TUIKitConstants
import com.tencent.imsdk.v2.V2TIMManager
import kotlinx.android.synthetic.main.item_converlist.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 *Create time 2020/7/1
 *Create Yu
 *description:
 */
class ConversationListAdapter(context: Context) : BaseQuickAdapter<ConversationInfo, BaseViewHolder>(R.layout.item_converlist, ArrayList<ConversationInfo>()) {
    var context = context
    override fun convert(helper: BaseViewHolder, item: ConversationInfo) {
        var lastMsg = item.lastMessage
        if (lastMsg != null && lastMsg.status === MessageInfo.MSG_STATUS_REVOKE) {
            if (lastMsg.isSelf) {
                lastMsg.extra = "您撤回了一条消息"
            } else if (lastMsg.isGroup) {
                val message: String = TUIKitConstants.covert2HTMLString(
                        if (TextUtils.isEmpty(lastMsg.groupNameCard)) lastMsg.fromUser else lastMsg.groupNameCard)
                lastMsg.extra = message + "撤回了一条消息"
            } else {
                lastMsg.extra = "对方撤回了一条消息"
            }
        }
        if (item.isTop) {
            //可以对item进行特殊绘制
        }
        if (item.unRead > 0) {
            helper.itemView.tv_unread_item_num.visibility = View.VISIBLE
            helper.itemView.tv_unread_item_num.text = item.unRead.toString()
        } else {
            helper.itemView.tv_unread_item_num.visibility = View.GONE
        }
        if (lastMsg != null) {
            if (lastMsg.extra != null) {
                helper.itemView.tv_content.text = (Html.fromHtml(lastMsg.extra.toString()))
            }
            helper.itemView.tv_time.text = (DateTimeUtil.getTimeFormatText(Date(lastMsg.msgTime * 1000)))
        }
        helper.itemView.tv_anchor.text = item.title


        helper.itemView.iimg_anchor.loadCircleUrl(item.iconUrlList[0].toString(), RequestOptions().placeholder(R.drawable.default_circle_small)
                .error(R.drawable.default_circle_small))
        item.iconUrlList
        helper.addOnClickListener(R.id.constraintLayout)

    }

    fun getNowTime(): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val dateString: String = formatter.format(currentTime)
        return dateString
    }
}
