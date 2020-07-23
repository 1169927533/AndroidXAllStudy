package com.example.a11699.androidxallstudy.sendgift

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_sendgift.*

/**
 *Create time 2020/7/22
 *Create Yu
 *description:
 */
class SendGiftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendgift)
        countdownview.setOnClickListener {
            countdownview.startClick()
        }
    }
}