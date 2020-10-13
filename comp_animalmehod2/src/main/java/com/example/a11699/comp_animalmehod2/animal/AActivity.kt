package com.example.a11699.comp_animalmehod2.animal

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.transition.Visibility
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.comp_animalmehod2.R
import kotlinx.android.synthetic.main.activity_a.*
import kotlinx.android.synthetic.main.activity_b.*

/**
 *Create time 2020/10/12
 *Create Yu
 *description:
 */
class AActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        setupWindowAnimations()
        tv_1.setOnClickListener {
            var intent = Intent(this, BActivity::class.java)
            var bundle = ActivityOptions.makeSceneTransitionAnimation(this@AActivity).toBundle()
            startActivity(intent, bundle)
        }
    }

    private fun setupWindowAnimations() {
        /*   var slideTransLation = Slide()
           slideTransLation.slideEdge = Gravity.LEFT
           slideTransLation.duration = 500
           window.reenterTransition = slideTransLation
           window.exitTransition = slideTransLation*/

        val enterTransition = FABTransition(aaaa, this)
        /*    enterTransition.addTarget(tvb)*/
        enterTransition.duration = 500
        window.enterTransition = enterTransition
        window.exitTransition = enterTransition

    }
}