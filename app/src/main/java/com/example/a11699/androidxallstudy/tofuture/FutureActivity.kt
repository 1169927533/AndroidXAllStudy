package com.example.a11699.androidxallstudy.tofuture

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.tofuture.view.FutureView
import com.example.a11699.lib_im.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_tofuture.*

/**
 *Create time 2020/9/29
 *Create Yu
 *description:仿照给未来写封信
 */
class FutureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tofuture)
        future_view.setOnClickListener {
            img1.visibility = View.GONE
            if (future_view.foldState == FutureView.FoldState.FOLD) {
                future_view.prePareUnFoldData()
                startAnimalSet(2)
            } else {
                future_view.prePareFoldData()
                startAnimalSet(1)
            }
        }
    }

    lateinit var yScaleAnimal: ObjectAnimator
    lateinit var xScaleAnimal: ObjectAnimator

    /**
     * methodAnimator = 1 展开动画
     * methodAnimator = 2 折叠动画
     */
    private fun startAnimalSet(methodAnimator: Int) {
        var scaleX = 0f
        scaleX = if (future_view.sourceBitMap.width > ScreenUtil.getScreenTotalWidth(this)) {
            future_view.sourceBitMap.width.toFloat() / ScreenUtil.getScreenTotalWidth(this).toFloat()
        } else {
            ScreenUtil.getScreenTotalWidth(this).toFloat() / future_view.sourceBitMap.width.toFloat()
        }

        var scaleY = com.example.a11699.lib_util.ScreenUtil.getScreenHeight(this).toFloat() / future_view.sourceBitMap.height.toFloat()

        when (methodAnimator) {
            1 -> {
                yScaleAnimal = ObjectAnimator.ofFloat(future_view,
                        "scaleY", 1f, scaleY)
                xScaleAnimal = ObjectAnimator.ofFloat(future_view,
                        "scaleX", 1f, scaleX)
            }
            2 -> {
                yScaleAnimal = ObjectAnimator.ofFloat(future_view,
                        "scaleY", scaleY, 1f)
                xScaleAnimal = ObjectAnimator.ofFloat(future_view,
                        "scaleX", scaleX, 1f)
            }
        }
        var yTransLateAnimal  =
        var animatorSet = AnimatorSet().apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (methodAnimator == 1) {
                        img1.visibility = View.VISIBLE
                    } else {
                        img1.visibility = View.GONE
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
        }
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.playTogether(future_view.animator, xScaleAnimal, yScaleAnimal)
        animatorSet.duration = (1000)
        animatorSet.start()
    }
}