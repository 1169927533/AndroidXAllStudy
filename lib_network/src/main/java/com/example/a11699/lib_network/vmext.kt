package com.example.a11699.lib_network

import com.example.a11699.lib_network.util.RetrofitBuild
import com.hipi.vm.BaseViewModel


fun <T> BaseViewModel.getRepo(cls:Class<T>?):T{
    return RetrofitBuild.retrofit.create(cls)
}