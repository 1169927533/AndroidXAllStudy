package com.example.a11699.androidxallstudy.mylibrary

import android.text.TextUtils
import android.util.Log
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_base.util.toast.ToastUtil
import com.example.a11699.comp_base.util.toastCenter
import kotlinx.android.synthetic.main.activity_threelogin.*

/**
 * @Author Yu
 * @Date 2020/11/25 11:53
 * @Description 第三方登录
 */
class ThreeLoginActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_threelogin
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun initView() {
        btn_initthree.setOnClickListener {
           MobManager.instance.init(this)
        }

        btn_loginByWX.setOnClickListener {
            MobManager.instance.authorization { token, unionid, code ->
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(unionid)) {
                 //   splashVm.authorizationLogin(token!!, unionid!!)
                    ToastUtil.show(this@ThreeLoginActivity,"三方登录成功")
                    Log.i("zjc","${token}  ${unionid}  ${code}")
                } else {
                    runOnUiThread {
                        if (code == 8) {
                            toastCenter(
                                    this,
                         "未安装微信"
                            )
                        } else {
                            toastCenter(
                                   this,
                                 "授权失败"
                            )
                        }

                    }
                }
            }
        }
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }
}