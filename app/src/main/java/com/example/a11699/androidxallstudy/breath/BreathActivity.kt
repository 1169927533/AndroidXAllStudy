package com.example.a11699.androidxallstudy.breath

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_breath.*

/**
 *Create time 2020/10/3
 *Create Yu
 *description:
 */
class BreathActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)
        gloriousView.setOnClickListener {
            if (gloriousView.animator.isRunning) {
                gloriousView.stopAnimal()
            } else {
                gloriousView.startAnimal()
            }
        }
        breath.setOnClickListener {
            breath.startLightAnim()
        }
        breathView2.setOnClickListener {
            if (breathView2.animatorRadius!!.isRunning) {
                breathView2.stopAnimal()
            } else {
                breathView2.startAnimal()
            }
        }
    }
}