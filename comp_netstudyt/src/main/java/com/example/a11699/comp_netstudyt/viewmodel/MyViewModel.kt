package com.example.a11699.comp_netstudyt.viewmodel

import android.app.Application
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.a11699.comp_netstudyt.Api.RetrofitBuild
import com.example.a11699.comp_netstudyt.bean.StudentBean
import com.example.a11699.comp_netstudyt.service.UserService
import kotlinx.coroutines.*
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor
import kotlin.coroutines.experimental.CoroutineContext

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

    //一个无关痛痒的东西 职位了测测试livedata的属性
    val testLiveData by lazy {
        MutableLiveData<String>()
    }

    fun getStudentInferMarion(uid: String) {
        viewModelScope.launch {
            var userService = getRepo(UserService::class.java)
            studentLiveData.value = userService.getStudentInforMation(uid)
        }

        GlobalScope.launch(Dispatchers.IO + CoroutineName("Hello")) {

        }
    }

    fun changLiveDataValue(data: String) {
        testLiveData.postValue(data)
    }
}
class MyContinuationIntercepor:ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = TODO("Not yet implemented")

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        TODO("Not yet implemented")
    }
}
class MyContinuation<T>:kotlin.coroutines.Continuation<T>{
    override val context: kotlin.coroutines.CoroutineContext
        get() = TODO("Not yet implemented")

    override fun resumeWith(result: Result<T>) {
        TODO("Not yet implemented")
    }
}