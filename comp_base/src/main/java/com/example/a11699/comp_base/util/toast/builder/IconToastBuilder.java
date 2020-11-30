package com.example.a11699.comp_base.util.toast.builder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author athoucai
 * @date 2017/12/4
 */

public class IconToastBuilder extends ToastBuilder {

    private CharSequence msg;
    private Drawable icon;

    public IconToastBuilder(Context context) {
        super(context);
    }

    public IconToastBuilder setMsgStr(CharSequence msg) {
        this.msg = msg;
        return this;
    }

    public IconToastBuilder setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public Toast build() {
        Toast iconToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        iconToast.setGravity(Gravity.CENTER, xOffset, yOffset);
        LinearLayout toastView = (LinearLayout) iconToast.getView();
        TextView tv = (TextView) toastView.getChildAt(0);
        tv.setText(msg);
        if ((gravity & Gravity.LEFT) != 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        } else if ((gravity & Gravity.TOP) != 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        } else if ((gravity & Gravity.RIGHT) != 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        } else if ((gravity & Gravity.BOTTOM) != 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, icon);
        }
        tv.setGravity(Gravity.CENTER);
        iconToast.setDuration(duration);
        return iconToast;
    }
}
