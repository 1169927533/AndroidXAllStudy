package com.example.a11699.module_smartrecycleview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_smart.*

/**
 *Create time 2020/8/14
 *Create Yu
 *description:给recycleview增加下拉刷新
 */
class SmartRecycleViewStudyActivity : AppCompatActivity() {
    private var isRefresh = true
    private var load = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart)
        initSmartRefreshLayout()//初始化SmartRefreshLayoutd的属性 和 下拉刷新功能
    }

    private fun initSmartRefreshLayout() {
        //设置阻尼效果（0-1） 越小阻尼越大默认0.5
        smart_refresh.setDragRate(0.6f)
        smart_refresh.setRefreshHeader(ClassicsHeader(this))//下拉刷新的经典样式

        smart_refresh.setEnableRefresh(true)//开启下拉刷新功能
        smart_refresh.setEnableLoadMore(false)//关闭上拉加载更多功能
        //监听下拉
        smart_refresh.setOnRefreshListener {
            if (isRefresh) {
                if(load==3){
                    it.finishRefreshWithNoMoreData()//完成刷新并标记没有更多数据 不用触发加载更多事件
                }
            }else{
                it.finishRefresh(false)
            }
        }
    }
}