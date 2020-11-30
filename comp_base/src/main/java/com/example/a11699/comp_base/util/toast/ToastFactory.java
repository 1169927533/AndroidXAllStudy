package com.example.a11699.comp_base.util.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

/**
 *
 * @author athoucai
 * @date 2017/9/26
 */

public final class ToastFactory {

    private static ToastCreator toastCreator = null;

    private ToastFactory() {
    }

    public static void setToastCreator(ToastCreator creator) {
        toastCreator = creator;
    }

    public static ToastCreator getToastCreator() {
        if (toastCreator == null) {
            toastCreator = new SimpleToastImpl();
        }
        return toastCreator;
    }

    /**
     * toast生成器，根据不同需求生成不同toast
     */
    public interface ToastCreator {

        /**
         * 弹出普通消息
         *
         * @param context
         * @param msgStr   消息文本
         * @param duration 时长
         * @param gravity  位置
         * @param xOffset
         * @param yOffset
         * @return
         */
        Toast show(Context context, CharSequence msgStr, int duration, int gravity, int xOffset, int yOffset);

        /**
         * 弹出自定义view
         *
         * @param context
         * @param view     需要展示的view
         * @param duration 时长
         * @param gravity  位置
         * @param xOffset
         * @param yOffset
         * @return
         */
        Toast showView(Context context, View view, int duration, int gravity, int xOffset, int yOffset);

        /**
         * 弹出自定义view
         *
         * @param context
         * @param layoutId
         * @param duration
         * @param gravity
         * @param xOffset
         * @param yOffset
         * @return
         */
        Toast showLayout(Context context, int layoutId, int duration, int gravity, int xOffset, int yOffset);

        /**
         * 弹出带图标的文本消息
         *
         * @param context
         * @param msg     消息文本
         * @param icon    提示的图标
         * @param gravity 图标的位置（left, top, right, bottom）
         * @return
         */
        Toast showIcon(Context context, CharSequence msg, Drawable icon, int gravity, int duration);
    }
}
