package com.example.a11699.androidxallstudy.cunstombarrage

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.activity_barrage.*

/**
 * 自定义弹幕滚动
 */
class BarrageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage)

        var animatorSet = AnimatorSet()
        var scaleX = ObjectAnimator.ofFloat(img_animal, "scaleX", 0.7f, 1f)
        var scaleY = ObjectAnimator.ofFloat(img_animal, "scaleY", 0.7f, 1f)
        scaleX.repeatCount = Animation.INFINITE
        scaleY.repeatCount = Animation.INFINITE
        scaleX.repeatMode = ObjectAnimator.REVERSE
        scaleY.repeatMode = ObjectAnimator.REVERSE

        animatorSet.duration = 700

        animatorSet.setInterpolator(AccelerateDecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY)
        animatorSet.start()

        btn_startanimal.setOnClickListener {
            var d = MatchDialog()
            d.show(supportFragmentManager, "match")
        }
    }

    override fun onPause() {
        super.onPause()

    }


}
