package com.example.a11699.androidxallstudy.piaodanmu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @Author Yu
 * @Date 2021/6/24 20:13
 * @Description TODO
 */
public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //返回textview是否处在选中的状态
    //而只有选中的textview才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }
}