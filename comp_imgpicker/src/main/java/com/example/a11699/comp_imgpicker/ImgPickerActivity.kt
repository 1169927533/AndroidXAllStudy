package com.example.a11699.comp_imgpicker

import android.app.FragmentManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 *Create time 2020/7/2
 *Create Yu
 *description:图片选择
 */
class ImgPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aa)
       /* val fragmentManager = supportFragmentManager
        fragmentManager
                .beginTransaction()
                .add(MyFragemnt(), "RxPermissions.TAG")
                .commit()*/


    }

    private fun checkPermision() {
        var rxPermissions: RxPermissions = RxPermissions(this)
        //这个方法就是直接拿到所有的权限是否全部请求ok
        rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { aBoolean ->
                    if (aBoolean) {
                        //同意
                    } else {
                        //不同意Z
                    }
                }

        /*//这个方法可以逐条知道某个权限情况
        rxPermissions.requestEach(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PRIVILEGED)
                .subscribe { permission ->
                    if (permission.shouldShowRequestPermissionRationale) {

                    }

                }*/
    }
}