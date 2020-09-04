package com.example.a11699.module_smartrecycleview.base.fragme

import android.os.Bundle

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
abstract class BaseVmFragment: BaseFrameFragment()  {



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
        initViewData()
    }


    abstract fun observeLiveData()


    abstract fun initViewData()


}