package com.example.a11699.module_smartrecycleview

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.comp_base.bean.DailyStudentBean
import com.example.a11699.module_smartrecycleview.adapter.SearchAdapter
import com.example.a11699.module_smartrecycleview.base.activity.BaseRecycleViewActivity
import com.example.a11699.module_smartrecycleview.viewmodel.SearchViewModel
import com.hipi.vm.lazyVm

/**
 *Create time 2020/8/14
 *Create Yu
 *description:给recycleview增加下拉刷新
 */
class SmartRecycleViewStudyActivity : BaseRecycleViewActivity<DailyStudentBean>() {
    override fun initView() {

    }

    val viewModel by lazyVm<SearchViewModel>()


    override val baseAdapter: BaseQuickAdapter<DailyStudentBean, BaseViewHolder> by lazy { SearchAdapter() }
    override val onFetchListener: (page: Int) -> Unit = {
        //这里去做网络请求
        viewModel.getStudentInferMarion("03764")
    }

    override fun observeLiveData() {
        viewModel.searchLiveData.observe(this, Observer {
            if (it != null) {
                if (it.result.size == 0 && baseAdapter.data.size == 0) {
                    finishGetDataError()
                } else {
                    finishGetDataSuccess()
                }
                smartRefreshUtil.onFetchFinish(it.result, true)
                baseAdapter.setEnableLoadMore(false)
            } else {
                finishGetDataError()
            }
        })

    }

}