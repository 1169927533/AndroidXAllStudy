package com.example.a11699.comp_netstudyt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a11699.comp_netstudyt.util.DialogUtils
import com.example.a11699.comp_netstudyt.util.lazyVm
import com.example.a11699.comp_netstudyt.util.lazyVmT
import com.example.a11699.comp_netstudyt.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_netstudy.*
import pub.devrel.easypermissions.EasyPermissions

/**
 *Create time 2020/7/20
 *Create Yu
 *description:
 */
class NetStudy : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    var whyAsk: String = "我需要向你申请网络权限！"
    var permisions = arrayOf(android.Manifest.permission.INTERNET)
    val viewModel by lazyVm<MyViewModel>()

    companion object {
        private const val MAINFEST_PERMISION_INTERNET = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netstudy)
        getPermission()

    }


    private fun getPermission() {
        if (EasyPermissions.hasPermissions(this, *permisions)) {
            viewModel.getStudentInferMarion("201602020124")
            viewModel.studentLiveData.observe(this, Observer {
                tv_data.text = it.college
            })

        } else {
            //进行申请
            DialogUtils.tips(this, whyAsk) {
                RequestLocationAndCallPermission()
            }
        }
    }

    private fun RequestLocationAndCallPermission() {
        if (EasyPermissions.hasPermissions(this, *permisions)) {
        } else {
            EasyPermissions.requestPermissions(this, "请求网络权限", MAINFEST_PERMISION_INTERNET)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }


}