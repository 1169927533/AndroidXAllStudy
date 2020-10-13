package com.example.a11699.androidxallstudy.botomtab

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_copy_tang.*
import kotlinx.android.synthetic.main.activity_copy_tang.img1
import kotlinx.android.synthetic.main.activity_copy_tang.tv1

/**
 *Create time 2020/10/9
 *Create Yu
 *description:仿照躺平中间菜单栏app
 */
class CopyTangApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_tang)

        initAnimator()

        trview.postDelayed({
            trview.startAnimal()
            tv1Animator.start()
            tv2Animator.start()
            descTitleAlphaAnim.start()
            img1.animate()
                    .rotation(90f)
                    .duration = 500
        }, 100)

        img1.setOnClickListener {
            img1.animate()
                    .rotation(-90f)
                    .duration = 500
            tv1.animate().alpha(0f).duration=500
            tv2.animate().alpha(0f).duration=500
            img1.animate().alpha(0f).duration=500
            trview.animaor_Close?.apply {
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        finish()
                       // overridePendingTransition(R.anim.fade_in2, R.anim.fade_out2)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
            }
            trview.startCloseAnimal()
        }
    }


    lateinit var tv1Animator: SpringAnimation
    lateinit var tv2Animator: SpringAnimation
    lateinit var descTitleAlphaAnim: ValueAnimator

    private fun initAnimator() {
        val dm: DisplayMetrics = resources.displayMetrics
        val screenHeight: Int = dm.heightPixels
        tv1.translationY = screenHeight.toFloat()
        tv2.translationY = screenHeight.toFloat()

        tv1.alpha = 0f
        tv2.alpha = 0f


        tv1Animator = SpringAnimation(tv1, DynamicAnimation.TRANSLATION_Y, 0f)
        tv1Animator.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        tv1Animator.spring.dampingRatio = 0.63f


        tv2Animator = SpringAnimation(tv2, DynamicAnimation.TRANSLATION_Y, 0f)
        tv2Animator.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        tv2Animator.spring.dampingRatio = 0.63f

        descTitleAlphaAnim = ObjectAnimator.ofFloat(0f, 1f)
        descTitleAlphaAnim.duration = 300
        descTitleAlphaAnim.addUpdateListener { valueAnimator ->
            tv1.alpha = valueAnimator.animatedValue as Float
            tv2.alpha = valueAnimator.animatedValue as Float
        }

    }


}