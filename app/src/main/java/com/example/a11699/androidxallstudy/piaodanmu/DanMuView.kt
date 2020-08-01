package com.example.a11699.androidxallstudy.piaodanmu

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ScreenUtil
import kotlinx.android.synthetic.main.view_danmu_item_view.view.*
import java.util.*

/**
 *Create time 2020/7/31
 *Create Yu
 *description:
 */
class DanMuView : FrameLayout, IDanmukView<Danmuke> {
    val dmAnimalView = LinkedList<View>()
    private var isNextAble = true
    val animatorTime = 3500L
    private var danmuke: Danmuke? = null
    private var parentWidth: Int = 0

    // 可以开始下一个弹幕了
    var nextAvalibeCall: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }

    override fun onNewModel(mode: Danmuke) {
        var viewGroup = LayoutInflater.from(context).inflate(R.layout.view_danmu_item_view, this, false)

        var s = ScreenUtil.getScreenWidth()
        setDamukeAniml(viewGroup, mode, s)

        nextAvalibeCall = {
            isNextAble = true
            checkNext()
        }

        if (isNextAble) {
            startNext(viewGroup)
        } else {
            dmAnimalView.add(viewGroup)
        }

    }

    override fun clear() {
        dmAnimalView.clear()
        nextAvalibeCall = null

    }

    private fun checkNext() {
        val next = dmAnimalView.poll()
        next?.let {
            startNext(it)
        }
    }

    private fun startNext(item: View) {
        if (item.parent != null) {
            var s = item.parent as ViewGroup
            s.removeAllViews()
        }
        addView(item, FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        isNextAble = false
        item.post { start(item) }
    }


    fun setDamukeAniml(view: View, danmuke: Danmuke, parentWidth: Int) {
        this.danmuke = danmuke
        this.parentWidth = parentWidth
        view.tvDanmukSenderName.setText(danmuke.senderName)
        view.tvDanmukContent.setText(danmuke.content)
        view.tvDanmukSenderLevel.setText(danmuke.content)
    }


    fun start(view: View) {
        val ta = ObjectAnimator.ofFloat(
                view,
                "translationX",
                parentWidth.toFloat(),
                -view.width.toFloat()
        )
        ta.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                removeView(view)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }


        })

        ta.duration = animatorTime
        ta.start()
        postDelayed({ nextAvalibeCall?.invoke() }, (animatorTime / 3).toLong())
    }


}