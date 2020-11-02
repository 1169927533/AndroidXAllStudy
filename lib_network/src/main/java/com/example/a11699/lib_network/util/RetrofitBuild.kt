package com.example.a11699.lib_network.util

import com.example.a11699.lib_network.config.BaseConfig
import com.example.a11699.lib_network.config.ConfigDao
import retrofit2.Retrofit

/**
 *Create time 2020/7/21
 *Create Yu
 *description:
 */
object RetrofitBuild {
    lateinit var retrofit: Retrofit
    private var mDefaultConfig: Class<out ConfigDao?>? = null

    init {
        BaseConfig().apply {
            retrofit = initRetrofitConfig().build()
        }
    }

    fun rectConfig(clientConfig: Class<out ConfigDao?>?) {
        retrofit = clientConfig?.newInstance()!!.initRetrofitConfig().build()
    }
}