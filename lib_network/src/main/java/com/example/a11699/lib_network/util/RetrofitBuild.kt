package com.example.a11699.lib_network.util

import android.util.Log

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *Create time 2020/7/21
 *Create Yu
 *description:
 */
object RetrofitBuild {
    fun initConfig(): Retrofit {
        //打印网络请求相关日志
        var loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("Zjc", message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        var okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        var retrofit = Retrofit.Builder().baseUrl("https://sdsy.zzjc.edu.cn")
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }
}