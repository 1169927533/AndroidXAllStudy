package com.example.a11699.comp_viewmodel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 *Create time 2020/7/14
 *Create Yu
 *description:
 */
class MyViewModelFactory(val arg: Int) : ViewModelProvider.Factory {
    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var constructor = modelClass.getConstructor(Int::class.java)
         return constructor.newInstance(arg)
    }*/
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(arg) as T
        }
        throw  RuntimeException("unknown class :" + modelClass.getName());
    }
}