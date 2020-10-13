package com.example.a11699.comp_animalmehod2.animal;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.SidePropagation;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Create time 2020/10/12
 * Create Yu
 * description:
 */

public class FABTransition extends Visibility {


    private View fab;
    private Context context;
    private static final String BOTTOM_TRANSITION_Y = "FABTransition:change_transY:transitionY";


    public FABTransition(View fab, Context context) {
        this.fab = fab;
        this.context = context;
    }

    /**
     * 收集动画的开始信息
     *
     * @param transitionValues 只有两个成员变量view和values, view指的是我们要从哪个view上收集信息, values是用来存放我们收集到的信息的
     *                         比如: 在captureStartValues里, transitionValues.view指的就是我们在开始动画的界面上的那个view，
     *                         在captureEndValues指的就是在目标界面上的那个view
     */
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        int transY = (int) (context.getResources().getDisplayMetrics().density * 56 * 2);
        transitionValues.values.put(BOTTOM_TRANSITION_Y, transY);

    }

    /**
     * 收集动画结束的信息
     */
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        transitionValues.values.put(BOTTOM_TRANSITION_Y, 0);
    }

    /**
     * 创建一个Animator
     */
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, final View view, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }
        float startY = 1;
        float endY = 0.5f;
        if (view == fab && startY != endY) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startY, endY);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object transY = animation.getAnimatedValue();
                    if (transY != null) {
                        view.setScaleX((Float) transY);
                    }

                }
            });
            return valueAnimator;
        }
        return null;
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, final View view, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }
        float startY = 0.5f;
        float endY = 1f;
        if (view == fab) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startY, endY);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object transY = animation.getAnimatedValue();
                    if (transY != null) {
                        view.setScaleX((Float) transY);
                    }

                }
            });
            return valueAnimator;
        }
        return null;
    }
}
