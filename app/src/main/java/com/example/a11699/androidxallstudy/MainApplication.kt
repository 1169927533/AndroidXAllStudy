package com.example.a11699.androidxallstudy

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.a11699.lib_im.imloginutil.ImHelper
import com.example.lib_crash.CrashHandler
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.OkHttpClient

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
        var config = OkHttpImagePipelineConfigFactory
                .newBuilder(this,  OkHttpClient()).build()

        Fresco.initialize(applicationContext,config);
        //初始化异常捕获 他会把么每次奔溃信息进行一个本地保存
        CrashHandler.getInstance().init(this)
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }
}