package com.example.a11699.androidxallstudy.myseekbar;

import android.app.Application;

/**
 * Create time 2020/7/9
 * Create Yu
 * description:
 */
public class AppCache {
    private static Application context;

    public static Application getContext() {
        return context;
    }

    public static void setContext(Application app) {
        if (app == null) {
            AppCache.context = null;
            return;
        }
        AppCache.context = app;
    }
}
