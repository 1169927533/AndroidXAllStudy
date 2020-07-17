package com.example.a11699.androidxallstudy.xiecheng

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.*
import java.util.logging.Logger

/**
 *Create time 2020/7/15
 *Create Yu
 *description:
 */
suspend fun main() {
    val job:Job = GlobalScope.launch(start = CoroutineStart.DEFAULT) {

        delay(1000)

    }
    delay(1000)
/*
    job.start()
    job.cancel()
    job.join()
    job.isActive
    job.isCancelled
    job.isCompleted*/
}