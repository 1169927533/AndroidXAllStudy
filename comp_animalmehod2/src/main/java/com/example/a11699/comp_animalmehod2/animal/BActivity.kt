package com.example.a11699.comp_animalmehod2.animal

import android.os.Bundle
import android.transition.*
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.comp_animalmehod2.R
import com.example.a11699.comp_animalmehod2.translation.MyTransition
import kotlinx.android.synthetic.main.activity_b.*

/**
 *Create time 2020/10/12
 *Create Yu
 *description:
 */
class BActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
     //   setUpWindowAnimations()
        cc.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setUpWindowAnimations() {
        val enterTransition = buildEnterTransition()
        window.enterTransition = enterTransition
    }

    private fun buildEnterTransition(): Transition {
        val enterTransition = Slide( )
        enterTransition.duration = 500
        return enterTransition
    }


}