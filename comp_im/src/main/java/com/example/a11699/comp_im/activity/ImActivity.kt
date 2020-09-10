package com.example.a11699.comp_im.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.fragment.ContentFragment
import com.example.a11699.comp_im.fragment.ContentMultiFragment
import com.example.a11699.comp_im.fragment.InputMoreFragment
import com.example.a11699.comp_im.weight.InputViewInterface
import com.example.a11699.lib_im.Constants
import com.example.a11699.lib_im.bean.ChatInfo
import com.example.a11699.lib_im.bean.MessageInfo
import kotlinx.android.synthetic.main.activity_im.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class ImActivity : AppCompatActivity() {

    private var contentFragment: ContentFragment? = null
    private var contentMultiFragment: ContentMultiFragment? = null
    private var inputMoreFragment: InputMoreFragment? = null

    private var mFragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im)
        var bundle = intent.extras
        var chatInfo = bundle?.getSerializable(Constants.CHAT_INFO) as ChatInfo
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

        if (contentMultiFragment == null) {
            var bundle: Bundle? = intent.extras
            contentMultiFragment = ContentMultiFragment(bottom_input_view)
            contentMultiFragment!!.arguments = bundle
        }


        if (inputMoreFragment == null) {
            inputMoreFragment = InputMoreFragment()
        }
        mFragmentManager!!.beginTransaction().replace(R.id.more_groups, inputMoreFragment!!)

        mFragmentManager!!.beginTransaction().replace(R.id.frame_content, contentMultiFragment!!).commitAllowingStateLoss()
        contentMultiFragment!!.viewGroupItemClick = {
            //  bottom_input_view.hideSoftInputView()
        }

        bottom_input_view.inputViewInterface = object : InputViewInterface {
            override fun clickSendMessage(msg: MessageInfo, messageInputView: View) {
                contentMultiFragment!!.sendMessage(msg, messageInputView)
            }

            override fun clickShowMore(showMore: Int, editText: EditText) {
                soft_viewgroup.mEditText = editText
                soft_viewgroup.setValeToShowMore(showMore)
            }
        }
    }


}