package com.hipi.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifeCircleCallBack<T> : LifecycleObserver {
    //(val lifecycle: Lifecycle,val successCall:(t:T)->Unit ,errorCall :(e: Throwable)->Unit )

    private var isOnDestroy = false
    private var lifecycle: Lifecycle
    private var successCall: ((t: T) -> Unit)
    private var errorCall: ((e: Throwable) -> Unit)?

    constructor(lifecycle: Lifecycle, successCall: (t: T) -> Unit) : this(
        lifecycle,
        successCall,
        null
    )

    constructor(
        lifecycle: Lifecycle,
        successCall: (t: T) -> Unit,
        errorCall: ((e: Throwable) -> Unit)?
    ) {
        this.lifecycle = lifecycle
        this.successCall = successCall
        this.errorCall = errorCall
        lifecycle.addObserver(this)
    }

    fun onSuccess(t: T) {
        if (!isOnDestroy) {
            successCall.invoke(t)
        }
    }


    fun onError(e: Throwable) {
        if (!isOnDestroy) {
            errorCall?.invoke(e)
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle.removeObserver(this)
        isOnDestroy = true
    }
}