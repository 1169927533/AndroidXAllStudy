package com.example.a11699.androidxallstudy.piaodanmu

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.a11699.androidxallstudy.R


class DanmukItemView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, mAttributeSet: AttributeSet?) : super(context, mAttributeSet)

    val animatorTime = 3500L

    init {
        LayoutInflater.from(context).inflate(R.layout.view_danmu_item_view, this, true)
    }


    var endCall: ((giftAnimalBean: GiftAnimalBean) -> Unit)? = null

    // 可以开始下一个弹幕了

    var nextAvalibeCall: (() -> Unit)? = null

    private var nextAvalibeCalled = false
    private val tansAni by lazy {
        val ta = ObjectAnimator.ofFloat(
                this,
                "translationX",
                parentWidth.toFloat(),
                -this.width.toFloat()
        )
        ta.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                giftAnimalBean!!.isBusy = false
                endCall!!(giftAnimalBean!!)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }


        })

        ta.addUpdateListener {
//            val c =it.animatedValue as Float
//            if(!nextAvalibeCalled&& c > (parentWidth - width*1.5f)){
//                nextAvalibeCall?.invoke()
//                nextAvalibeCalled=true
//            }

        }

        ta.duration = animatorTime
        ta
    }


    private var parentWidth: Int = 0
    private var giftAnimalBean: GiftAnimalBean? = null
    fun setDamukeAniml(parentWidth: Int, giftAnimalBean: GiftAnimalBean) {
        this.giftAnimalBean = giftAnimalBean
        this.parentWidth = parentWidth
    }

    fun clear() {
        nextAvalibeCall = null
        endCall = null
        tansAni.cancel()
    }


    fun start() {
        tansAni.start()
        postDelayed({ nextAvalibeCall?.invoke() }, (animatorTime * (width * 1.1 / parentWidth)).toLong())
    }

}