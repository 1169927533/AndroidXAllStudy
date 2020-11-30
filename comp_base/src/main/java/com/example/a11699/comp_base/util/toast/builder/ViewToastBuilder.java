package com.example.a11699.comp_base.util.toast.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * @author athoucai
 * @date 2017/12/4
 */

public class ViewToastBuilder extends ToastBuilder {

    private View view;

    private int layoutId;

    public ViewToastBuilder(Context context) {
        super(context);
    }

    public ViewToastBuilder setView(View view) {
        this.view = view;
        return this;
    }

    public ViewToastBuilder setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    @Override
    public Toast build() {
        Toast viewToast = Toast.makeText(context, "", duration);
        View toastView = null;
        if (view != null) {
            toastView = view;
        } else if (layoutId > 0) {
            toastView = LayoutInflater.from(context).inflate(layoutId, null);
        } else {
            throw new IllegalArgumentException("view is null or layoutid is illegal");
        }
        viewToast.setView(toastView);
        viewToast.setGravity(gravity, xOffset, yOffset);
        viewToast.setDuration(duration);
        return viewToast;
    }
}
