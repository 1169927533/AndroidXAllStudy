package com.example.a11699.lib_network.config

import android.util.Log
import com.example.a11699.lib_network.util.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *Create time 2020/10/29
 *Create Yu
 *description：网络请求基础配置
 */
open class BaseConfig : ConfigDao {

    override fun initClient(): OkHttpClient {
        var okHttpClient = OkHttpClient.Builder()
        //打印网络请求相关日志
        var loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("Zjc", message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    override fun initBaseUrl(): String {
        return "https://sdsy.zzjc.edu.cn"
    }

    override fun initRetrofitConfig(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(initBaseUrl())
                .client(initClient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
    }


}