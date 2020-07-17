package com.example.a11699.androidxallstudy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Create time 2020/7/11
 * Create Yu
 * description:
 */
public class FollowFingerView extends View{

    //绘制圆形
    private Paint mPaint;

    //绘制背景
    private Paint mBackGroundPaint;
    //View的宽和高
    private int mWidth;
    private int mHeight;
    //定义Scroller
    private Scroller mScroller;
    //存储上次View的位置参数
    private int mLastX;
    private int mLastY;

    public FollowFingerView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public FollowFingerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // TODO Auto-generated constructor stub
    }
    private void init(){
        mPaint = new Paint();
//设置圆形颜色
        mPaint.setColor(0x22ff0000);
        mBackGroundPaint=new Paint();
//设置背景颜色
        mBackGroundPaint.setColor(0xfff8efe0);
        mScroller = new Scroller(getContext());
//设置默认宽高，wrap_content时使用
        mWidth = 400;
        mHeight = 400;
    }
    @Override
    protected void onDraw(Canvas canvas) {
//绘制背景
        canvas.drawPaint(mBackGroundPaint);
        //绘制半径为30像素的圆形
        int radius = 30;
        canvas.drawCircle(30,  30, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
//处理wrap_content失效
        setMeasuredDimension(measureWidth(widthMode,width), measureHeight(heightMode,height));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //获取触发事件时，手指的触碰位置
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下时，移动View到触碰位置
                scrollTo(-x, -y);
                break;
            case MotionEvent.ACTION_MOVE:
//事件发生时手指的位置与上一个事件结束时手指的位置，之间的距离
//注意方向，可能这样写更容易理解：
//int dx = -(x - mLastX);
//int dy = -(y - mLastY);

                int dx = mLastX - x;
                int dy = mLastY - y;
                //跟随手指移动
                scrollBy(dx, dy);
                break;

            case MotionEvent.ACTION_UP:
//手指抬起的时候，开始返回初始位置
                mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -getScrollY(), 500);
                Log.d("Follow", "getScrollX is " + getScrollX());
                invalidate();
                break;

            default:
                break;
        }
//记录事件结束时手指的位置
        mLastX = x;
        mLastY = y;
        //该View不是clickable的，返回值默认为false，应该手动改为true，否则不能消费事件，只能执行down
        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private int measureWidth(int widthMode,int widthSize){
        switch (widthMode) {

            case MeasureSpec.EXACTLY:
                mWidth=widthSize;
                //Log.d("ViewConstructor", "mode is exactly");
                break;

            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            default:
                break;
        }
        return mWidth;
    }

    private int measureHeight(int heightMode,int heightSize){

        switch (heightMode) {

            case MeasureSpec.EXACTLY:
                mHeight=heightSize;
                break;

            case View.MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            default:
                break;
        }
        return mHeight;
    }
}
