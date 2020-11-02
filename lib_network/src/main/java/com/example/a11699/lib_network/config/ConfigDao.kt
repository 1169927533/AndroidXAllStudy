package com.example.a11699.lib_network.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *Create time 2020/10/29
 *Create Yu
 *description:网络请求的基础配置
 */
interface ConfigDao {
    fun initClient(): OkHttpClient
    fun initRetrofitConfig(): Retrofit.Builder
    fun initBaseUrl(): String
}