package com.example.a11699.androidxallstudy.inanimal

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_inanimal.*
import java.util.*


/**
 * @Author Yu
 * @Date 2020/11/17 16:58
 * @Description 进场动画
 * https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/flying_carpet.webp 飞毯
https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/jeep.webp 吉普
https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/motorcycle.webp 摩托
https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/private_aircraft.webp 私人飞机
https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/sports_car.webp 跑车
https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/liner.webp 邮轮
 */
class LiveRoomInAnimalActivity : BaseActivity() {
    lateinit var inRoonmAnimal: ObjectAnimator
    lateinit var controller: DraweeController

    var inRoomList = arrayListOf<String>("https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/sports_car.webp",
            "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/liner.webp",
            "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/private_aircraft.webp",
            "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/motorcycle.webp",
            "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/jeep.webp",
            "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/flying_carpet.webp"
    )

    var listDataResource = LinkedList<String>()



    override fun getLayoutId(): Int {
        return R.layout.activity_inanimal
    }

    override fun initView() {
        initInRoomAnimal()
        var intem = 0
        initFresco("https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/flying_carpet.webp")
        btn_inroom.setOnClickListener {
            if(inRoonmAnimal.isRunning){
                if(intem>inRoomList.size-1){
                    intem = 0
                }
                listDataResource.add(inRoomList[intem])
            }else{
                listDataResource.add(inRoomList[0])
                inRoonmAnimal.start()
            }
            intem++
        }
    }

    override fun observeLiveData() {
    }


    override fun initViewData() {
    }



    /**
     * 初始化进场动画
     */
    private fun initInRoomAnimal() {
        var parentWidth = ViewUtil.getScreenWidth(this)
        inRoonmAnimal = ObjectAnimator.ofFloat(simpleview, "translationX", 0f, -(parentWidth / 2f + 310 * 3 / 2))
        inRoonmAnimal.duration = 2000
        inRoonmAnimal.startDelay = 1
        inRoonmAnimal.interpolator = DecelerateInterpolator()
        inRoonmAnimal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                 initFresco(listDataResource[0])

                listDataResource.removeAt(0)
            }


            override fun onAnimationEnd(animation: Animator?) {
                if(listDataResource.size>0){
                    inRoonmAnimal.start()
                    inRoonmAnimal.startDelay = 1 //设置一个延迟 是因为在低版本手机 会出现调用start方法的时候 onAnimationStart不会被调用的问题。
                }else{
                    simpleview.setImageURI("")
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }


    private fun initFresco(url: String) {
        controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(url)
                .setOldController(simpleview.controller)
                .build()

        simpleview.controller = controller
    }

}