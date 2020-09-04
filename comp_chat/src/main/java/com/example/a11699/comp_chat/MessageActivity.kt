package com.example.a11699.comp_chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a11699.comp_chat.adapter.AnchorAdapter
import com.example.a11699.comp_chat.util.ReadDbUtil

import kotlinx.android.synthetic.main.activity_message.*



/**
 *Create time 2020/7/1
 *Create Yu
 *description:单机版本
 */

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        smartrefresh.setEnableLoadMore(false)
        smartrefresh.setEnableRefresh(false)
        var list_anchor = ReadDbUtil.instance(this).getChatPeople("123456")
        var sendMessageAdapter = AnchorAdapter(this)
        recycleviewmessage.adapter = sendMessageAdapter
        recycleviewmessage.layoutManager = LinearLayoutManager(this)
        sendMessageAdapter.setNewData(list_anchor)
        img_back.setOnClickListener { finish() }
        sendMessageAdapter.setOnItemChildClickListener { adapter, view, position ->
            var intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("receiveImg", list_anchor?.get(position)?.receiveimg)
            intent.putExtra("receiveid", list_anchor?.get(position)?.receiveid)
            intent.putExtra("receiveName", list_anchor?.get(position)?.receivename)
            intent.putExtra("topic", "")
            startActivity(intent)

        }
    }
}