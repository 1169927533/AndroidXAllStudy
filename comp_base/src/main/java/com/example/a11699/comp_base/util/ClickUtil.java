package com.example.a11699.comp_base.util;

/**
 * @Author Yu
 * @Date 2020/11/10 15:04
 * @Description TODO
 */
public class ClickUtil {
    private static long lastClickTime = 0L;

    public ClickUtil() {
    }

    public static boolean isClickAvalible() {
        return isClickAvalible(500L);
    }

    public static boolean isClickAvalible(long miniIntervalMills) {
        if (System.currentTimeMillis() - lastClickTime > miniIntervalMills) {
            lastClickTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
}