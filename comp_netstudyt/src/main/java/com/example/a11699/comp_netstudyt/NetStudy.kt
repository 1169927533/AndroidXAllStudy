package com.example.a11699.comp_netstudyt

import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.a11699.comp_netstudyt.dialog.TestDialog
import com.example.a11699.comp_netstudyt.util.DialogUtils
import com.example.a11699.comp_netstudyt.viewmodel.MyViewModel
import com.hipi.vm.lazyVm
import kotlinx.android.synthetic.main.activity_netstudy.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        tv_test.setOnClickListener {
            viewModel.changLiveDataValue("nestStudy里面设置的值")
        }
        tv_data.setOnClickListener {
            viewModel.getStudentInferMarion("201602020124")
        }
        btn_click.setOnClickListener {
            var test = TestDialog()
            test.show(supportFragmentManager, "test")
        }
        getPermission()
        observableData()



        btn_getdata.setOnClickListener {
            var data = getWebDateTime("http://d.meimingzan.com/ilk/develop")
        }
    }

    lateinit var date: Date// 转换为标准时间对象

    private fun getWebDateTime(webUrl: String) {
        useXieCheng(webUrl)
        useThread(webUrl)
    }


    private fun observableData() {
        viewModel.studentLiveData.observe(this, Observer {
            tv_data.text = it.college
        })
        viewModel.testLiveData.observe(this, Observer {
            tv_test.text = it.toString()
        })
    }


    private fun getPermission() {
        if (EasyPermissions.hasPermissions(this, *permisions)) {
            viewModel.getStudentInferMarion("201602020124")
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


    /**
     * 协程
     */
    private fun useXieCheng(webUrl: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA) // 输出北京时间
            //  s(webUrl)
            d(webUrl)
            tttt.text = sdf.format(date)
        }
    }

    suspend fun s(webUrl: String): Date = withContext(Dispatchers.IO) {
        val url = URL(webUrl) // 取得资源对象
        val uc: URLConnection = url.openConnection() // 生成连接对象
        uc.connect() // 发出连接
        val ld: Long = uc.date // 读取网站日期时间
        date = Date(ld)
        return@withContext date
    }

    /**
     * 开线程
     */
    private fun useThread(webUrl: String) {
        var thread = Thread() {
            val url = URL(webUrl) // 取得资源对象
            val uc: URLConnection = url.openConnection() // 生成连接对象
            uc.connect() // 发出连接
            val ld: Long = uc.date // 读取网站日期时间
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA) // 输出北京时间
            date = Date(ld)
            Log.i("thread", sdf.format(date))
        }
        thread.start()
    }


    suspend fun d(url: String): Date = suspendCoroutine<Date> {
        Thread(){
            val url = URL(url) // 取得资源对象
            val uc: URLConnection = url.openConnection() // 生成连接对象
            uc.connect() // 发出连接
            val ld: Long = uc.date // 读取网站日期时间
            date = Date(ld)
            it.resume(date)
        }.start()

    }

}