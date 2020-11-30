package com.example.a11699.comp_base.util.toast.builder;

import android.content.Context;
import android.widget.Toast;

/**
 * @author athoucai
 * @date 2017/12/4
 */

public abstract class ToastBuilder {
    protected Context context;
    protected int duration;
    protected int gravity;
    protected int xOffset;
    protected int yOffset;

    public ToastBuilder(Context context) {
        this.context = context;
    }

    public ToastBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public ToastBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public ToastBuilder setxOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public ToastBuilder setyOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public abstract Toast build();
}
