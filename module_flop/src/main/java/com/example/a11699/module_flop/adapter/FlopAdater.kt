package com.example.a11699.module_flop.adapter

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.a11699.module_flop.R
import com.example.a11699.module_flop.bean.FlopBean
import kotlinx.android.synthetic.main.activity_floa.*
import kotlinx.android.synthetic.main.item_flop.view.*

/**
 *Create time 2020/10/13
 *Create Yu
 *description:
 */
class FlopAdater(var context: Context) : BaseQuickAdapter<FlopBean, BaseViewHolder>(R.layout.item_flop, ArrayList<FlopBean>()) {
    var bitmap: Bitmap? = null
    override fun convert(helper: BaseViewHolder, item: FlopBean?) {
        helper.itemView.flopview?.let { itt ->
            itt.mFontBg = item?.frontSrc!!
            Glide.with(context).asBitmap()
                    .load(item!!.backSrc)
                    .into(object : SimpleTarget<Bitmap?>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                            itt.mBackBitMap = resource
                        }
                    })

            itt.setOnClickListener {
                itt.startAnimal()
            }
        }
    }
}