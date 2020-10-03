package com.example.a11699.androidxallstudy.breath;

/**
 * Create time 2020/10/3
 * Create Yu
 * description:
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.a11699.androidxallstudy.R;

/**
 *Create time 2020/10/3
 *Create Yu
 *description:光炫效果
 * 方案2 ： 绘制渐变色的方式 不好的一点，渐变结束的颜色必须和整个view的背景色保持一样
 */
public class BreatheView extends View {
    private int mInsideViewWidth = dp2px(getContext(), 70);
    private int mInsideViewHeight = dp2px(getContext(), 40);
    private int mLightDiffusionWidth = dp2px(getContext(), 20);  //扩散区宽度
    private int mTextColor = Color.parseColor("#ffffff"); //默认字体颜色为白色
    private int mTextSize = sp2px(getContext(), 22);
    private int mInsideColor = Color.parseColor("#9FB6FF");//内部图形颜色，为蓝色
    private boolean isLight = false; //控制是否开启动画

    private int[] colors = {Color.parseColor("#9FB6FF"), Color.parseColor("#A7BAFE"), Color.parseColor("#F26882"), Color.parseColor("#F8DF57")};//变动颜色

    private int mOutsideColor;
    private Paint mTextPaint;
    private Paint mInsideViewPaint;
    private Paint mOutsideRectPaint;
    private float mOutsideRectHeight;
    private Paint mOutsideCirclePaint;

    private int mRealWidth;
    private int mRealHeight;
    private LinearGradient mLinearGradient;
    private RadialGradient mRadialGradient;
    private String mBreatheViewText;
    private ValueAnimator mGoLightAnim;
    private ValueAnimator mBackLightAnim;
    private ValueAnimator mColorAnim;
    private int mColor;

    private int mEndGraidentColor = Color.parseColor("#03DAC5");   //这个就是我们的结束颜色

    public BreatheView(Context context) {
        this(context, null);
    }

