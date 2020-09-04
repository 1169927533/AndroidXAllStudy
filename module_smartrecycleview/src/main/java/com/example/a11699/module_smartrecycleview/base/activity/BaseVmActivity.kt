package com.example.a11699.module_smartrecycleview.base.activity

import com.example.a11699.module_smartrecycleview.base.activity.BaseFrameActivity

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
abstract class BaseVmActivity : BaseFrameActivity() {
    override fun init() {
        observeLiveData()
        initViewData()
    }

    /**
     * 订阅vm
     */
    abstract fun observeLiveData()

    abstract fun initViewData()

}