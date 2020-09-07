package com.example.a11699.comp_im.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.fragment.ContentFragment
import com.example.a11699.lib_im.Constants
import com.example.a11699.lib_im.bean.ChatInfo
import kotlinx.android.synthetic.main.activity_im.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ImActivity : AppCompatActivity() {

    private var contentFragment: ContentFragment? = null
    private var mFragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im)
        var bundle = intent.extras
        var chatInfo = bundle?.getSerializable(Constants.CHAT_INFO) as ChatInfo
        Log.i("Zjc", chatInfo.messageType.toString())
        addContentToMoreView()
    }

    //消息展示界面
    private fun addContentToMoreView() {
        if (mFragmentManager == null) {
            mFragmentManager = supportFragmentManager
        }
        if (contentFragment == null) {
            var bundle: Bundle? = intent.extras

            contentFragment = ContentFragment(bottom_input_view)
            contentFragment!!.arguments = bundle
        }
       mFragmentManager!!.beginTransaction().replace(R.id.frame_content, contentFragment!!).commitAllowingStateLoss()
        contentFragment!!.viewGroupItemClick = {
            bottom_input_view.hideSoftInputView()
        }
    }

}