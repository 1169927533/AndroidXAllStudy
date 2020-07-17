package com.example.a11699.androidxallstudy.webview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.example.a11699.androidxallstudy.MainActivity
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_web_view_study.*

/**
 * 网页js调用android
 * android调用网页js
 */
class WebViewStudy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_study)
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/show.html")
        var ss = dsadsa(this)
        webView.addJavascriptInterface(ss, "justTest");
        btn.setOnClickListener {
            testJS()
        }
    }

    fun testJS() {
        webView.loadUrl("javascript:test()");
    }

    class dsadsa(context: Context) {
        var context = context

        @JavascriptInterface
        public fun hello(msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
