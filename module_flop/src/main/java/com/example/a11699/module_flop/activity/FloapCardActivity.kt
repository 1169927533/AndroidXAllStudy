package com.example.a11699.module_flop.activity

import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
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
    var imgView = ArrayList<ImageView>()
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
        imgView.add(flopview1)
        imgView.add(flopview2)
        imgView.add(flopview3)
        imgView.add(flopview4)
        imgView.add(flopview5)
        imgView.add(flopview6)


        btn_send.setOnClickListener {
            for (index in 0 until adapter.itemCount) {
                holder = recycleview.findViewHolderForAdapterPosition(index)
                listLocation.add(holder!!.itemView.flopview.location)
                prePareAnimal(imgView[index], holder!!.itemView.flopview.location, index)
            }
        }
    }

    private fun prePareAnimal(view: View, location3: IntArray, index: Int) {
        var location1 = IntArray(2)
        recycleview.getLocationInWindow(location1)

        var location = IntArray(2)
        view.getLocationInWindow(location)

        var xxx  = view.width*1.6
        var curx = view.width
        var pian = (xxx-curx)/2


        var yyy = view.height*1.6
        var cury =  view.height
        var piany = (yyy-cury)/2


        var trx = location[0] - (location1[0] + location3[0])
        var tryy = location[1] - (location1[1] + location3[1])

        var anima = ValueAnimator.ofFloat(1f, 1.6f).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                view.scaleX = it.animatedValue as Float
                view.scaleY = it.animatedValue as Float
            }
        }
        anima.startDelay = 500 * index.toLong()
        anima.start()
        view.animate().translationY(-(tryy.toFloat()-piany.toFloat())).translationX(-(trx.toFloat()-pian.toFloat())).setStartDelay(500 * index.toLong()).duration = 500
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