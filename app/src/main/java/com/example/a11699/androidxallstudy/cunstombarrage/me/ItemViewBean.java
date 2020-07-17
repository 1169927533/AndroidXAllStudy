package com.example.a11699.androidxallstudy.cunstombarrage.me;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Create time 2020/6/21
 * Create Yu
 * description:
 */
public class ItemViewBean {
    View view;
    int leftdistance;
    int childWidth;
    String content;
    ObjectAnimator objectAnimator;


    public ItemViewBean(View view, String content, int leftdistance, int childWidth, ObjectAnimator objectAnimator) {
        this.view = view;
        this.leftdistance = leftdistance;
        this.childWidth = childWidth;
        this.objectAnimator = objectAnimator;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectAnimator getObjectAnimator() {
        return objectAnimator;
    }

    public void setObjectAnimator(ObjectAnimator objectAnimator) {
        this.objectAnimator = objectAnimator;
    }

    public int getChildWidth() {
        return childWidth;
    }

    public void setChildWidth(int childWidth) {
        this.childWidth = childWidth;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getLeftdistance() {
        return leftdistance;
    }

    public void setLeftdistance(int leftdistance) {
        this.leftdistance = leftdistance;
    }
}
