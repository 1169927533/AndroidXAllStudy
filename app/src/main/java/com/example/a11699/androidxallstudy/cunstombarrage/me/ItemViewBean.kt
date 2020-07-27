package com.example.a11699.androidxallstudy.cunstombarrage.me

import android.animation.ObjectAnimator
import android.view.View

/**
 *Create time 2020/6/22
 *Create Yu
 *description:一个弹幕的内容
 */
class ItemViewBean(view: View, content: String, leftDistance: Int, childWidth: Int, objectAnimator: ObjectAnimator?) {
    var view: View = view
    var leftDistance: Int = leftDistance //控件左边距离屏幕右侧的距离
    var childWidth: Int = childWidth //一个控件的宽度
    var objectAnimator: ObjectAnimator? = objectAnimator
    var content: String = content
}