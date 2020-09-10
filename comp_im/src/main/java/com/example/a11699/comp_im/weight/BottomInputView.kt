package com.example.a11699.comp_im.weight

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.fragment.InputMoreFragment
import com.example.a11699.comp_im.util.InputUtil
import com.example.a11699.lib_im.util.MessageInfoUtil
import com.example.a11699.lib_im.util.ScreenUtil
import kotlinx.android.synthetic.main.chat_input_layout.view.*

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class BottomInputView : LinearLayout, TextWatcher {
    var state = 0 //0没点过 1展示更多 2不展示更多


    private var mInputContent: String = ""//输入得内容
    var mLastMsgLineCount = 0

    private var mFragmentManager: FragmentManager? = null
    private var inputMoreFragment: InputMoreFragment? = null

    var inputViewInterface: InputViewInterface? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        inflate(context, R.layout.chat_input_layout, this)

        initContentView()
        //点击更多按钮
        more_btn.setOnClickListener {
            inputViewInterface?.let { inputView ->
                if (ScreenUtil.isSoftShowing(this)) {
                    inputView.clickShowMore(1,chat_message_input)
                } else {
                    if (state == 1 || state == 0) {
                        state = 1
                        inputView.clickShowMore(state,chat_message_input)
                        state = 2
                    } else {
                        inputView.clickShowMore(state,chat_message_input)
                        state = 1
                    }
                }
            }
        }

        chat_message_input.addTextChangedListener(this)
        //输入消息框被点击
        chat_message_input.setOnTouchListener { v, event ->
            showSoftInputView()
            false
        }

        send_btn.setOnClickListener {
            inputViewInterface?.let { inputview ->
                inputview.clickSendMessage(MessageInfoUtil.buildTextMessage(chat_message_input.text.trim().toString()), chat_message_input)
            }
        }
    }

    private fun initContentView() {
        if (mFragmentManager == null) {
            mFragmentManager = (context as AppCompatActivity).supportFragmentManager
        }
        if (inputMoreFragment == null) {
            inputMoreFragment = InputMoreFragment()
        }
        mFragmentManager!!.beginTransaction().replace(R.id.more_groups, inputMoreFragment!!).commitAllowingStateLoss()
    }

    override fun afterTextChanged(s: Editable?) {
        if (TextUtils.isEmpty(s.toString().trim { it <= ' ' })) {
            send_btn.isClickable = false
            send_btn.visibility = View.GONE
            more_btn.visibility = View.VISIBLE
        } else {
            send_btn.isClickable = true
            send_btn.visibility = View.VISIBLE
            more_btn.visibility = View.GONE

            if (chat_message_input.lineCount !== mLastMsgLineCount) {
                mLastMsgLineCount = chat_message_input.lineCount
                /*if (mChatInputHandler != null) {
                    mChatInputHandler.onInputAreaClick()
                }*/
            }
            if (!TextUtils.equals(mInputContent, chat_message_input.text.toString())) {
                // FaceManager.handlerEmojiText(mTextInput, mTextInput.getText().toString(), true)
            }
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        mInputContent = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    //收起软件盘
    fun hideSoftInputView() {
        if (chat_message_input.text.isEmpty()) {
            send_btn.visibility = View.GONE
            more_btn.visibility = View.VISIBLE
        } else {
            send_btn.visibility = View.VISIBLE
            more_btn.visibility = View.GONE
        }
        InputUtil.hideSoftInput(chat_message_input, context)
    }

    //展开软键盘
    private fun showSoftInputView() {
        InputUtil.showSoftInout(chat_message_input, context)
    }
}