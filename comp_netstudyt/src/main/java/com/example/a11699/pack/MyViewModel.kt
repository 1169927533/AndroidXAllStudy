package com.example.a11699.pack

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.a11699.comp_base.bean.StudentBean
import com.example.a11699.comp_base.service.UserService
import com.example.a11699.lib_network.vm.BaseViewModel

/**
 *Create time 2020/10/29
 *Create Yu
 *description:
 */
class MyViewModel(application: Application) : BaseViewModel(application) {
    val userLiveData by lazy {
        MutableLiveData<StudentBean>()
    }

    fun getUserInformaion() {
        launch({
            var userService = getRepo(UserService::class.java)
            userLiveData.value = userService.getStudentInforMation("201602020124")
        }, error = {
            Log.i("zjc","dsadsad")
        })
    }
}