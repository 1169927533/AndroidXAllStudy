package com.example.a11699.comp_netstudyt.service

import com.example.a11699.comp_netstudyt.Api.Api
import com.example.a11699.comp_netstudyt.bean.StudentBean
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Create time 2020/7/20
 *Create Yu
 *description:
 */
interface UserService {
    @GET("/SDSY/TgetOneStudent")
    suspend fun getStudentInforMation(@Query("sid") uid: String): StudentBean
}