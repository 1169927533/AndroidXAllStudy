package com.example.a11699.androidxallstudy.zhezhi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_paper.*
import kotlinx.android.synthetic.main.item_large.*
import kotlinx.android.synthetic.main.item_small.*

/**
 *Create time 2020/9/19
 *Create Yu
 *description:一个仿照折纸效果
 */
class PaperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper)
        layout_small.setOnClickListener {
            paperView.unfold(true, true)
        }

        btn_close.setOnClickListener {
            //   paperView.fold(true, true)
        }
    }
}