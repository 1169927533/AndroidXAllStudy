package com.example.a11699.androidxallstudy.piaodanmu

import android.app.ActivityManager
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ScreenUtil
import kotlinx.android.synthetic.main.view_danmu_item_view.view.*

import java.util.*

class DanmukView : FrameLayout, IDanmukView<Danmuke> {
    var haveInitView = ArrayList<GiftAnimalBean>()

    constructor(context: Context) : super(context)
    constructor(context: Context, mAttributeSet: AttributeSet?) : super(context, mAttributeSet)

    val dmAnimalView = LinkedList<DanmukItemView>()


    private var isNextAble = true

    override fun onNewModel(mode: Danmuke) {
        var giftAnimalBean: GiftAnimalBean? = null
        for (value in haveInitView) {
            Log.i("zjc", "${value.isBusy}")
            if (!value.isBusy) {
                giftAnimalBean = value
                break
            }
        }
        Log.i("zjc", "${haveInitView.size}")
        var itemView: DanmukItemView? = null
        if (giftAnimalBean == null) {
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_danmukitemview, this, false) as DanmukItemView
            itemView.setOnClickListener {

            }
            giftAnimalBean = GiftAnimalBean(true, itemView)
            haveInitView.add(giftAnimalBean)
        } else {
            itemView = giftAnimalBean.view as DanmukItemView
        }
        giftAnimalBean.isBusy = true
        itemView.setDamukeAniml(mode, ScreenUtil.getScreenWidth(),giftAnimalBean)

        itemView.tvDanmukSenderName.text = mode.senderName
        itemView.tvDanmukContent.text = mode.content
        itemView.tvDanmukSenderLevel.text = mode.content

        itemView.nextAvalibeCall = {
            isNextAble = true
            checkNext()
        }

        itemView.endCall = {
            it.isBusy = false
        }
        if (isNextAble) {
            startNext(itemView)
        } else {
            dmAnimalView.add(itemView)
        }
    }

    private fun checkNext() {
        val next = dmAnimalView.poll()
        next?.let {
            startNext(it)
        }
    }

    private fun startNext(item: DanmukItemView) {
        if(item.parent==null){
            addView(
                item,
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            )
        }

        isNextAble = false
        item.post { item.start() }
    }

    override fun clear() {
        dmAnimalView.clear()
    }

}