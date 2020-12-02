package com.example.module_webview

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_base.arouter.RouterConstant

/**
 * @Author Yu
 * @Date 2020/12/1 16:00
 * @Description 加载webview
 */
@Route(path = RouterConstant.Web.MAIN)
class WebActivity : BaseActivity() {

    lateinit var webFragment: WebFragment

    @Autowired(name = RouterConstant.Web.KEY_WEB_MODEL, required = true)
    @JvmField
    var mWebTransportModel: WebTransportModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun isTitleCenter(): Boolean {
        return true
    }

    override fun getToolBarTitle(): String {
        return "用户等级"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initView() {
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
        mWebTransportModel?.let {
            Log.i("Zkc", it.title + "  " + it.uid + "  " + it.url)
            webFragment = supportFragmentManager.findFragmentById(R.id.webfragment) as WebFragment
            webFragment.setWebTransportModel(mWebTransportModel!!)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK && keyBack()
    }

    private fun keyBack(): Boolean {
        return webFragment.keyBack()
    }
}