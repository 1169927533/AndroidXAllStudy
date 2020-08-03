package com.example.a11699.androidxallstudy.piaodanmu

import android.app.ActivityManager
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.myseekbar.ScreenUtil

import java.util.*

class DanmukView  : FrameLayout, IDanmukView<Danmuke> {

    constructor(context: Context) : super(context)
    constructor(context: Context, mAttributeSet: AttributeSet?) : super(context, mAttributeSet)

    val dmAnimalView  = LinkedList<DanmukItemView>()


     private var isNextAble = true

    override fun onNewModel(mode: Danmuke) {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_danmukitemview,this,false) as DanmukItemView
        itemView.setDamukeAniml(mode, ScreenUtil.getScreenWidth())
        itemView.setOnClickListener {

        }
        itemView.nextAvalibeCall = {
            isNextAble = true
            checkNext()
        }

        itemView.endCall ={
            itemView.clear()
            removeView(itemView)
        }
        if(isNextAble){
          startNext(itemView)
        }else{
            dmAnimalView.add(itemView)
        }
    }

    private fun checkNext(){
        val next = dmAnimalView.poll()
        next?.let {
            startNext(it)
        }
    }

    private fun startNext(item:DanmukItemView){

        addView(item,FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT))
        isNextAble = false
        item.post {    item.start() }

    }

    override fun clear() {
        dmAnimalView.clear()
    }

}