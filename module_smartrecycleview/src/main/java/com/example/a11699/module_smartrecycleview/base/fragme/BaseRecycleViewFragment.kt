package com.example.a11699.module_smartrecycleview.base.fragme

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.module_smartrecycleview.R
import com.example.a11699.module_smartrecycleview.util.SmartRefreshUtil

import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_base_recycleview.*
import kotlinx.android.synthetic.main.layout_emptyview.*

/**
 *Create time 2020/8/19
 *Create Yu
 *description:
 *  帮助处理上拉刷新 和 下拉加载
 *  结合ViewPager实现懒加载
 */
abstract class BaseRecycleViewFragment<T> : BaseVmFragment() {
    var viewGroupItemClick: (() -> Unit)? = null//外部view被点击

    //无数据展示效果
    open val emptyView: View by lazy {
        LayoutInflater.from(requireContext()).inflate(R.layout.layout_emptyview, null)
    }

    open var isDoWorkInResume: Boolean = false //是否要在每次onResume的时候取请求数据
    open var isBeenGetData = false //是否已经加载了数据

    lateinit var smartRefreshUtil: SmartRefreshUtil<T>
    abstract val baseAdapter: BaseQuickAdapter<T, BaseViewHolder>
    abstract val onFetchListener: ((page: Int) -> Unit)
    abstract val layoutManager: RecyclerView.LayoutManager
    open val isShouldNeedLoadMore: Boolean = true
    open val isShouldNeedRefresh: Boolean = true
    lateinit var recycleview: RecyclerView

    override fun getLayoutId(): Int {
        return R.layout.activity_base_recycleview
    }

    override fun initViewData() {
        frameLayout.addView(emptyView, 0)
        initRecycleviewLayout()
    }

    var gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            viewGroupItemClick?.let {
                it.invoke()
            }
            return false

        }

        override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
        ): Boolean {
            return false

        }

        override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }
    })

    open fun setRecycleViewItemDirection() {

    }

    private fun initRecycleviewLayout() {
        //修改阻尼效果（0 - 1），越小阻尼越大，默认0.5
        smartRefreshLayout_fans.setDragRate(0.6f);
        smartRefreshLayout_fans.setRefreshHeader(ClassicsHeader(requireContext()))
        recycleview = recycleview_fans
        recycleview.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
        recycleview_fans.layoutManager = layoutManager
        recycleview_fans.adapter = baseAdapter
        smartRefreshUtil = SmartRefreshUtil(
                baseAdapter, recycleview_fans, smartRefreshLayout_fans,
                isShouldNeedLoadMore, isShouldNeedRefresh, 1, onFetchListener
        )
        setRecycleViewItemDirection()

    }


    override fun onResume() {
        super.onResume()
        if (isDoWorkInResume) {
            smartRefreshUtil.startRefresh()//开启第一个加载数据
        } else {
            doWorkNotAgainInOnResume()
        }
    }

    private fun doWorkNotAgainInOnResume() {//在onResume里面就执行一次
        if (!isBeenGetData) {
            isBeenGetData = true
            smartRefreshUtil.startRefresh()//开启第一个加载数据
        }
    }


    override fun showLoading(toShow: Boolean) {
        //自定义加载动画
        /*if (toShow) {
             LoadingDialog.showLoadingDialog(requireActivity())
         } else {
             LoadingDialog.cancelLoadingDialog()
         }*/
    }


    fun finishGetDataError() {
        emptyView.visibility = View.VISIBLE
        recycleview.visibility = View.GONE
    }

    fun finishGetDataSuccess() {
        emptyView.visibility = View.GONE
        recycleview.visibility = View.VISIBLE
    }
}