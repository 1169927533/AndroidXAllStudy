package com.example.a11699.comp_base.util.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * Toast统一管理类
 *
 * @author athoucai
 */
public class ToastUtil {
    private static boolean DEBUG;

    public static void setDebug(boolean debug) {
        ToastUtil.DEBUG = debug;
    }

    //==================================================================================

    /**
     * 只在debug模式下显示toast
     */
    public static Toast showDebug(Context context, @StringRes int msgStr) {
        if (!ToastUtil.DEBUG) {
            return null;
        }
        return show(context, msgStr, Toast.LENGTH_SHORT);
    }

    /**
     * 只在debug模式下显示toast
     */
    public static Toast showDebug(Context context, CharSequence msg) {
        if (!ToastUtil.DEBUG) {
            return null;
        }
        return show(context, msg, Toast.LENGTH_SHORT);
    }

    //==================================================================================

    /**
     * 长时间显示err Toast
     */
    public static Toast showError(Context context, @StringRes int msgStr) {
        return showLong(context, msgStr);
    }

    /**
     * 长时间显示err Toast
     */
    public static Toast showError(Context context, CharSequence msgStr) {
        return showLong(context, msgStr);
    }

    //==================================================================================

    /**
     * 默认显示的Toast
     */
    public static Toast show(Context context, CharSequence msgStr) {
        return show(context, msgStr, Toast.LENGTH_SHORT);
    }

    /**
     * 默认显示的Toast
     */
    public static Toast show(Context context, @StringRes int msgRes) {
        return show(context, msgRes, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     */
    public static Toast showLong(Context context, CharSequence message) {
        return show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     */
    public static Toast showLong(Context context, @StringRes int msgRes) {
        return show(context, msgRes, Toast.LENGTH_LONG);
    }

    //==================================================================================

    /**
     * 自定义显示Toast text
     */
    public static Toast show(Context context, @StringRes int msgRes, int duration) {
        CharSequence msg = (context == null) ? "" : context.getString(msgRes);
        return show(context, msg, duration);
    }

    public static Toast show(Context context, CharSequence msgStr, int duration) {
        return show(context, msgStr, duration, Gravity.BOTTOM, 0, 0);
    }

    public static Toast show(Context context, CharSequence msgStr, int duration, int gravity, int xOffset, int yOffset) {
        return ToastFactory.getToastCreator().show(context, msgStr, duration, gravity, xOffset, yOffset);
    }

    //==================================================================================

    /**
     * 自定义显示Toast view
     */
    public static Toast showView(Context context, View view) {
        return showView(context, view, Toast.LENGTH_LONG, Gravity.CENTER, 0, 0);
    }

    /**
     * 自定义显示Toast view
     */
    public static Toast showView(Context context, View view, int duration, int gravity, int xOffset, int yOffset) {
        return ToastFactory.getToastCreator().showView(context, view, duration, gravity, xOffset, yOffset);
    }

    //==================================================================================

    /**
     * 自定义显示Toast view
     */
    public static Toast showLayout(Context context, int layoutId) {
        return showLayout(context, layoutId, Toast.LENGTH_LONG, Gravity.CENTER, 0, 0);
    }

    /**
     * 自定义显示Toast view
     */
    public static Toast showLayout(Context context, int layoutId, int duration, int gravity, int xOffset, int yOffset) {
        return ToastFactory.getToastCreator().showLayout(context, layoutId, duration, gravity, xOffset, yOffset);
    }

    //==================================================================================

    /**
     * 自定义显示Toast icon
     */
    public static Toast showIcon(Context context, @DrawableRes int iconRes) {
        return showIcon(context, null, iconRes);
    }

    public static Toast showIcon(Context context, Drawable icon) {
        return showIcon(context, null, icon);
    }

    /**
     * 自定义显示Toast icon
     */
    public static Toast showIcon(Context context, CharSequence msg, @DrawableRes int iconRes) {
        return showIcon(context, msg, ContextCompat.getDrawable(context, iconRes));
    }

    /**
     * 自定义显示Toast icon
     */
    public static Toast showIcon(Context context, CharSequence msg, Drawable icon) {
        return showIcon(context, msg, icon, Gravity.TOP, Toast.LENGTH_LONG);
    }

    public static Toast showIcon(Context context, CharSequence msg, Drawable icon, int gravity, int duration) {
        return ToastFactory.getToastCreator().showIcon(context, msg, icon, gravity, duration);
    }
}
