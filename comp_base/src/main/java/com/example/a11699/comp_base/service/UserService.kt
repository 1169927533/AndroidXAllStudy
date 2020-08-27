package com.example.a11699.comp_base.service

import com.example.a11699.comp_base.bean.DailyList
import com.example.a11699.comp_base.bean.SearchStudentBean
import com.example.a11699.comp_base.bean.StudentBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Create time 2020/7/20
 *Create Yu
 *description:
 */
interface UserService {
    @GET("/SDSY/TgetOneStudent")
    suspend fun getStudentInforMation(@Query("sid") uid: String): StudentBean

    @GET("/SDSY/getSqrjListByTid")
    suspend fun getSearchStudent(@Query("t_id") t_id: String ): DailyList
}