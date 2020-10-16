package com.example.a11699.comp_animalmehod2.translation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_a.*

/**
 *Create time 2020/10/15
 *Create Yu
 *description:
 */
class MyTransition(var view: View) : Transition() {
    private var TOP = "top"
    private var HEIGHT = "height"
    private var mView: View? = null

    init {
        mView = view
    }

    /**
     * 收集动画的开始信息
     */
    override fun captureStartValues(transitionValues: TransitionValues) {
        var mView = transitionValues.view
        var rect = Rect()
        mView.getHitRect(rect)//获取在父类的矩阵坐标
        transitionValues.values[TOP] = rect.top
        transitionValues.values[HEIGHT] = mView.height
    }

    /**
     * 收集动画的结束信息
     */
    override fun captureEndValues(transitionValues: TransitionValues) {
        transitionValues.values[TOP] = 0;
        transitionValues.values[HEIGHT] = transitionValues.view.height;
    }

    override fun isTransitionRequired(startValues: TransitionValues?, endValues: TransitionValues?): Boolean {
        return true
    }

    /**
     * 上面两个都收集完了，就开始创建动画
     */
    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues, endValues: TransitionValues): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }

        var startY = 1f
        var endY = 0.5f
        var valueAnimator = ObjectAnimator.ofFloat(startY, endY)
        valueAnimator.addUpdateListener {
            var transY = it.animatedValue as Float
            startValues.view.scaleX = (transY)
            endValues.view.scaleX = (transY)
        }

        return valueAnimator;
    }
}