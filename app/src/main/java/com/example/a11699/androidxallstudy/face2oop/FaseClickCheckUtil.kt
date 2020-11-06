/*
package com.example.a11699.androidxallstudy.face2oop

import android.view.View
import com.example.a11699.androidxallstudy.R

*/
/**
 * @Author Yu
 * @Date 2020/11/5 10:27
 * @Description 面向切面的防快速点击 工具类
 *//*

class FaseClickCheckUtil {
    companion object{
        fun isFastClick(view:View,interval:Long):Boolean{
            var key = R.id.view_click_time
            //最近的点击时间
            var currentClickTime = System.currentTimeMillis()
            if(view.getTag(key)==null){
                //1. 这是第一次点击
                view.setTag(key,currentClickTime)
                return false;
            }
            //2. 非第一次点击
            //上次点击时间
            var lastClickTIme:Long = view.getTag(key) as Long
            if((currentClickTime - lastClickTIme)<interval){
                // 未超过时间间隔，视为快速点击
                return true
            }else{
                //保存最近点击时间
                view.setTag(key,currentClickTime)
                return false
            }
        }
    }
}
*/
