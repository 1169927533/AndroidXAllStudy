package com.example.a11699.androidxallstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.util.startActivity
import com.example.a11699.comp_base.SPContent
import com.example.a11699.lib_im.imloginutil.ImHelper
import com.example.a11699.lib_save.MMKVUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 *Create time 2020/9/2
 *Create Yu
 *description:
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var uid = MMKVUtils.get(SPContent.Login.SpName, this)!!.readString(SPContent.Login.LoginId)
        if (uid != null) {
            ImHelper.loginIm(uid, success = {
                MMKVUtils.get(SPContent.Login.SpName, this)!!.saveData(SPContent.Login.LoginId, uid)
                startActivity<MainActivity>(this)
                finish()
                Log.i(ImHelper.Tag, "Login Im  成功")
            }, error = {
                Log.i(ImHelper.Tag, "Login Im  失败")
            })
        }
        btn_login_im.setOnClickListener {
            ImHelper.loginIm(edit_name.text.toString(), success = {
                MMKVUtils.get(SPContent.Login.SpName, this).saveData(SPContent.Login.LoginId, edit_name.text.toString())
                startActivity<MainActivity>(this)
                finish()
                Log.i(ImHelper.Tag, "Login Im  成功")
            }, error = {
                Log.i(ImHelper.Tag, "Login Im  失败")
            })
        }
    }
}