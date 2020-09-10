package com.example.a11699.lib_customview.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
object InputUtil {
    fun showSoftInout(editText: View, context: Context) {
        editText.requestFocus()
        var inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.HIDE_NOT_ALWAYS)//如果输入法打开则关闭，如果没打开则打开
    }

    fun hideSoftInput(editText: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        editText.clearFocus()
    }
}