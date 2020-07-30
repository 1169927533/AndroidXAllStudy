package com.example.a11699.comp_live.manager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.a11699.comp_base.Util
import com.example.a11699.comp_live.listener.OnViewPagerListener

/**
 *Create time 2020/7/30
 *Create Yu
 *description:
 */
class PagerLayoutManager : LinearLayoutManager {
    lateinit var viewGroup: ViewGroup
    lateinit var mRecycleview: RecyclerView
    lateinit var context: Context
    lateinit var onViewChangListener: OnViewPagerListener
    var currentPosition = 0
    fun setViewGroup(rootView: ViewGroup?, listener: IreloadInterface?) {
        var isInit = false
        onViewChangListener = object : OnViewPagerListener {
            override fun onInitComplete(view: ViewGroup) {
                view?.let {
                    if (rootView != null) {
                        if (rootView.parent != null) {
                            var pa = rootView.parent as ViewGroup
                            pa?.let { itt ->
                                itt.removeView(rootView)
                            }
                        }
                    }
                    it.addView(rootView)
                    if (listener != null && !isInit) {
                        listener.onReloadPage(-1000, false, viewGroup)
                    }
                    isInit = true
                }
            }

            override fun onPageRelease(view: ViewGroup) {
                view.removeView(rootView)
            }

            override fun onPageSelected(position: Int, isBottom: Boolean, view: ViewGroup) {
                if (rootView != null) {
                    if (rootView.parent != null) {
                        var pa = rootView.parent as ViewGroup
                        pa?.let {
                            it.removeView(rootView)
                        }
                    }
                }
                view.addView(rootView)
                listener?.onReloadPage(realPosition(position), isBottom, viewGroup)
            }
        }
    }

    private fun realPosition(position: Int): Int {
        val baseQuickAdapter =
                mRecycleview.adapter as BaseQuickAdapter<*, *>?
        val size = baseQuickAdapter!!.data.size
        return if (position >= size) position % size else position
    }

    private val snapHelper by lazy {
        PagerSnapHelper()
    }

    constructor(context: Context) : super(context) {
        init(context)
    }


    private fun init(context: Context) {
        this.context = context
    }


    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        snapHelper.attachToRecyclerView(view)
        view?.let {
            mRecycleview = view
            mRecycleview.addOnChildAttachStateChangeListener(mChildAttachStateChangeLister)
            /* mRecycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                 override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                     super.onScrollStateChanged(recyclerView, newState)

                 }

                 override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                     super.onScrolled(recyclerView, dx, dy)
                 }
             })*/
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                onViewChangListener?.let {
                    var view = snapHelper.findSnapView(this) as ViewGroup
                    var position = view?.let { it1 -> getPosition(it1) }
                    if (currentPosition != position) {
                        currentPosition = position
                        it.onPageSelected(position!!, position == itemCount - 1, view)
                    }
                }
            }
        }
    }


    private var mChildAttachStateChangeLister = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            Util.printLog("onChildViewDetachedFromWindow")

            onViewChangListener?.let {
                it.onPageRelease(view as ViewGroup)
            }
        }

        override fun onChildViewAttachedToWindow(view: View) {
            Util.printLog("onChildViewAttachedToWindow")
            viewGroup = view as ViewGroup
            if (childCount == 1) {
                onViewChangListener?.let {
                    it.onInitComplete(view as ViewGroup)
                }
            }
        }
    }

    interface IreloadInterface {
        /**
         * 重载界面
         *
         * @param position 当页面的游标
         * @param isBottom 是否到底
         * @param view     ViewGroup
         */
        fun onReloadPage(position: Int, isBottom: Boolean, view: View?)

        /**
         * 销毁界面
         *
         * @param isNext   是否有下一个
         * @param position 页面的游标
         * @param view     ViewGroup
         */
        fun onDestroyPage(isNext: Boolean, position: Int, view: View?)
    }
}
