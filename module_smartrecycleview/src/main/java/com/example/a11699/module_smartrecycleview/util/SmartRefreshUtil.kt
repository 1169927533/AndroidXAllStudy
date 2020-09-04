package com.example.a11699.module_smartrecycleview.util

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *Create time 2020/8/19
 *Create Yu
 *description:下拉刷新，上拉加载的帮助类
 */
class SmartRefreshUtil<T>(private val adapter: BaseQuickAdapter<T, BaseViewHolder>,
                          private val recycleView: RecyclerView,
                          private val smartRefreshLayout: SmartRefreshLayout,
                          isNeedLoadMore: Boolean,
                          isNeedRefresh: Boolean,
                          needNormalNum: Int,//静默加载数量
                          private val onFetchListener: ((page: Int) -> Unit)

) {
    init {
        if (isNeedLoadMore) {
            adapter.setEnableLoadMore(isNeedLoadMore)
            //adapter.disableLoadMoreIfNotFullPage()//默认第一次加载会进入回调，如果不需要可以配置
            adapter.setPreLoadNumber(needNormalNum)//静默加载数量 当展示的数量小于这个的时候 会自动加载上拉加载更多
            /**
             * 设置上拉加载样式 可以自定义 见官网https://www.jianshu.com/p/b343fcff51b0/
             */
            val simpleLoadMoreView = SimpleLoadMoreView()
            adapter.setLoadMoreView(simpleLoadMoreView)
            simpleLoadMoreView.setLoadMoreEndGone(true)
            /**
             * 加载更多
             */
            adapter.setOnLoadMoreListener({ onLoadMore() }, recycleView)
        }
        smartRefreshLayout.setEnableLoadMore(false)
        if (isNeedRefresh) {
            smartRefreshLayout.setEnableRefresh(isNeedRefresh)
            smartRefreshLayout.setOnRefreshListener { onRefresh() }
        }
    }


    var isRefreshIng: Boolean = false //是否正在刷新
    var isOnLoadMoreIng: Boolean = false//是否正在加载更多
    private var currentPage: Int = 0
    private var eachPageSize: Int = 0//后台请求来一页展示的数量

    /**
     * 上拉加载更多
     */
    private fun onLoadMore() {
        if (isRefreshIng || isOnLoadMoreIng) {
            if (isOnLoadMoreIng) {
                isOnLoadMoreIng = false
            }
            return
        }
        isOnLoadMoreIng = true
        onFetchListener(currentPage + 1)
    }

    fun startRefresh() {
        onRefresh()
    }

    /**
     * 下拉刷新
     */
    private fun onRefresh() {
        if (isRefreshIng || isOnLoadMoreIng) {
            if (isRefreshIng) {
                isRefreshIng = false
            }
            smartRefreshLayout.finishRefresh(true)
            return
        }
        isRefreshIng = true
        currentPage = 0
        onFetchListener(0)
    }

    fun onFetchFinish(data: List<T>?, goneIfNoData: Boolean) {
        Log.i("Zjccsssc", data!!.size.toString())
        smartRefreshLayout.finishRefresh(true)
        data?.let { listData ->
            if (currentPage == 0 && isRefreshIng) {
                eachPageSize = listData.size
            }
            if (isOnLoadMoreIng) {
                currentPage++
                adapter.addData(listData)
            } else {
                Log.i("qqqq", "我进来添加数据")

                adapter.setNewData(listData)
            }
            if (listData.size < eachPageSize) {
                adapter.loadMoreEnd(goneIfNoData)//会在底部提示 没有更多数据了
            } else {
                adapter.loadMoreComplete()//表示此次上拉结束了 下一页还有数据
            }
        }
        isOnLoadMoreIng = false
        isRefreshIng = false
    }


}