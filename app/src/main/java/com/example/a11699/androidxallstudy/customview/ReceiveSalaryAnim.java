package com.example.a11699.androidxallstudy.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.a11699.androidxallstudy.R;



/**
 * Create time 2020/7/11
 * Create Yu
 * description:
 */
public class ReceiveSalaryAnim extends View {
    private static final String TAG = ReceiveSalaryAnim.class.getSimpleName();

    int[] location = new int[2];
    private Path mPath = new Path();
    private PathMeasure mPm;
    private int mPointPos = 0; // 小球运动的位置
    private static final int STATE_STATIC = 0; // 处于静止状态
    private static final int STATE_MOVING = 1;
    private int mDirection = STATE_STATIC;
    private DisplayMetrics dm;
    private Drawable mBall;
    private Rect mBallRect;
    private static final int INIT_HEIGHT = 60;
    private int mLineEndPosY;
    private Paint mPaint = new Paint();

    public ReceiveSalaryAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        dm = context.getResources().getDisplayMetrics();
        mBall = context.getResources().getDrawable(R.drawable.barrage_bg);
        mBallRect = new Rect();
    }

    private void receiveSalary() {
        if (mDirection != STATE_STATIC) {
            return;
        }
        mDirection = STATE_MOVING;
        setClickable(false);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float offset = INIT_HEIGHT * dm.density - mBall.getIntrinsicHeight() / 2;
        getLocationOnScreen(location);
        mPath.moveTo(location[0], location[1] + offset);
        mPath.cubicTo(location[0], location[1] + offset + (offset / 4), location[0], location[1] + offset
                + (offset * 4), location[0], location[1] + offset);
        mPm = new PathMeasure(mPath, false);
        mPm.getPosTan(mPm.getLength() - 1, pos, tan);
        mLineEndPosY = (int) (pos[1]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        if (mDirection != STATE_STATIC) {
            return super.onTouchEvent(arg0);
        }
        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            // getLocationInWindow(location);
            setBallRect();
            final int x = (int) arg0.getX();
            final int y = (int) arg0.getY();
            if (mBallRect.contains(x, y)) {
                receiveSalary();
                return super.onTouchEvent(arg0);
            }
        }
        return super.onTouchEvent(arg0);
    }

    private void setBallRect() {
        mBallRect.set(location[0], mLineEndPosY - mBall.getIntrinsicHeight() / 2,
                location[0] + mBall.getIntrinsicWidth(), mLineEndPosY + mBall.getIntrinsicHeight() / 2);
    }

    float[] pos = new float[2];
    float[] tan = new float[2];

    // 将均匀点映射到正弦曲线，获得由0加速，然后回弹减速到0的效果
    private float getAccelerationPoint(float len, float point) {
        return (float) (len * (Math.sin((2 * point - len) * Math.PI / (2 * len)) / 2 + 0.5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lineStartX = location[0];
        float lineStartY = location[1];
        float lineEndX = lineStartX;
        float lineEndY = mLineEndPosY;
        // 取 drawable 的长宽
        int w = mBall.getIntrinsicWidth();
        int h = mBall.getIntrinsicHeight();
        if (mDirection != STATE_STATIC && mPointPos < mPm.getLength()) {
            // getAccelerationPoint(mPm.getLength(), mPointPos);
            mPm.getPosTan(getAccelerationPoint(mPm.getLength(), mPointPos), pos, tan);
            lineEndY = pos[1];
        } else {
            if (mPointPos > 0) {
                mPm.getPosTan(mPm.getLength() - 1, pos, tan);
                lineEndY = pos[1];
            }
            setClickable(true);
            mDirection = STATE_STATIC;
            mPointPos = 0;
            setBallRect();
        }

        // draw line from location[1] to lineEndY, draw ball from
        // lineEndY-ball's height
        canvas.save();
        mPaint.setColor(0xffff0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        canvas.drawLine(lineStartX + w / 2, lineStartY, lineEndX + w / 2, lineEndY, mPaint);
        canvas.restore();

        canvas.save();

        // 取 drawable 的颜色格式
        Bitmap.Config config = mBall.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        // Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        mBall.setBounds((int) lineEndX, (int) lineEndY - h / 2, (int) lineEndX + w, (int) lineEndY + h / 2);
        // 把 drawable 内容画到画布中
        mBall.draw(canvas);
        canvas.restore();
        if (mDirection != STATE_STATIC && mPointPos < mPm.getLength()) {
            mPointPos += 8;
            invalidate();
        }
    }
}
