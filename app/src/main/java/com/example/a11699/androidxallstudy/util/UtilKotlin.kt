package com.example.a11699.androidxallstudy.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 *Create time 2020/6/17
 *Create Yu
 *description: 扩展startActivity
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}