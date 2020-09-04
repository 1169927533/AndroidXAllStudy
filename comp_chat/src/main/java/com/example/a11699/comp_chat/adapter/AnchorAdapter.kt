package com.example.a11699.comp_chat.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_chat.R
import com.example.a11699.comp_chat.bean.ChatBean
import kotlinx.android.synthetic.main.item_people.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 *Create time 2020/7/1
 *Create Yu
 *description:
 */
class AnchorAdapter(context: Context) : BaseQuickAdapter<ChatBean, BaseViewHolder>(R.layout.item_people, ArrayList<ChatBean>()) {
    var context = context
    override fun convert(helper: BaseViewHolder, item: ChatBean) {
        helper.itemView.tv_content.text = item.chatcontent
        helper.itemView.tv_anchor.text = item.receivename
       /* ImageLoader.with(context)
                .url(item?.receiveimg)
                .placeHolder(R.drawable.chat_defult_crop)
                .error(R.drawable.chat_defult_crop)
                .into(helper.getView<ImageView>(R.id.iimg_anchor))*/
        helper.itemView.tv_time.text = item.sendtime
        var time = item.sendtime
        if (time.substring(0, 10) == getNowTime()) {
            helper.itemView.tv_time.text = time.substring(11, 16)
        } else {
            helper.itemView.tv_time.text = time
        }
        helper.addOnClickListener(R.id.constraintLayout)

    }

    fun getNowTime(): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val dateString: String = formatter.format(currentTime)
        return dateString
    }
}
