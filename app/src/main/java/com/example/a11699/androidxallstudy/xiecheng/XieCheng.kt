package com.example.a11699.androidxallstudy.xiecheng

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *Create time 2020/7/9
 *Create Yu
 *description:
 */
class XieCheng {
    fun testOne() {
        test()
    }

    //启动的协程任务会阻断当前线程，直到该协程执行结束。当协程执行结束之后，页面才会被显示出来。
    fun test() = runBlocking {
        repeat(8) {
            Log.e("TAG", "协程执行$it 线程id：${Thread.currentThread().id}")
            delay(1000)
        }
    }

    //launch不会阻断主线程。
    fun startXie_Two() {
        val job = GlobalScope.launch {
            delay(100)
            Log.e("TAG", "协程执行结束 -- 线程id：${Thread.currentThread().id}")
        }
    }
}