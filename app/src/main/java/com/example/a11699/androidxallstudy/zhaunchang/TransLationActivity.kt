package com.example.a11699.androidxallstudy.zhaunchang

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_translation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 *Create time 2020/10/5
 *Create Yu
 *description:转场效果
 */
class TransLationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)
        translationview.setOnClickListener {
            translationview.startAnimal()
        }
        val spring = SpringForce(0f)
                .setDampingRatio(0.5f)
                .setStiffness(SpringForce.STIFFNESS_VERY_LOW)
        val anim = SpringAnimation(ball, SpringAnimation.TRANSLATION_Y).setSpring(spring)

        bounce.setOnClickListener {
            anim.cancel()
            anim.setStartValue(-100f)
            anim.start()
        }

        initView()
    }

    private var mLetterAnims: ArrayList<SpringAnimation>? = null
    private fun initView() {
        // get the screen height.
        val dm: DisplayMetrics = resources.displayMetrics
        val screenHeight: Int = dm.heightPixels
        mLetterAnims = ArrayList()

        for (i in 0 until cons.childCount) {
            val letterView: View = cons.getChildAt(i)
            letterView.translationY = screenHeight.toFloat() //全部移到屏幕看不到的位置
            var letterAnimY = SpringAnimation(letterView, SpringAnimation.TRANSLATION_Y, 0f)
            letterAnimY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
            letterAnimY.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            mLetterAnims?.add(letterAnimY)
        }

        // text about 'Native messaging'
      //  tv1.translationY = 500f
        tv1.alpha = 0f
        val descTitleAnimY = SpringAnimation(tv1, DynamicAnimation.TRANSLATION_Y, 0f)
        descTitleAnimY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        descTitleAnimY.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY

        val descTitleAlphaAnim: ValueAnimator = ObjectAnimator.ofFloat(0f, 1f)
        descTitleAlphaAnim.duration = 300
        descTitleAlphaAnim.addUpdateListener { valueAnimator -> tv1.alpha = valueAnimator.animatedValue as Float }


        // the button of sign up
      //  bounce.translationY = 500f
        val signUpBtnAnimY = SpringAnimation(bounce, SpringAnimation.TRANSLATION_Y, 0f)
        signUpBtnAnimY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        signUpBtnAnimY.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY


        // top logo by left
      //  img1.translationY = 400f
        img1.alpha = 0f
        val leftLogoAnimY = SpringAnimation(img1, SpringAnimation.TRANSLATION_Y, 0f)
        leftLogoAnimY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        leftLogoAnimY.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        leftLogoAnimY.setStartVelocity(-2000f)

        // top logo by right
     //   img2.translationY = 400f
        img2.alpha = 0f
        val rightLogoAnimY = SpringAnimation(img2, SpringAnimation.TRANSLATION_Y, 0f)
        rightLogoAnimY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        rightLogoAnimY.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        rightLogoAnimY.setStartVelocity(-2000f)

        val logoAlphaAnim = ObjectAnimator.ofFloat(0f, 1f)
        logoAlphaAnim.duration = 600
        logoAlphaAnim.addUpdateListener { valueAnimator ->
            img1.alpha = valueAnimator.animatedValue as Float
            img1.alpha = valueAnimator.animatedValue as Float
        }

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            leftLogoAnimY.start()
            img2.postDelayed(Runnable { rightLogoAnimY.start() }, 150)
            tv2.postDelayed(Runnable {
                descTitleAlphaAnim.startDelay = 100
                descTitleAlphaAnim.start()
                descTitleAnimY.start()
                signUpBtnAnimY.start()
            }, 300)
            for (letterAnim in mLetterAnims!!) {
                cons.postDelayed(Runnable { letterAnim.start() }, 12 * mLetterAnims!!.indexOf(letterAnim).toLong())
            }
            logoAlphaAnim.start()
        }
    }
}