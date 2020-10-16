package com.example.a11699.module_flop.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.addListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.a11699.lib_util.dp
import com.example.a11699.module_flop.R
import com.example.a11699.module_flop.adapter.FlopAdater
import com.example.a11699.module_flop.bean.FlopBean
import com.example.a11699.module_flop.customview.FlopView
import com.example.a11699.module_flop.customview.FlopViewGroup
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
    var listFlopView = ArrayList<FlopView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floa)
        recycleview.layoutManager = GridLayoutManager(this, 3)
        var adapter = FlopAdater(this)
        recycleview.adapter = adapter
        recycleview.addItemDecoration(FollowItemDecoration())


        var list = ArrayList<FlopBean>()
        list.add(FlopBean(R.drawable.b1, "https://upload-images.jianshu.io/upload_images/16562048-36d730fc88d46c68.png"))
        list.add(FlopBean(R.drawable.b2, "https://upload-images.jianshu.io/upload_images/16562048-51b62bcde50714c2.png"))
        list.add(FlopBean(R.drawable.b3, "https://upload-images.jianshu.io/upload_images/16562048-34b96f46aa0a9614.png"))
        list.add(FlopBean(R.drawable.b4, "https://upload-images.jianshu.io/upload_images/16562048-15a610246f5d3b1e.png"))
        list.add(FlopBean(R.drawable.b5, "https://lk20.oss-accelerate.aliyuncs.com/avatar/default/avatar_2.jpg"))
        list.add(FlopBean(R.drawable.b6, "https://upload-images.jianshu.io/upload_images/16562048-41bd5a442ec84a1e.png"))
        adapter.setNewData(list)


        btn_send.setOnClickListener {
            for (value in listFlopView) {
                cc.removeView(value)
            }
            listFlopView.clear()
            for (index in 0 until adapter.itemCount) {
                holder = recycleview.findViewHolderForAdapterPosition(index)
                listLocation.add(holder!!.itemView.flopview.location)
                prePareAnimal(list[index].frontSrc, list[index].backSrc, holder!!.itemView.flopview.location, index)
            }
        }



        ffffff.setOnClickListener {
            ffffff.startAnimal()
        }

        click.setOnClickListener {
            Toast.makeText(this,"Dasdasdasdas",Toast.LENGTH_LONG).show()
        }
    }

    private fun prePareAnimal(fg: Int, bg: String, location3: IntArray, index: Int) {
        var flopView = FlopView(this)
        listFlopView.add(flopView)
        flopView.mFontBg = fg
        Glide.with(this).asBitmap()
                .load(bg)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        flopView.mBackBitMap = resource
                    }
                })
        flopView.id = R.id.f1
        val ivLeftLayoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                50.dp.toInt(), 100.dp.toInt()
        )
        ivLeftLayoutParams.leftToLeft = R.id.cc
        ivLeftLayoutParams.bottomToBottom = R.id.cc
        ivLeftLayoutParams.rightToRight = R.id.cc
        flopView.layoutParams = ivLeftLayoutParams
        cc.addView(flopView)
        flopView.setOnClickListener {
            flopView.startAnimal()
        }



        var location1 = IntArray(2)
        recycleview.getLocationInWindow(location1)
        var location = IntArray(2)
        flopview6.getLocationInWindow(location)
        var xxx = flopview6.width * 1.6
        var curx = flopview6.width
        var pian = (xxx - curx) / 2
        var yyy = flopview6.height * 1.6
        var cury = flopview6.height
        var piany = (yyy - cury) / 2
        var trx = location[0] - (location1[0] + location3[0])
        var tryy = location[1] - (location1[1] + location3[1])
        flopView.startTransAnimal(-(trx.toFloat() - pian.toFloat()), -(tryy.toFloat() - piany.toFloat()), index)

    }

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
            when (column) {
                0 -> {
                    outRect.set(middle, bottom, middle, 0)
                }
                1 -> {
                    outRect.set(middle, bottom, middle, 0)
                }
                else -> {
                    outRect.set(middle, bottom, middle, 0)
                }
            }
        }
    }
}