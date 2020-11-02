package com.example.a11699.androidxallstudy

import android.app.Application
import com.example.a11699.lib_im.imloginutil.ImHelper

/**
 *Create time 2020/9/2
 *Create Yu
 *description:
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化im
        ImHelper.initImSdk(this, 1400382026)
        registerActivityLifecycleCallbacks(HookActivityLifecycleCallBacks())
    }
}