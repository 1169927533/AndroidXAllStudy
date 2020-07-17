package com.example.a11699.androidxallstudy.permissionstudy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R


class PermissionStudyActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peimission_study)
        checkPermississon()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermississon() {
        if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            requestCameraPermission()
        }
    }

    private val REQUEST_PERMISSION_CAMERA_CODE = 1

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode === REQUEST_PERMISSION_CAMERA_CODE) {
            val grantResult = grantResults[0]
            val granted = grantResult == PackageManager.PERMISSION_GRANTED
        }
    }

}
