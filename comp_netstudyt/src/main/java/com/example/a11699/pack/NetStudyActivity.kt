package com.example.a11699.pack

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.a11699.comp_netstudyt.R
import com.example.a11699.lib_network.config.BaseConfig
import com.example.a11699.lib_network.util.RetrofitBuild
import com.hipi.vm.lazyVm
import kotlinx.android.synthetic.main.ac_n2.*

/**
 *Create time 2020/10/29
 *Create Yu
 *description:
 */
class NetStudyActivity : AppCompatActivity() {

    val viewModel by lazyVm<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_n2)

        btn_send.setOnClickListener {
            viewModel.getUserInformaion()
        }

        btn_seterror.setOnClickListener {
            RetrofitBuild.rectConfig(ErrorConfig::class.java)
        }
        btn_setsuccess.setOnClickListener {
            RetrofitBuild.rectConfig(SuccessConfig::class.java)

        }


    }

    class ErrorConfig : BaseConfig() {
        override fun initBaseUrl(): String {
            return "https://sdsy.zzjc.edu.cnsss"
        }
    }
    class SuccessConfig:BaseConfig(){
        override fun initBaseUrl(): String {
            return "https://sdsy.zzjc.edu.cn"
        }
    }
}