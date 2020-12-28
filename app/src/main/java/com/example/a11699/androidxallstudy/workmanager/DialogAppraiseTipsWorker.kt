package com.example.a11699.androidxallstudy.workmanager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * @Author Yu
 * @Date 2020/12/25 14:55
 * @Description TODO
 */
class DialogAppraiseTipsWorker(val context: Context, parms: WorkerParameters) : Worker(context,parms) {
    override fun doWork(): Result {
        return Result.success()
    }


    companion object {
        const val TAG = "DialogAppraiseTipsWorker"

        /**
         * 这些都是为我们创建的任务设定的条件。
         * 比如：如下：
         * 然后通过setConstraints()设置进去
         */
        private val triggerContentMaxDelay = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络链接的时候使用,避免各种网络判断,省时省力
                .setRequiresDeviceIdle(false)//是否在设备空闲的时候执行
                .setRequiresBatteryNotLow(true)//是否在低电量的时候执行
                .setRequiresStorageNotLow(true)//是否在内存不足的时候执行
                .setRequiresCharging(true)//是否时充电的时候执行
                .setTriggerContentMaxDelay(1000 * 1, TimeUnit.MILLISECONDS)//延迟执行
                .build()

        val showDialogWorkRequest = OneTimeWorkRequest.Builder(DialogAppraiseTipsWorker::class.java)
                    .addTag(TAG)
                .setInitialDelay(1, TimeUnit.MINUTES)//设置延迟时间
                .setInputData(workDataOf("params_tag" to "params"))////此处set input data 需要的参数 是一个Data对象,注意只可以添加一次,如果有多个参数需要传递,可以封装成一个data 数据类
                .setConstraints(triggerContentMaxDelay)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
                .build()



    }
}