package com.example.a11699.comp_netstudyt.viewmodel

import android.app.Application

import androidx.lifecycle.MutableLiveData

import com.example.a11699.comp_base.bean.StudentBean
import com.example.a11699.comp_base.service.UserService
import com.example.a11699.lib_network.vm.BaseViewModel


/**
 *Create time 2020/7/21
 *Create Yu
 *description:
 */
class MyViewModel(application: Application) : BaseViewModel(application) {
    //获取学生信息
    val studentLiveData by lazy {
        MutableLiveData<StudentBean>()
    }

    //一个无关痛痒的东西 只为了测测试livedata的属性
    val testLiveData by lazy {
        MutableLiveData<String>()
    }

    fun getStudentInferMarion(uid: String) {
        launch({
            var userService = getRepo(UserService::class.java)
            studentLiveData.value = userService.getStudentInforMation(uid)
        })
    }

    fun changLiveDataValue(data: String) {
        testLiveData.value = data
    }
}