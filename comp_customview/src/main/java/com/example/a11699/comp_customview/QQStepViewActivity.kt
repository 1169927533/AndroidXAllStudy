package com.example.a11699.comp_customview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_qqstep_layout.*

/**
 *Create time 2020/7/5
 *Create Yu
 *description:
 */
class QQStepViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qqstep_layout)
        qqstepview.mStepMax = 3000f
        var valueAnimator = ObjectAnimator.ofFloat(0f, 3000f)
        valueAnimator.setDuration(1000)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener {
            var dd = it.getAnimatedValue() as Float
            qqstepview.setCurrentStep(dd)
        }
        valueAnimator.start()
    }
}