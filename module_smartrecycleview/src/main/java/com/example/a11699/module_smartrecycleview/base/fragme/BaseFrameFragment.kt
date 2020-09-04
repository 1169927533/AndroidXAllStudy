package com.example.a11699.module_smartrecycleview.base.fragme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
abstract class BaseFrameFragment : Fragment() {


    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(getLayoutId(), container, false)
        return v

    }
    abstract fun showLoading(toShow: Boolean)

}