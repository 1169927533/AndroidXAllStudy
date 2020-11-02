package com.example.a11699.androidxallstudy.weibo

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import com.example.a11699.androidxallstudy.R
import java.util.regex.Pattern

/**
 *Create time 2020/11/2
 *Create Yu
 *description:
 */
var mLinkHighlightColor = Color.BLUE // 链接高亮的颜色，默认蓝色

class TopicTextView : androidx.appcompat.widget.AppCompatTextView {
    public var itemClick: ((String) -> Unit?)? = null

    // 定义正则表达式
    private val AT = "@[\u4e00-\u9fa5\\w]+\\s" // @人（末尾含空格）

    private val TOPIC = "#[\u4e00-\u9fa5\\w]+#" // ##话题

    private val EMOJI = "\\[[\u4e00-\u9fa5\\w]+\\]" // 表情

    private val URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" // url

    private var
            REGEXXXX = "($AT)|($TOPIC)|($EMOJI)|($URL)"


    constructor(context: Context) : super(context) {

    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(attributeSet)
    }

    private fun initView(attributeSet: AttributeSet) {
        // 要实现文字的点击效果，这里需要做特殊处理
        movementMethod = LinkMovementMethod.getInstance()

        var typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WeiboTextView)

        // 设置链接高亮颜色
        mLinkHighlightColor = typedArray.getColor(R.styleable.WeiboTextView_linkHighlightColor,
                mLinkHighlightColor)
        typedArray.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(setCustomTypeToView(text), type)
    }

    private fun setCustomTypeToView(text: CharSequence?): SpannableString {
        val spannableString = SpannableString(text)

        // 设置正则

        var mPattern = Pattern.compile(REGEXXXX)
        var matcher = mPattern.matcher(spannableString)

        if (matcher.find()) {
            // 重置正则位置
            matcher.reset()
        }

        while (matcher.find()) {
            // 根据group的括号索引，可得出具体匹配哪个正则(0代表全部，1代表第一个括号)
            val at = matcher.group(1)
            val topic = matcher.group(2)
            val emoji = matcher.group(3)
            val url = matcher.group(4)

            // 处理@符号
            if (at != null) {
                // 获取匹配位置
                val start = matcher.start(1)
                val end = start + at.length
                spannableString.setSpan(MyClickableSpan(at, { itemClick }), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // 处理话题##符号
            if (topic != null) {
                val start = matcher.start(2)
                val end = start + topic.length
                spannableString.setSpan(MyClickableSpan(topic, { itemClick }), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // 处理url地址
            if (url != null) {
                val start = matcher.start(4)
                val end = start + url.length
                spannableString.setSpan(MyClickableSpan(url, { itemClick }), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        return spannableString
    }


    class MyClickableSpan(content: String, var it: () -> Unit) : ClickableSpan() {
        override fun onClick(v: View) {
            it?.let {
                it.invoke()
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = mLinkHighlightColor
            ds.isUnderlineText = false
        }
    }

}