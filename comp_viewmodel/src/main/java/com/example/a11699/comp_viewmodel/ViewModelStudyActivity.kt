package com.example.a11699.comp_viewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

import androidx.lifecycle.lifecycleScope
import com.example.a11699.comp_viewmodel.viewmodel.MyViewModel
import com.example.a11699.comp_viewmodel.viewmodel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_viewmodelstudy.*
import kotlinx.coroutines.GlobalScope

/**
 *Create time 2020/7/14
 *Create Yu
 *description:
 */
class ViewModelStudyActivity : AppCompatActivity() {
    lateinit var viewmodel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodelstudy)
        viewmodel = ViewModelProvider(this, MyViewModelFactory(10)).get(MyViewModel::class.java)
        btn_myviewmodel.setOnClickListener {
            textView.setText("自定义构造函数里的值" + viewmodel.args)
        }
        initData()

    }


    private fun initData() {
        viewmodel.numLiveData.observe(this, Observer {

        })
    }
}