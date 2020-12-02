package com.example.a11699.comp_base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *Create time 2020/9/11
 *Create Yu
 *description:基类 做了状态栏适配方案
 */
public abstract class BaseFragment : Fragment() {
    var mContentView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContentView = inflater.inflate(getLayoutId(), container, false)
        return mContentView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeLiveData()
        initViewData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun observeLiveData() //订阅vm

    abstract fun initViewData()

    /**
     * 创建containerview
     *
     * @param inflater
     * @param layoutResID 你自己的布局id
     * @return
     */
    open fun createContainerView(inflater: LayoutInflater, layoutResID: Int): View? {
        return if (layoutResID <= 0) {
            null
        } else inflater.inflate(layoutResID, null)
    }



}