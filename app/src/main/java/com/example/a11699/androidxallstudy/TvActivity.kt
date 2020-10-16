package com.example.a11699.androidxallstudy

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import kotlinx.android.synthetic.main.activity_tv.*

/**
 *Create time 2020/10/16
 *Create Yu
 *description:
 */
class TvActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv)
        var actionText = StringBuilder()
        actionText.append("<a style=\"text-decoration:none;\" href='username'>"
                + "username:" + " </a>")
        var s = "dsa"
        actionText
                .append("<a隐形人"
                        + "<a style=\"color:blue;text-decoration:none;\" href='${s}'> "
                        + " love" + "</a>");
        actionText.append(" : \"" + "孙燕姿" + "\"");
        tv.text = Html.fromHtml(actionText.toString());
        tv.movementMethod = LinkMovementMethod
                .getInstance();
        val text: CharSequence = tv.text
        val ends = text.length
        val spannable: Spannable = tv.text as Spannable
        val urlspan: Array<URLSpan> = spannable.getSpans(0, ends, URLSpan::class.java)
        val stylesBuilder = SpannableStringBuilder(text)
        stylesBuilder.clearSpans() // should clear old spans

        for (url in urlspan) {

            val myURLSpan = TextViewURLSpan(this, url.url)
            stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                    spannable.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        tv.setText(stylesBuilder)



    }

    private class TextViewURLSpan(val context: Context, private val clickString: String) : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.setColor(getColor(context, R.color.colorPrimaryDark))
            ds.setUnderlineText(false) //去掉下划线
        }

        override fun onClick(widget: View) {

                Toast.makeText(context, clickString, Toast.LENGTH_LONG)
                        .show()


        }


    }

}