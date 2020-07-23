package com.example.a11699.comp_viewmodel.viewmodel

import android.util.Log
import androidx.lifecycle.*

/**
 *Create time 2020/7/14
 *Create Yu
 *description:
 */
class MyViewModel(args: Int) : ViewModel()  {
    var args = args
    val numLiveData by lazy {
        MutableLiveData<Int>()
    }

    private val users: MutableLiveData<List<String>> by lazy {
        loadUsers()
    }

    fun getUsers(): LiveData<List<String>> {
        return users
    }

    private fun loadUsers(): MutableLiveData<List<String>> {
        return MutableLiveData()
    }


}