    public BreatheView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BreatheView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(attrs);
        initPaint();
    }

    /**
     * 步骤一：根据xml属性，设置控件的属性
     *
     * @param attrs
     */
    private void obtainAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BreatheView);
        mInsideViewWidth = typedArray.getDimensionPixelSize(R.styleable.BreatheView_insideViewWidth, mInsideViewWidth);
        mInsideViewHeight = typedArray.getDimensionPixelSize(R.styleable.BreatheView_insideViewHeight, mInsideViewHeight);
        mLightDiffusionWidth = (int) typedArray.getDimension(R.styleable.BreatheView_lightDiffusionWidth, mLightDiffusionWidth);

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.BreatheView_BreatheViewTextSize, mTextSize);
        mTextColor = typedArray.getColor(R.styleable.BreatheView_BreatheViewTextColor, mTextColor);
        //控件文字
        mBreatheViewText = typedArray.getString(R.styleable.BreatheView_BreatheViewText);
        //内部颜色
        mInsideColor = typedArray.getColor(R.styleable.BreatheView_BreatheViewInsideColor, mInsideColor);
        //是否开启灯光扩散，在xml中设置
        isLight = typedArray.getBoolean(R.styleable.BreatheView_BreatheViewIsLight, isLight);
        typedArray.recycle();//回收资源
    }

    /**
     * 步骤二：初始化绘制控件的笔触
     */
    private void initPaint() {
        //文本文字
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        //内部的图形
        mInsideViewPaint = new Paint();
        mInsideViewPaint.setStyle(Paint.Style.FILL);
        mInsideViewPaint.setAntiAlias(true);
        mInsideViewPaint.setColor(mInsideColor);


        //外部方区域图形
        mOutsideColor = mInsideColor;
        mOutsideRectPaint = new Paint();
        mOutsideRectHeight = mInsideViewHeight;
        mLinearGradient = new LinearGradient(0, 0, 0, mOutsideRectHeight,
                new int[]{Color.WHITE, mOutsideColor, Color.WHITE}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        mOutsideRectPaint.setShader(mLinearGradient);
        mOutsideRectPaint.setStyle(Paint.Style.FILL);
        mOutsideRectPaint.setAntiAlias(true);
        mOutsideRectPaint.setColor(mOutsideColor);


        //外部的圆形区域
        mOutsideCirclePaint = new Paint();
        mOutsideCirclePaint.setStyle(Paint.Style.FILL);
        mOutsideCirclePaint.setAntiAlias(true);
        mOutsideCirclePaint.setColor(mOutsideColor);
        mRadialGradient = new RadialGradient(mOutsideRectHeight / 2, mOutsideRectHeight / 2,
                mOutsideRectHeight / 2, mOutsideColor, Color.WHITE, Shader.TileMode.CLAMP);
        mOutsideCirclePaint.setShader(mRadialGradient);

    }

    /**
     * 第三步，测试控件大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getPaddingBottom() + getPaddingTop() + Math.abs(mLightDiffusionWidth * 2) + mInsideViewHeight;
        int width = getPaddingLeft() + getPaddingRight() + mInsideViewWidth + mInsideViewHeight / 2 + mLightDiffusionWidth;
        mRealWidth = resolveSize(width, widthMeasureSpec);
        mRealHeight = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(mRealWidth, mRealHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 第四步，绘制控件
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //变色
        mInsideViewPaint.setColor(mInsideColor);
        mInsideViewPaint.setAlpha(150);

        canvas.translate(mInsideViewHeight / 2 + mLightDiffusionWidth + getPaddingLeft(), mRealHeight >> 1);
        canvas.save();//保存图层

        if (isLight) {
            mOutsideCirclePaint.setColor(mOutsideColor);
            mOutsideRectPaint.setColor(mOutsideColor);
            mOutsideCirclePaint.setAlpha(150);
            mOutsideRectPaint.setAlpha(150);
            //绘制外部的长方形
            canvas.translate(0, -mOutsideRectHeight / 2);
            canvas.drawRect(0, 0, mInsideViewWidth, mOutsideRectHeight, mOutsideRectPaint);
            //绘制外部的半圆
            canvas.translate(-mOutsideRectHeight / 2, 0);
            canvas.drawArc(0, 0, (int) mOutsideRectHeight, (int) mOutsideRectHeight, 90, 180, true, mOutsideCirclePaint);
            canvas.restore();
            canvas.save();
        }

        //绘制内部的长方形与半圆
        canvas.translate(0, -(mInsideViewHeight >> 1));
        canvas.drawRect(0, 0, mInsideViewWidth, mInsideViewHeight, mInsideViewPaint);
        canvas.translate(-(mInsideViewHeight / 2), 0);
        canvas.drawArc(0, 0, mInsideViewHeight, mInsideViewHeight, 90, 180, true, mInsideViewPaint);
        canvas.restore();
        canvas.save();
        canvas.translate(0, -(mInsideViewHeight >> 1));

        //文字的x轴坐标
        float stringWidth = mTextPaint.measureText(mBreatheViewText);
        float x = (mInsideViewWidth - stringWidth) / 2;
        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = (float) mInsideViewHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(mBreatheViewText, x, y, mTextPaint);
        canvas.restore();
    }

    /**
     * 这边是设置动画，动画分成两个部分，第一部分，是扩散出去，第二部分是扩散回来，同时颜色渐变
     */
    public void startLightAnim() {
        mGoLightAnim = ValueAnimator.ofFloat(0f, mLightDiffusionWidth);
        mGoLightAnim.setDuration(1500);
        //动态开始设置扩散出去动画
        mGoLightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //// TODO: 动画里面里面不应该不停的new对象，对内存会造成影响，,需要优化
                mOutsideRectHeight = (int) (mInsideViewHeight + (float) animation.getAnimatedValue() * 2);
                setGradient();
                invalidate();
            }
        });
        mGoLightAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isLight) {
                    return;
                }
                mGoLightAnim.setStartDelay(1500);
                mGoLightAnim.start();

            }
        });

        //扩散回来的颜色动画
        mBackLightAnim = ValueAnimator.ofFloat(mLightDiffusionWidth, 0f);
        mBackLightAnim.setDuration(1500);
        mBackLightAnim.setStartDelay(1500);
        mBackLightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOutsideRectHeight = (int) (mInsideViewHeight + (float) animation.getAnimatedValue() * 2);
                setGradient();
                invalidate();
            }
        });
        mBackLightAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isLight) {
                    return;
                }
                mBackLightAnim.setDuration(1500);
                mBackLightAnim.start();


            }
        });

        //第二部分颜色渐变动画
        int i = (int) (Math.random() * 3);
        mColor = colors[i];
        mColorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), mInsideColor, mColor);
        mColorAnim.setDuration(1500);
        mColorAnim.setStartDelay(1500);
        mColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mInsideColor = ((Integer) animation.getAnimatedValue());
                mOutsideColor = mInsideColor;
                invalidate();
            }
        });
        mColorAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isLight) {
                    return;
                }

                int i = (int) (Math.random() * 3);
                mColor = colors[i];
                Log.e("TAG", i + "");
                mColorAnim.setObjectValues(mInsideColor, mColor);
                mColorAnim.setStartDelay(1500);
                mColorAnim.start();
            }
        });

        mGoLightAnim.start();
        mBackLightAnim.start();
        mColorAnim.start();
        isLight = true;
    }

    /**
     * 重新设置Gradient
     */
    private void setGradient() {
        mLinearGradient = new LinearGradient(0, 0, 0, mOutsideRectHeight,
                new int[]{mEndGraidentColor, mOutsideColor,mEndGraidentColor}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        mOutsideRectPaint.setShader(mLinearGradient);
        mRadialGradient = new RadialGradient(mOutsideRectHeight / 2, mOutsideRectHeight / 2,
                mOutsideRectHeight / 2, mOutsideColor, mEndGraidentColor, Shader.TileMode.CLAMP);
        mOutsideCirclePaint.setShader(mRadialGradient);
    }

    /**
     * 设置渐变底色
     * @param endGraidentColor
     */
    public void setEndGraidentColor(int endGraidentColor) {
        mEndGraidentColor = endGraidentColor;
    }

    /**
     * 关闭动画
     */
    public void cancelLightAnim() {
        isLight = false;//关闭动画
        if (mGoLightAnim != null) {
            mGoLightAnim.cancel();
        }
        if (mBackLightAnim != null) {
            mBackLightAnim.cancel();
        }
        if (mColorAnim != null) {
            mColorAnim.cancel();
        }
        mOutsideRectHeight = mInsideViewHeight;
        mInsideColor = mColor;
        mOutsideColor = mColor;
        invalidate();
    }

    /**
     * dp转换成px，scale为像素密度，density越高，分辨率越高
     *
     * @param context 获取屏幕
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, int dpVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    /**
     * sp转换成px，fontSCale为文字像素密度，fontScale越高，分辨率越高
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, int spVal) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }
}

