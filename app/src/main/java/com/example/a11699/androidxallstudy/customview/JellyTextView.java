package com.example.a11699.androidxallstudy.customview;

/**
 * Create time 2020/7/11
 * Create Yu
 * description:
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.TextView;

/**
 * Created by zhaokaiqiang on 15/2/28.
 */
public class JellyTextView extends androidx.appcompat.widget.AppCompatButton {

    private OverScroller mScroller;

    private float lastX;
    private float lastY;

    private float startX;
    private float startY;

    public JellyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context, new DecelerateInterpolator());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果子View被分发了事件，请求父布局不要在之后拦截事件
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float disX = event.getRawX() - lastX;
                float disY = event.getRawY() - lastY;

                offsetLeftAndRight((int) disX);
                offsetTopAndBottom((int) disY);
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                mScroller.startScroll((int) getX(), (int) getY(), -(int) (getX() - startX),
                        -(int) (getY() - startY));
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            invalidate();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = getX();
        startY = getY();
    }

    public void spingBack() {

        if (mScroller.springBack((int) getX(), (int) getY(), 0, (int) getX(), 0,
                (int) getY() - 100)) {
            Log.d("TAG", "getX()=" + getX() + "__getY()=" + getY());
            invalidate();
        }

    }


}