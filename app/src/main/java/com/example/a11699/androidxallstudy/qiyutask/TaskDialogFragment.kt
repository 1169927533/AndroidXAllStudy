package com.example.a11699.androidxallstudy.qiyutask

import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customtab.fragment.ItemFragmetn
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil
import com.example.a11699.androidxallstudy.qiyutask.adapter.TaskTabAdapter
import com.example.a11699.androidxallstudy.qiyutask.fragment.TaskFragment
import kotlinx.android.synthetic.main.dialog_qiyutask.*

/**
 * @Author Yu
 * @Date 2020/12/3 11:09
 * @Description 任务弹窗
 */
class TaskDialogFragment : BaseDialogFragment() {
    var items = arrayListOf<String>("新手任务", "日常任务")

    override fun inflateLayout(): Int {
        return R.layout.dialog_qiyutask
    }

    override fun initView() {
        viewpager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TaskFragment.newInstance()
            }

            override fun getCount(): Int {
                return items.count()
            }
        }
        var adapter3 = TaskTabAdapter(items, viewpager)
        //初始化底部view
        var imgView = ImageView(requireContext())
        imgView.setBackgroundResource(R.drawable.shape_white_16)
        var layoutParams = RelativeLayout.LayoutParams(ViewUtil.dip2px(16f), ViewUtil.dip2px(4f))
        imgView.layoutParams = layoutParams
        customTabLayout21.initBottomView(imgView)
        customTabLayout21.setAdapter(adapter3, viewpager, 0)
    }
}