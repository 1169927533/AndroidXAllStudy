package com.example.a11699.module_flop.activity

import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a11699.lib_util.dp
import com.example.a11699.module_flop.R
import com.example.a11699.module_flop.adapter.FlopAdater
import com.example.a11699.module_flop.bean.FlopBean
import com.example.a11699.module_flop.customview.FlopView
import kotlinx.android.synthetic.main.activity_floa.*
import kotlinx.android.synthetic.main.item_flop.view.*

/**
 *Create time 2020/10/13
 *Create Yu
 *description:
 */
class FloapCardActivity : AppCompatActivity() {
    var holder: RecyclerView.ViewHolder? = null
    var listLocation = ArrayList<IntArray>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floa)
        recycleview.layoutManager = GridLayoutManager(this, 3)
        var adapter = FlopAdater(this)
        recycleview.adapter = adapter
        recycleview.addItemDecoration(FollowItemDecoration())
        var list = ArrayList<FlopBean>()
        list.add(FlopBean(R.drawable.b1, ""))
        list.add(FlopBean(R.drawable.b2, ""))
        list.add(FlopBean(R.drawable.b3, ""))
        list.add(FlopBean(R.drawable.b4, ""))
        list.add(FlopBean(R.drawable.b5, ""))
        list.add(FlopBean(R.drawable.b6, ""))
        adapter.setNewData(list)

        btn_send.setOnClickListener {
            for (index in 0 until adapter.itemCount) {
                holder = recycleview.findViewHolderForAdapterPosition(index)
                listLocation.add(holder!!.itemView.flopview.location)
                prePareAnimal(holder!!.itemView.flopview, holder!!.itemView.flopview.location)
            }
            prePareAnimal(btn_send, recycleview.findViewHolderForAdapterPosition(1)!!.itemView.flopview.location)
        }
    }

    private fun prePareAnimal(view: View, location3: IntArray) {
        var location1 = IntArray(2)
        recycleview.getLocationInWindow(location1)

        var location = IntArray(2)
        view.getLocationInWindow(location)


        var trx = location[0] - (location1[0] + location3[0])
        var tryy = location[1] - (location1[1] + location3[1])

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                view.scaleX = it.animatedValue as Float
                view.scaleY = it.animatedValue as Float
            }
        }.start()
        view.animate().translationY(-tryy.toFloat()).translationX(-trx.toFloat()).setStartDelay(1000L).duration = 1000


    }

    private fun preFlopView(flopView: FlopView, frontSrc: Int) {
        flopView.mFontBg = frontSrc

    }

    //首页 关注页
    class FollowItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            val column = itemPosition % 3
            val margin = 34.dp.toInt()
            val bottom = 6.dp.toInt()
            val middle = 13.dp.toInt()
            when {
                column == 0 -> {
                    outRect.set(middle, bottom, middle, 0)
                }
                column == 1 -> {
                    outRect.set(middle, bottom, middle, 0)
                }
                else -> {
                    outRect.set(middle, bottom, middle, 0)
                }
            }
        }
    }
}