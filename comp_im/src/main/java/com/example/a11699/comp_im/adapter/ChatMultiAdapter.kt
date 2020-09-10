package com.example.a11699.comp_im.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_im.R
import com.example.a11699.lib_im.bean.MessageInfo
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.android.synthetic.main.message_left.view.*
import kotlinx.android.synthetic.main.message_right.view.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ChatMultiAdapter(data: ArrayList<MessageInfo>) : BaseMultiItemQuickAdapter<MessageInfo, BaseViewHolder>(data) {
    var recycleview: RecyclerView? = null

    companion object {
        const val TYPE_TOP = 0
        const val TYPE_LEFT = 1
        const val TYPE_RIGHT = 2
    }

    init {
        addItemType(TYPE_TOP, R.layout.message_top)
        addItemType(TYPE_RIGHT, R.layout.message_right)
        addItemType(TYPE_LEFT, R.layout.message_left)
    }

    override fun convert(helper: BaseViewHolder, item: MessageInfo?) {
        helper?.let { mHelper ->
            when (mHelper.itemViewType) {
                TYPE_TOP -> {

                }
                TYPE_LEFT -> {
                    helper.getView<TextView>(R.id.tv_leftmessage).text = (item!!.timMessage.textElem.text)
                    helper.itemView.img_anchor.loadCircleUrl(
                            item.timMessage.faceUrl, RequestOptions()
                            .placeholder(R.drawable.default_circle_small)
                            .error(R.drawable.default_circle_small)
                    )
                }
                TYPE_RIGHT -> {
                    helper.getView<TextView>(R.id.tv_rightmessage).text = (item!!.timMessage.textElem.text)
                    helper.itemView.img_anchor_right.loadCircleUrl(
                            item.timMessage.faceUrl, RequestOptions()
                            .placeholder(R.drawable.default_circle_small)
                            .error(R.drawable.default_circle_small)
                    )
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recycleview = recyclerView
    }
}