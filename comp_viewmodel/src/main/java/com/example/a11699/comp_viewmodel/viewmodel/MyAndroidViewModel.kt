package com.example.a11699.comp_viewmodel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @Author Yu
 * @Date 2020/11/20 11:06
 * @Description 如果想获取Application的话 就需要继承AndroidViewModel类
 */
class MyAndroidViewModel(application: Application):AndroidViewModel(application) {
init {
    viewModelScope.launch {

    }
}
}