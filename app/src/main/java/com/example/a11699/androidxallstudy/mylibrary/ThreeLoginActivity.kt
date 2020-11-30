package com.example.mylibrary

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_base.arouter.RouterConstant
import com.example.a11699.comp_base.util.toastCenter
import com.example.util.MobManager
import com.mob.MobSDK
import kotlinx.android.synthetic.main.activity_threelogin.*

/**
 * @Author Yu
 * @Date 2020/11/25 11:53
 * @Description 第三方登录
 */
@Route(path = RouterConstant.Three.MAIN)
class ThreeLoginActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_threelogin
    }

    override fun initView() {
        btn_initthree.setOnClickListener {
           MobManager.instance.init(this)
        }

        btn_loginByWX.setOnClickListener {
            MobManager.instance.authorization { token, unionid, code ->
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(unionid)) {
                 //   splashVm.authorizationLogin(token!!, unionid!!)
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