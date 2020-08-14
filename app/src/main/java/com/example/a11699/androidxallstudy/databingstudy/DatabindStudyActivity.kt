package com.example.a11699.androidxallstudy.databingstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.databinding.ActivityDatabindstudyBindingImpl
import kotlinx.android.synthetic.*

/**
 *Create time 2020/8/9
 *Create Yu
 *description:
 */
class DatabindStudyActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setContentView( R.layout.activity_databindstudy)

    }
}