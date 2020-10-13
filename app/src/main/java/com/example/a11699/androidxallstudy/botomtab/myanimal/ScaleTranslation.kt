package com.example.a11699.androidxallstudy.botomtab.myanimal

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.transition.TransitionValues
import android.transition.Visibility
import android.view.View
import android.view.ViewGroup
import com.example.a11699.comp_animalmehod2.animal.FABTransition

/**
 *Create time 2020/10/13
 *Create Yu
 *description:
 */
class ScaleTranslation : Visibility {
    private var fab: View? = null
    private var context: Context? = null
    private var label: String = "0"//jin 进场动画 chu出场动画

    constructor(view: View, mContext: Context, mLabel: String) : super() {
        fab = view
        context = mContext
        label = mLabel
    }

    override fun onAppear(sceneRoot: ViewGroup?, view: View, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (null == startValues || null == endValues) {
            return null
        }
        if (label == "jin") {
            val startY = 1f
            val endY = 0.5f
            if (view == fab) {
                val valueAnimator = ValueAnimator.ofFloat(startY, endY)
                valueAnimator.addUpdateListener { animation ->
                    val transY = animation.animatedValue
                    if (transY != null) {
                        view.scaleX = (transY as Float)
                        view.scaleY = (transY as Float)

                    }
                }
                return valueAnimator
            }
        } else if (label == "chu") {
            val startY = 1f
            val endY = 0.5f
            if (view === fab) {
                val valueAnimator = ValueAnimator.ofFloat(startY, endY)
                valueAnimator.addUpdateListener { animation ->
                    val transY = animation.animatedValue
                    if (transY != null) {
                        view.scaleX = (transY as Float)
                        view.scaleY = (transY as Float)

                    }
                }
                return valueAnimator
            }
        }

        return null
    }

    override fun onDisappear(sceneRoot: ViewGroup?, view: View, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (null == startValues || null == endValues) {
            return null
        }
        if (label == "jin") {
            val startY = 1f
            val endY = 0.5f
            if (view === fab) {
                val valueAnimator = ValueAnimator.ofFloat(startY, endY)
                valueAnimator.addUpdateListener { animation ->
                    val transY = animation.animatedValue
                    if (transY != null) {
                        view.scaleX = (transY as Float)
                        view.scaleY = (transY as Float)

                    }
                }
                return valueAnimator
            }
        } else if (label == "chu") {
            return null
        }

        return null
    }

}