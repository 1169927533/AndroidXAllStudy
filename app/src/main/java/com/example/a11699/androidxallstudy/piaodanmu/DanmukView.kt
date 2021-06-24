package com.example.a11699.androidxallstudy.piaodanmu

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
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

    private val dmAnimalView = LinkedList<String>()

    private var isNextAble = true

    override fun onNewModel(mode: Danmuke) {
        var giftAnimalBean: GiftAnimalBean? = null
        for (value in haveInitView) {
            if (!value.isBusy) {
                giftAnimalBean = value
                break
            }
        }
        Log.i("zjc", "${haveInitView.size}")

        if (giftAnimalBean == null) {
            var vv = LayoutInflater.from(context)
                    .inflate(R.layout.layout_danmukitemview, this, false) as DanmukItemView
            vv.setOnClickListener {

            }
            giftAnimalBean = GiftAnimalBean(false, vv)
            haveInitView.add(giftAnimalBean)
        }

        var itemView: DanmukItemView = giftAnimalBean.view as DanmukItemView
        itemView.setDamukeAniml(ScreenUtil.getScreenWidth(), giftAnimalBean)
        itemView.tvDanmukSenderName.text = "mode.senderName"
        itemView.tvDanmukContent.text = mode.content
        itemView.tvDanmukSenderLevel.text = "mode.content"

        itemView.tvDanmukContent.isFocusable = true;
        itemView.tvDanmukContent.ellipsize = TextUtils.TruncateAt.MARQUEE;
        itemView.tvDanmukContent.setSingleLine();
        itemView.tvDanmukContent.setSelected(true)
        itemView.tvDanmukContent.setFocusableInTouchMode(true);
        itemView.tvDanmukContent.setHorizontallyScrolling(true);

        itemView.nextAvalibeCall = {
            //isNextAble = true
            checkNext()
        }

        itemView.endCall = {
            it.isBusy = false
        }

        if (isNextAble) {
            Log.i("zjc", "这就进来了？")
            startNext(giftAnimalBean)
        } else {
            dmAnimalView.add("itemView")
        }
    }

    private fun checkNext() {
        val next = dmAnimalView.poll()
        next?.let {
            for (value in haveInitView) {
                if (!value.isBusy) {
                    startNext(value)
                    break
                }
            }
        }
        if (next == null) {
            isNextAble = true
            Log.i("zjc", "这就为true？？")
        }
    }

    private fun startNext(item: GiftAnimalBean) {
        Log.i("zjc", "有： ${childCount}")
        item.isBusy = true
        isNextAble = false
        if (item.view.parent == null) {
            addView(
                    item.view,
                    FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            )
        }
        (item.view as DanmukItemView).post { (item.view as DanmukItemView).start() }
    }

    override fun clear() {
        dmAnimalView.clear()
    }

}