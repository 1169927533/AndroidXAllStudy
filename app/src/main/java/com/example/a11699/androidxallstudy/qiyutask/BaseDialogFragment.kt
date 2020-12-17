package com.example.a11699.androidxallstudy.qiyutask

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.a11699.androidxallstudy.R

/**
 * @Author Yu
 * @Date 2020/12/3 14:16
 * @Description TODO
 */
abstract class BaseDialogFragment : DialogFragment() {
    abstract fun inflateLayout(): Int
    abstract fun initView()
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Transparent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(inflateLayout(), container, true)
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 设置宽度为屏宽, 靠近屏幕底部。
        val window = dialog!!.window
        val lp = window!!.attributes
        window.setDimAmount(0.0f)
        lp.gravity = Gravity.BOTTOM // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.windowAnimations = R.style.BottomDialogAnimation
        window.attributes = lp
        initView()
    }

}