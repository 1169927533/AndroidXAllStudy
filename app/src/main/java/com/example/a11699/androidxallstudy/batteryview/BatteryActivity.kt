package com.example.a11699.androidxallstudy.batteryview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_battery.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Create time 2020/9/12
 *Create Yu
 *description:自定义电池
 */
class BatteryActivity : AppCompatActivity() {
    private val countDownJob by lazy {
        lifecycleScope.launch {
            var index = batteryView.thePowerMaxNum
            while (true) {
                delay(500)
                if (index <  0) {
                    index = batteryView.thePowerMaxNum
                }
                batteryView.setPaintColor(index)
                index--
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)
        batteryView.setOnClickListener {
            countDownJob.start()
        }
    }
}