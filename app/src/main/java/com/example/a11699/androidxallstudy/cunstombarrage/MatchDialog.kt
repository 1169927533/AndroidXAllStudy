package com.example.a11699.androidxallstudy.cunstombarrage

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.cunstombarrage.me.MyBarrageRelayout
import kotlinx.android.synthetic.main.activity_barrage.*
import kotlinx.android.synthetic.main.my_barrage_layout.*

/**
 *Create time 2020/6/21
 *Create Yu
 *description:
 */
class MatchDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Transparent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      var view = inflater.inflate(R.layout.my_barrage_layout, container, false)
        var listContent = ArrayList<String>()
        listContent.add("你ewd？")
        listContent.add("你ewd？")
        listContent.add("你ewddd？")
        listContent.add("你ewd？")
        listContent.add("你ewd？")
        listContent.add("你ewddd？")

        view.findViewById<MyBarrageRelayout>(R.id.myvarr).addBarrageView(listContent)
        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 设置宽度为屏宽, 靠近屏幕底部。
        val window = dialog!!.window
        val lp = window!!.attributes
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.MATCH_PARENT // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT // 宽度持平

        window.attributes = lp
    }
}