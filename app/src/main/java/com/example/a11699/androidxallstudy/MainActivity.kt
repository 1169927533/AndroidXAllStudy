package com.example.a11699.androidxallstudy

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.activity.NavigationActivity
import com.example.a11699.androidxallstudy.botomtab.BottomTabActivity
import com.example.a11699.androidxallstudy.cunstombarrage.BarrageActivity
import com.example.a11699.androidxallstudy.customdropdown.DropDownActivity
import com.example.a11699.androidxallstudy.custompasswordview.CustomPasswordActivity
import com.example.a11699.androidxallstudy.customtab.TabActivity
import com.example.a11699.androidxallstudy.loadingactivity.WuBaActivity
import com.example.a11699.androidxallstudy.ossstudy.OssStudyActivity
import com.example.a11699.androidxallstudy.permissionstudy.PermissionStudyActivity
import com.example.a11699.androidxallstudy.piaodanmu.DanMuActivity
import com.example.a11699.androidxallstudy.sendgift.SendGiftActivity
import com.example.a11699.androidxallstudy.soul.activity.SoulActivity
import com.example.a11699.androidxallstudy.util.startActivity
import com.example.a11699.androidxallstudy.viewdraghelper.ViewDragerStudyActivity
import com.example.a11699.androidxallstudy.webview.WebViewStudy
import com.example.a11699.comp_base.SPContent
import com.example.a11699.comp_customview.QQStepViewActivity
import com.example.a11699.comp_im.activity.MessageActivity
import com.example.a11699.comp_imgpicker.ImgPickerActivity
import com.example.a11699.comp_live.MainLiveActivity
import com.example.a11699.comp_netstudyt.NetStudy
import com.example.a11699.comp_viewmodel.ViewModelStudyActivity
import com.example.a11699.lib_im.imloginutil.ImHelper
import com.example.a11699.lib_save.MMKVUtils
import com.example.a11699.module_smartrecycleview.SmartRecycleViewStudyActivity
import com.tencent.imsdk.v2.V2TIMManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 *Create time 2020/6/17
 *Create Yu
 *description:
 */
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_permission.setOnClickListener { startActivity<PermissionStudyActivity>(this) }
        btn_webview.setOnClickListener { startActivity<WebViewStudy>(this) }
        btn_oss.setOnClickListener { startActivity<OssStudyActivity>(this) }
        // btn_greendao.setOnClickListener { startActivity<GreenDaoActivity>(this) }
        btn_barrage.setOnClickListener { startActivity<BarrageActivity>(this) }
        btn_bottomtab.setOnClickListener { startActivity<BottomTabActivity>(this) }
        btn_imgpicker.setOnClickListener { startActivity<ImgPickerActivity>(this) }
        btn_qqstep.setOnClickListener { startActivity<QQStepViewActivity>(this) }
        btn_viewdrager.setOnClickListener {
            Toast.makeText(this, "das", Toast.LENGTH_SHORT).show()
            startActivity<ViewDragerStudyActivity>(this)
        }
        beauty_item_seekbar.setOnSeekBarChangeListener { progress ->
        }
        btn_myviewmodel.setOnClickListener { startActivity<ViewModelStudyActivity>(this) }
        btn_navigation.setOnClickListener { startActivity<NavigationActivity>(this) }
        btn_network.setOnClickListener { startActivity<NetStudy>(this) }
        btn_sendgift.setOnClickListener { startActivity<SendGiftActivity>(this) }
        btn_dropdown.setOnClickListener { startActivity<DropDownActivity>(this) }
        btn_soul.setOnClickListener { startActivity<SoulActivity>(this) }
        btn_live.setOnClickListener { startActivity<MainLiveActivity>(this) }
        btn_danmu.setOnClickListener { startActivity<DanMuActivity>(this) }
        btn_tab.setOnClickListener { startActivity<TabActivity>(this) }
        btn_wubaloading.setOnClickListener { startActivity<WuBaActivity>(this) }
        btn_smartrecycleview.setOnClickListener { startActivity<SmartRecycleViewStudyActivity>(this) }
        btn_custompassword.setOnClickListener { startActivity<CustomPasswordActivity>(this) }
        btn_im.setOnClickListener { startActivity<MessageActivity>(this) }
    }

    override fun onResume() {
        super.onResume()
        when (V2TIMManager.getInstance().loginStatus) {
            V2TIMManager.V2TIM_STATUS_LOGOUT -> {
                //无登陆 app闪退了 进行一个二次登录
                loginApp()
            }
        }

    }


    private fun loginApp() {
        var uid = MMKVUtils.get(SPContent.Login.SpName, this)!!.readString(SPContent.Login.LoginId)
        if (uid != null) {
            ImHelper.loginIm(uid, success = {
                MMKVUtils.get(SPContent.Login.SpName, this)!!.saveData(SPContent.Login.LoginId, uid)
                startActivity<MainActivity>(this)
                finish()
            }, error = {
            })
        }
    }
}