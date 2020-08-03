package com.example.a11699.androidxallstudy.piaodanmu

import android.view.ViewStub
class DanmukViewStub(val viewStub: ViewStub) : IDanmukView<Danmuke> {


    private val giftShowView: DanmukView by lazy {
        (viewStub.inflate() as DanmukView)
    }

    override fun onNewModel(mode: Danmuke) {
        giftShowView.onNewModel(mode)
    }

    override fun clear() {
        giftShowView.clear()
    }


}
