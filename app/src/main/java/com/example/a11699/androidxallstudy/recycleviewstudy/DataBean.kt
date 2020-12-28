package com.example.a11699.androidxallstudy.recycleviewstudy

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @Author Yu
 * @Date 2020/12/20 9:16
 * @Description TODO
 */
class DataBean : MultiItemEntity {
    var mType = 0
    var mName = "Yu"

    constructor(t: Int, n: String) {
        this.mType = t
        this.mName = n
    }

    override fun getItemType(): Int {
        return mType
    }
}