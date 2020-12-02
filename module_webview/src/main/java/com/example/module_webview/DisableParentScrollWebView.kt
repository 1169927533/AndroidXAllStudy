package com.example.module_webview

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

/**
 * @Author Yu
 * @Date 2020/12/1 16:21
 * @Description TODO
 */
class DisableParentScrollWebView : WebView {
    constructor(context: Context) : super(getFixedContext(context))
    constructor(context: Context, attributeSet: AttributeSet) : super(getFixedContext(context), attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(getFixedContext(context), attributeSet, defStyleAttr)

    companion object {
        fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                context.createConfigurationContext(Configuration())
            } else {
                context
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN->{
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.onTouchEvent(event)
    }
}