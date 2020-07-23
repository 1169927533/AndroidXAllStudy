package com.example.a11699.comp_netstudyt.util

import android.content.ClipboardManager
import android.content.Context
import android.content.res.TypedArray
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText


/**
 * des 视图扩展方法
 * @date 2020/5/14
 * @author zs
 */





/**
 * 防止重复点击,可同时注册多个view
 */
fun setNoRepeatClick(vararg views: View, interval: Long = 400, onClick: (View) -> Unit) {
    views.forEach {
        it.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击
 * @param interval 重复间隔
 * @param onClick  事件响应
 */
var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}

/**
 * 复制剪切板
 */
fun copy(context: Context, msg: String) {
    val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clip.text = msg
    toast("已复制")
}



/**
 * 获取当前主图颜色属性
 */
fun getThemeColor(context: Context,attr:Int):Int{
    val array: TypedArray = context.theme.obtainStyledAttributes(
        intArrayOf(
            attr
        )
    )
    val color = array.getColor(0, -0x50506)
    array.recycle()
    return color
}


/**
 * editText搜索按钮
 * @param onClick 搜索点击事件
 */
fun EditText.keyBoardSearch(onClick:()->Unit) {
    //添加搜索按钮
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onClick()
        }else {
            toast("请输入关键字")
            return@setOnEditorActionListener false
        }
        return@setOnEditorActionListener true
    }
}





