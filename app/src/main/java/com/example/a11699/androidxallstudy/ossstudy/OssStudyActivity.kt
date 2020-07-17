package com.example.a11699.androidxallstudy.ossstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import com.example.a11699.OssConfigBean
import com.example.a11699.OssService
import com.example.a11699.androidxallstudy.R
import com.example.a11699.Config
import kotlinx.android.synthetic.main.activity_oss_study.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * 需要先打开本地的后台demo D:\Workplace\IdeaWork\AppTokenServerDemo
 */
class OssStudyActivity : AppCompatActivity() {
    lateinit var ossService: OssService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oss_study)

        var ossConfigBean: OssConfigBean = OssConfigBean.Builder("http://oss-cn-hangzhou.aliyuncs.com", "", "http://192.168.137.1:7080/", "cangkuone", "ucklive/")
                .build()
        Config.initData(ossConfigBean)

        ossService = Config.initOss(this@OssStudyActivity, Config.OSS_ENDPOINT)

        btn_send.setOnClickListener {
            getNetWork()
            val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(i, 11)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == 11)) {
            val selectedImage = data!!.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage!!,
                    filePathColumn, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
            val picturePath = cursor!!.getString(columnIndex)
            cursor!!.close()
            ossService.asyncSendImg("test", picturePath)
            ossService.pushImgError = { _, _, _ ->
                Log.i("Zjc", "传失败了")
            }
            ossService.pushImgSuccess = {
                Log.i("Zjc", "传成功了")
            }
        }
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.arg1 == 11) {
                val jsonObj = JSONObject(msg.obj.toString())
                val statusCode: Int = jsonObj.getInt("StatusCode")
                if (statusCode == 200) {
                    val ak: String = jsonObj.getString("AccessKeyId")
                    val sk: String = jsonObj.getString("AccessKeySecret")
                    val token: String = jsonObj.getString("SecurityToken")
                    val expiration: String = jsonObj.getString("Expiration")
                    ossService = Config.initOss(this@OssStudyActivity, Config.OSS_ENDPOINT, ak, sk, token, expiration)
                } else {
                    Log.i("zjc", "报错啦")
                }
            }
        }
    }

    private fun getNetWork() {

        val client = OkHttpClient()
        val request: Request = Request.Builder().url(Config.STS_SERVER_URL).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var message = Message()
                message.obj = response.body?.string().toString()
                message.arg1 = 11
                handler.sendMessage(message)
            }
        })
    }
}

