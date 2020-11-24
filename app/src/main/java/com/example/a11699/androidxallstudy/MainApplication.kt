package com.example.a11699.androidxallstudy

import android.app.Application
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
    }
}