package com.example.a11699.androidxallstudy.permissionstudy

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.Util
import kotlinx.android.synthetic.main.activity_peimission_study.*


class PermissionStudyActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peimission_study)
        checkPermississon()
        studyMatrix()
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

    fun studyMatrix() {
        var bimap = BitmapFactory.decodeResource(resources, R.drawable.img1)
        Util.printLog("图片宽高")
        Util.printLog("宽：${bimap.width}  高：${bimap.height}")

        var rotateMatrix = Matrix()
        var rectLocation = FloatArray(9)



        rotateMatrix.preRotate(45f)
        //  rotateMatrix.preTranslate(bimap.width / 2.toFloat(), bimap.height / 2.toFloat())
        rotateMatrix.getValues(rectLocation)
        for (value in rectLocation) {
            Util.printLog("" + value)
        }
        Util.printLog("平移后==================================")

        tvvv.setText("卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽卧槽")
        tvvv.setScrollMode(MarQueeTextView.SCROLL_FAST);

    }

}
