package com.example.a11699.androidxallstudy.recycleviewstudy

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.recycleview_activity.*

/**
 * @Author Yu
 * @Date 2020/12/20 8:58
 * @Description recycleview学习\
 *
 */
class RecycleViewStudyActivity : BaseActivity() {
    var mData = ArrayList<DataBean>()
    override fun getLayoutId(): Int {
        return R.layout.recycleview_activity
    }

    override fun initView() {
        var mDataBean = DataBean(0, "Yu")
        mData.add(mDataBean)
        mDataBean = DataBean(1, "Yu")
        mData.add(mDataBean)
        mDataBean = DataBean(1, "Zhi")
        mData.add(mDataBean)
        mDataBean = DataBean(1, "Qiang")
        mData.add(mDataBean)

        recycleview.layoutManager = GridLayoutManager(this, 2)
        recycleview.addItemDecoration(ReItemDecoration())

        recycleview.adapter = RecycleViewAdapter(mData)
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }


    class ReItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            val column = itemPosition % 2
            val margin = ViewUtil.dip2px(8.toFloat())
            if (column == 0) {
                outRect.set(0, 0, margin / 2, margin)
            } else {
                outRect.set(margin / 2, 0, 0, margin)
            }
        }
    }
}