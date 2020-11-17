package com.example.a11699.androidxallstudy.meinformation

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.a11699.androidxallstudy.R

/**
 * @Author Yu
 * @Date 2020/11/12 14:02
 * @Description
 */
class MyTestDialog:DialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Transparent)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_mytest, container, false)
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 设置宽度为屏宽, 靠近屏幕底部。
        val window = dialog!!.window
        val lp = window!!.attributes
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT // 宽度持平

        window.attributes = lp
    }
}