package com.example.a11699.androidxallstudy.piaodanmu

interface IDanmukView<T> {



    /**
     * 显示礼物
     */
    fun onNewModel(mode: T)

    /**
     * 退出直播间或者切换房间 清空
     */
    fun clear()


}