package com.example.a11699.comp_netstudyt.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import com.example.a11699.comp_netstudyt.Api.RetrofitBuild

/**
 *Create time 2020/7/22
 *Create Yu
 *description:
 */
open class BaseViewModel : AndroidViewModel {
    var mData: Bundle? = null
        private set

    constructor(application: Application) : super(application)
    constructor(application: Application, data: Bundle?) : super(application) {
        mData = data
    }

    fun <T> getRepo(cls: Class<T>?): T {

        return RetrofitBuild.initConfig().create(cls)
    }
}