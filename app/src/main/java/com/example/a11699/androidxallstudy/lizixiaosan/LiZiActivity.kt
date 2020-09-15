package com.example.a11699.androidxallstudy.lizixiaosan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_battery.*
import kotlinx.android.synthetic.main.activity_lizi.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Create time 2020/9/14
 *Create Yu
 *description:
 */
class LiZiActivity : AppCompatActivity() {
    private val countDownJob by lazy {
        lifecycleScope.launch {
            while (true) {
                delay(500)
                path_view.startAnimal()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lizi)
        path_view.setOnClickListener {
            countDownJob.start()
        }
        path_view_getpos.setOnClickListener {
            path_view_getpos.startAnimal()
        }
    }
}