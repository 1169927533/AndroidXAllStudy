package com.example.a11699.androidxallstudy

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup

/**
 *Create time 2020/10/30
 *Create Yu
 *description:
 */
class HookActivityLifecycleCallBacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i("zjc", "${activity.javaClass.name}在HookActivityLifecycleCallBacks执行了onActivityCreated方法")
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        if(!activityNameSet.contains(activity.javaClass.name)){
            val viewGroup = activity.window.decorView as ViewGroup
            if(viewGroup!=null){
                val size = viewGroup.childCount
                val cusTomeFrameLayout = MyViewGroup(activity)
                for (i in 0 until size) {
                    val view = viewGroup.getChildAt(i)
                    if (view != null) {
                        viewGroup.removeView(view)
                        cusTomeFrameLayout.addView(view)
                    }
                }
                viewGroup.addView(cusTomeFrameLayout)
            }
            activityNameSet.add(activity.javaClass.name)
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
    companion object {
        var activityNameSet: MutableSet<String> = HashSet()
    }
}