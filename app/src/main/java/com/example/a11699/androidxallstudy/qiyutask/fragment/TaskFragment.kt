package com.example.a11699.androidxallstudy.qiyutask.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.androidxallstudy.qiyutask.adapter.TaskItemAdapter
import com.example.a11699.androidxallstudy.qiyutask.bean.DailyTask
import com.example.a11699.androidxallstudy.qiyutask.bean.DailyTask.DailyTaskBean
import com.example.a11699.module_smartrecycleview.base.fragme.BaseRecycleViewFragment

/**
 * @Author Yu
 * @Date 2020/12/3 14:19
 * @Description TODO
 */
class TaskFragment : BaseRecycleViewFragment<DailyTask.DailyTaskBean>() {
    override val isShouldNeedLoadMore: Boolean = false
    override val isShouldNeedRefresh: Boolean = false

    companion object {
        fun newInstance(): TaskFragment {
            var fragment = TaskFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val baseAdapter: BaseQuickAdapter<DailyTask.DailyTaskBean, BaseViewHolder> by lazy {
        TaskItemAdapter()
    }
    override val onFetchListener: (page: Int) -> Unit = {
        val dateTask: List<DailyTaskBean> = ArrayList<DailyTaskBean>()
        var dailyTaskBean = DailyTaskBean()
        dailyTaskBean.award = "321"

    }
    override val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun observeLiveData() {

    }
}