package com.example.a11699.androidxallstudy.piaodanmu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_danmu.*

/**
 *Create time 2020/7/31
 *Create Yu
 *description:飘屏弹幕
 */
class DanMuActivity : AppCompatActivity() {
    private val danManager by lazy {
        DanmukManager<Danmuke>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danmu)
        danManager.addView(DanmukViewStub(stubDanmuk1))
        danManager.addView(DanmukViewStub(stubDanmuk2))

        btn_showdanmu.setOnClickListener {
            danManager.onDanmukArrived(Danmuke("dsa","223"))
        }
    }
}