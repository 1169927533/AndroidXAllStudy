package com.example.a11699.androidxallstudy.botomtab;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

/**
 * Create time 2020/10/16
 * Create Yu
 * description:
 */
class Fade3 extends Visibility {
    static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    private static boolean DBG = false;

    private static final String LOG_TAG = "Fade";

    /**
     * Constructs a Fade transition that will fade targets in and out.
     */
    public Fade3() {
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA,
                transitionValues.view.getTransitionAlpha());
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view,
                             TransitionValues startValues,
                             TransitionValues endValues) {
        float startY = 1f;
        float endY = 1f;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(startY, endY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transY = (float) animation.getAnimatedValue();
                view.setScaleX(transY);
                view.setScaleY(transY);

            }
        });
        return valueAnimator;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Animator onDisappear(ViewGroup sceneRoot, final View view, TransitionValues startValues,
                                TransitionValues endValues) {
        float startY = 1f;
        float endY = 1f;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(startY, endY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transY = (float) animation.getAnimatedValue();
                view.setScaleX(transY);
                view.setScaleY(transY);

            }
        });
        return valueAnimator;
    }

}
