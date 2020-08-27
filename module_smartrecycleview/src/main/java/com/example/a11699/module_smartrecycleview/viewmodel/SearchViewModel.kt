package com.example.a11699.module_smartrecycleview.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.a11699.comp_base.bean.DailyList
import com.example.a11699.comp_base.bean.SearchStudentBean
import com.example.a11699.comp_base.service.UserService
import com.example.a11699.lib_network.vm.BaseViewModel

/**
 *Create time 2020/8/27
 *Create Yu
 *description:
 */
class SearchViewModel(application: Application) : BaseViewModel(application) {

    val searchLiveData by lazy {
        MutableLiveData<DailyList>()
    }

    fun getStudentInferMarion(uid:String) {
        launch({
            var userService = getRepo(UserService::class.java)
            searchLiveData.value = userService.getSearchStudent(uid)
        })
    }
}