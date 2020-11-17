package com.example.a11699.androidxallstudy.inanimal

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_inanimal.*


/**
 * @Author Yu
 * @Date 2020/11/17 16:58
 * @Description 进场动画
 */
class LiveRoomInAnimalActivity : BaseActivity() {
    lateinit var inRoonmAnimal: ObjectAnimator
    lateinit var controller: DraweeController
    override fun getLayoutId(): Int {
        return R.layout.activity_inanimal
    }

    override fun initView() {
        initFresco()
        initInRoomAnimal()
        btn_inroom.setOnClickListener {

        }
        btn_startanimal.setOnClickListener {
            inRoonmAnimal.start()
        }
    }

    override fun observeLiveData() {
    }


    override fun initViewData() {
    }

    private fun initFresco() {
        controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri("https://p.upyun.com/demo/webp/webp/animated-gif-0.webp")
                .setOldController(simpleview.controller)
                .build()
        simpleview.controller = controller

    }


    /**
     * 初始化进场动画
     */
    private fun initInRoomAnimal() {
        var parentWidth = ViewUtil.getScreenWidth(this)
        inRoonmAnimal = ObjectAnimator.ofFloat(simpleview, "translationX", 0f, -(parentWidth / 2f + 310 * 3 / 2))
        inRoonmAnimal.duration = 2000
        inRoonmAnimal.repeatCount = ValueAnimator.INFINITE
        inRoonmAnimal.repeatMode = ValueAnimator.RESTART
        inRoonmAnimal.interpolator = DecelerateInterpolator()
        inRoonmAnimal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }


            override fun onAnimationEnd(animation: Animator?) {
                simpleview.setImageURI("https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/sports_car.webp")
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }

}