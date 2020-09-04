package com.example.a11699.comp_chat

import android.os.Bundle

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a11699.comp_chat.adapter.SendMessageAdapter
import com.example.a11699.comp_chat.bean.ChatBean
import com.example.a11699.comp_chat.util.DataUtilKotlin
import com.example.a11699.comp_chat.util.ReadDbUtil

import kotlinx.android.synthetic.main.activity_chat_local.*


/**
 *Create time 2020/6/22
 *Create Yu
 *description:本地得聊天
 */
class ChatActivity : AppCompatActivity() {
    var dataList: MutableList<ChatBean> = mutableListOf()
    var receiveImg: String = ""
    var receiveid: String = ""
    var receiveName: String = ""
    var topic: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_local)

        intent?.let {
            receiveImg = it.getStringExtra("receiveImg").toString()//对方
            receiveid = it.getStringExtra("receiveid").toString()//对方id
            receiveName = it.getStringExtra("receiveName").toString()//对方名字

            topic = it.getStringExtra("topic").toString()//话题
            if (topic != "") {
                var chatBean = ChatBean(SendMessageAdapter.TYPE_RIGHT, "123456",
                       "余本人",
                        "",DataUtilKotlin.getNowTime_HOUR(), DataUtilKotlin.getNowTime_HOUR()
                        , topic, receiveid, receiveName, receiveImg)
                ReadDbUtil.instance(this).inseartChat(chatBean)
            }

            tv_name.text = receiveName
        }
        initData()
        initView()
        showSoftInputFromWindow()

    }

    fun initData() {
        dataList = ReadDbUtil.instance(this).getChatInformation(receiveid, "123456")!!
    }

    fun initView() {
        img_back.setOnClickListener { finish() }
        var sendMessageAdapter = SendMessageAdapter(dataList, this)
        recycleview.adapter = sendMessageAdapter
        recycleview.layoutManager = LinearLayoutManager(this)

        tv_send.setOnClickListener {
            var content = ed_content.text.toString()
            if (content != "") {
                var chatBean = ChatBean(SendMessageAdapter.TYPE_RIGHT,"123456" ,
                       "余本人",
                        "",DataUtilKotlin.getNowTime_HOUR(), DataUtilKotlin.getNowTime_HOUR()
                        , content, receiveid, receiveName, receiveImg)
                dataList.add(chatBean)
                ReadDbUtil.instance(this).inseartChat(chatBean)
                sendMessageAdapter!!.notifyItemInserted(dataList.size - 1)
                ed_content.setText("")
                recycleview.scrollToPosition(sendMessageAdapter.itemCount - 1)
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private fun showSoftInputFromWindow() {
        ed_content.isFocusable = true
        ed_content.isFocusableInTouchMode = true
        ed_content.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}