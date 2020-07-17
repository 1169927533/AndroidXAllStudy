package com.example.a11699.comp_viewmodel


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a11699.comp_viewmodel.viewmodel.MyViewModel

/**
 *Create time 2020/7/15
 *Create Yu
 *description:
 */
class MyFragement: Fragment(){
    fun dsa(){
        var myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
    }
}