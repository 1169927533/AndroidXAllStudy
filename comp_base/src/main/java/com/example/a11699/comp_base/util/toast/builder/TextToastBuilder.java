package com.example.a11699.comp_base.util.toast.builder;

import android.content.Context;
import android.widget.Toast;

/**
 * @author athoucai
 * @date 2017/12/4
 */

public class TextToastBuilder extends ToastBuilder {
    private CharSequence msgStr;

    public TextToastBuilder(Context context) {
        super(context);
    }

    public TextToastBuilder setMsgStr(CharSequence msgStr) {
        this.msgStr = msgStr;
        return this;
    }

    @Override
    public Toast build() {
        Toast toast = Toast.makeText(context, msgStr, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setDuration(duration);
        return toast;
    }
}
