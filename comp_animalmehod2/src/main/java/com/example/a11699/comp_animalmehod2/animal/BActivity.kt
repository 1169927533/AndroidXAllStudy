package com.example.a11699.comp_animalmehod2.animal

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionSet
import android.transition.Visibility
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.comp_animalmehod2.R
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
       setUpWindowAnimations()
        //  setMyCustonAnimal()
        tvb.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setUpWindowAnimations() {
        val enterTransition: Visibility = buildEnterTransition()
        window.enterTransition = enterTransition
    }

    private fun buildEnterTransition(): Visibility {
        val enterTransition = FABTransition(cc,this)
    /*    enterTransition.addTarget(tvb)*/
        enterTransition.duration = 500
        return enterTransition
    }


    private fun setMyCustonAnimal(){
        val cotentTransition = TransitionSet()
        /*val slide = Slide(Gravity.LEFT)
        slide.duration = 500
        slide.excludeTarget(android.R.id.navigationBarBackground, true)
        slide.excludeTarget(android.R.id.statusBarBackground, true)
      //  slide.excludeTarget(R.id.tvb, true)
        cotentTransition.addTransition(slide)*/
        //fab进入动画
      /*  val fabTransition = FABTransition( )
        fabTransition.addTarget(R.id.tvb)
        fabTransition.duration = 500
        cotentTransition.addTransition(fabTransition)
        window.enterTransition = fabTransition*/
    }
}