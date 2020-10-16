package com.example.a11699.comp_animalmehod2.animal

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.a11699.comp_animalmehod2.R
import com.example.a11699.comp_animalmehod2.TransitionHelper
import kotlinx.android.synthetic.main.activity_a.*

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
        aaaa.setOnClickListener {
            //      var pair = android.util.Pair.create(tv_1 as View, tv_1.transitionName!!)
            var intent = Intent(this, BActivity::class.java)
            val pairs: Array<Pair<View, String>> = TransitionHelper.createSafeTransitionParticipants(this, true)

            var bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this@AActivity, *pairs).toBundle()

            startActivity(intent, bundle)

        }
    }

    private fun setupWindowAnimations() {
        var slideTransLation = Slide()
        slideTransLation.duration = 500
        window.exitTransition = slideTransLation
        // window.enterTransition = slideTransLation

    }
}