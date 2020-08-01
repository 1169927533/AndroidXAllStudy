package com.example.a11699.androidxallstudy.piaodanmu

import android.view.ViewStub
class DanmukViewStub(val viewStub: ViewStub) : IDanmukView<Danmuke> {


    private val giftShowView: DanMuView by lazy {
        (viewStub.inflate() as DanMuView)
    }

    override fun onNewModel(mode: Danmuke) {
        giftShowView.onNewModel(mode)
    }

    override fun clear() {
        giftShowView.clear()
    }


}
