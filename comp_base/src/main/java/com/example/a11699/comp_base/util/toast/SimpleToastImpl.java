package com.pince.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.pince.toast.builder.IconToastBuilder;
import com.pince.toast.builder.TextToastBuilder;
import com.pince.toast.builder.ViewToastBuilder;

/**
 * @author athoucai
 * @date 2017/9/26
 */

public class SimpleToastImpl implements ToastFactory.ToastCreator {

    @Override
    public Toast show(Context context, CharSequence msgStr, int duration, int gravity, int xOffset, int yOffset) {
        Toast toast = new TextToastBuilder(context)
                .setMsgStr(msgStr)
                .setDuration(duration)
                .setGravity(gravity)
                .setxOffset(xOffset)
                .setyOffset(yOffset)
                .build();
        toast.show();
        return toast;
    }

    @Override
    public Toast showView(Context context, View view, int duration, int gravity, int xOffset, int yOffset) {
        Toast toast = new ViewToastBuilder(context)
                .setView(view)
                .setDuration(duration)
                .setGravity(gravity)
                .setxOffset(xOffset)
                .setyOffset(yOffset)
                .build();
        toast.show();
        return toast;
    }

    @Override
    public Toast showLayout(Context context, int layoutId, int duration, int gravity, int xOffset, int yOffset) {
        Toast toast = new ViewToastBuilder(context)
                .setLayoutId(layoutId)
                .setDuration(duration)
                .setGravity(gravity)
                .setxOffset(xOffset)
                .setyOffset(yOffset)
                .build();
        toast.show();
        return toast;
    }

    @Override
    public Toast showIcon(Context context, CharSequence msg, Drawable icon, int gravity, int duration) {
        Toast toast = new IconToastBuilder(context)
                .setMsgStr(msg)
                .setIcon(icon)
                .setDuration(duration)
                .setGravity(gravity)
                .build();
        toast.show();
        return toast;
    }
}