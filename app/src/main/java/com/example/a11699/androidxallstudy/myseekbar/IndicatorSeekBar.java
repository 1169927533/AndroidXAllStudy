package com.example.a11699.androidxallstudy.myseekbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.a11699.androidxallstudy.R;

/**
 * Create time 2020/7/9
 * Create Yu
 * description:
 */
public class IndicatorSeekBar extends LinearLayout {

    TextView thumb;
    private View viewFirstColor;
    private int thumbWidth = ViewUtil.dip2px(34);


    public IndicatorSeekBar(Context context) {
        this(context, null);
    }

    public IndicatorSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private boolean isTrack = false;

    private void initView(Context context) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_indicator_seekbar, this);
        thumb = (TextView) findViewById(R.id.isb_progress);
        viewFirstColor = findViewById(R.id.viewFirstColor);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        if (startX < thumb.getRight() + thumb.getWidth() * 3 && startX > thumb.getLeft() - thumb.getWidth() * 3) {
                            isTrack = true;
                        } else {
                            isTrack = false;
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getX() - startX;

                        if (isTrack && dx != 0) {
                            Log.d("IndicatorSeekBar", "dx  : " + dx + "   getWidth() ：" + getWidth() + "       -----dx/getWidth() :" + dx / getWidth() + " (dx/getWidth()*pross ) : " + (dx / getWidth() * 100));
                            float prossChange = (pross + (dx / getWidth() * 100));
                            if (prossChange > 100) {
                                prossChange = 100;
                            }
                            if (prossChange < 0) {
                                prossChange = 0;
                            }
                            updateTextview(prossChange);
                            if (listener != null) {
                                listener.onChange((int) prossChange);
                            }
                            startX = event.getX();
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }

                return true;
            }
        });
        updateTextview(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private float pross = 0;

    public void updateTextview(final float progress) {
        Log.d("IndicatorSeekBar", "updateTextview  : " + progress);
        if (getWidth() <= 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    setPross(progress);
                }
            });
        } else {
            setPross(progress);
        }
    }

    private void setPross(float progress) {
        pross = progress;
        int w = getWidth();
        float firstW = (w * (pross / 100f));
        float thumbMagin = ((w - thumbWidth) * (pross / 100f));

        FrameLayout.LayoutParams lpThumb = (FrameLayout.LayoutParams) thumb.getLayoutParams();
        FrameLayout.LayoutParams lpFirst = (FrameLayout.LayoutParams) viewFirstColor.getLayoutParams();
        lpFirst.width = (int) firstW;
        lpThumb.leftMargin = (int) thumbMagin;
        thumb.setLayoutParams(lpThumb);
        viewFirstColor.setLayoutParams(lpFirst);
        thumb.setText(String.valueOf((int) progress));
        requestLayout();
    }

    private float startX = 0;

    ProgressChangeListener listener;

    public static interface ProgressChangeListener {
        public void onChange(int progress);
    }

    public void setOnSeekBarChangeListener(ProgressChangeListener listener) {
        this.listener = listener;
    }


}

