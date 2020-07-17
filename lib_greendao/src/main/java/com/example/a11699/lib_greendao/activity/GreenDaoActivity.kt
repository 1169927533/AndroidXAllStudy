package com.example.a11699.lib_greendao.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a11699.lib_greendao.R
import com.example.a11699.lib_greendao.db.ChatBean
import com.example.a11699.lib_greendao.util.ReadDbUtil
import kotlinx.android.synthetic.main.activity_green_dao.*

class GreenDaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_dao)
        btn_send.setOnClickListener {
            var chatBean = ChatBean()
            chatBean.receiveid = ed_receivedid.text.toString()
            chatBean.sendid = ed_sendid.text.toString()
            chatBean.chatcontent = ed_content.text.toString()
            ReadDbUtil.instance(this).inseartChat(chatBean)
        }
        btn_query.setOnClickListener {
            var listContent = ReadDbUtil.instance(this).getChatInformation(ed_receivedid.text.toString(), ed_sendid.text.toString())
            var c: String = ""
            for (chatbean in listContent) {
                c = c + chatbean.chatcontent+" "
            }
            content.text = c
        }
    }
}
