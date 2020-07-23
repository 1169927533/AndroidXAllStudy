package com.example.a11699.comp_netstudyt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.a11699.comp_netstudyt.Api.RetrofitBuild
import com.example.a11699.comp_netstudyt.bean.StudentBean
import com.example.a11699.comp_netstudyt.service.UserService
import kotlinx.coroutines.launch

/**
 *Create time 2020/7/21
 *Create Yu
 *description:
 */
class MyViewModel(application: Application) : BaseViewModel(application) {
    val studentLiveData by lazy {
        MutableLiveData<StudentBean>()
    }

    fun getStudentInferMarion(uid: String) {
        viewModelScope.launch {
            var userService = getRepo(UserService::class.java)
            studentLiveData.value = userService.getStudentInforMation(uid)
        }
    }
}