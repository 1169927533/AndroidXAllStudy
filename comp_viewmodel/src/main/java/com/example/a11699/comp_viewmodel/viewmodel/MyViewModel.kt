package com.example.a11699.comp_viewmodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel

/**
 *Create time 2020/7/14
 *Create Yu
 *description:
 */
class MyViewModel(args: Int) : ViewModel() {
    var args = args


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


