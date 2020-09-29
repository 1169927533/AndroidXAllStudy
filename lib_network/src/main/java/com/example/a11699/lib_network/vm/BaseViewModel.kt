package com.example.a11699.lib_network.vm

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a11699.lib_network.util.RetrofitBuild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

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

    fun launch(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(Throwable) -> Unit = {},
            complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                error.invoke(this, e)
            } finally {
                complete.invoke(this)
            }
        }
    }

}