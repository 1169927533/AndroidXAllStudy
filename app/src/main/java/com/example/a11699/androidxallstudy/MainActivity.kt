package com.example.a11699.androidxallstudy

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.activity.NavigationActivity
import com.example.a11699.androidxallstudy.batteryview.BatteryActivity
import com.example.a11699.androidxallstudy.bitmap.BitStudyActivity
import com.example.a11699.androidxallstudy.botomtab.BottomTabActivity
import com.example.a11699.androidxallstudy.breath.BreathActivity
import com.example.a11699.androidxallstudy.codescroll.CodeScrollActivity
import com.example.a11699.androidxallstudy.cunstombarrage.BarrageActivity
import com.example.a11699.androidxallstudy.customdropdown.DropDownActivity
import com.example.a11699.androidxallstudy.custompasswordview.CustomPasswordActivity
import com.example.a11699.androidxallstudy.customtab.TabActivity
import com.example.a11699.androidxallstudy.funnyeffect.FunnyActivity
import com.example.a11699.androidxallstudy.lizixiaosan.LiZiActivity
import com.example.a11699.androidxallstudy.loadingactivity.WuBaActivity
import com.example.a11699.androidxallstudy.meinformation.MeInforMationActivity
import com.example.a11699.androidxallstudy.ossstudy.OssStudyActivity
import com.example.a11699.androidxallstudy.permissionstudy.PermissionStudyActivity
import com.example.a11699.androidxallstudy.piaodanmu.DanMuActivity
import com.example.a11699.androidxallstudy.sendgift.SendGiftActivity
import com.example.a11699.androidxallstudy.soul.activity.SoulActivity
import com.example.a11699.androidxallstudy.suspension.FloatActivity
import com.example.a11699.androidxallstudy.threed.ThreedActivity
import com.example.a11699.androidxallstudy.timepick.TimePickActivity
import com.example.a11699.androidxallstudy.tofuture.FutureActivity

import com.example.a11699.androidxallstudy.util.startActivity
import com.example.a11699.androidxallstudy.viewdraghelper.ViewDragerStudyActivity
import com.example.a11699.androidxallstudy.webview.WebViewStudy
import com.example.a11699.androidxallstudy.weibo.WeiBoActivity
import com.example.a11699.androidxallstudy.xmlwriteandread.XmlStudyActivity
import com.example.a11699.androidxallstudy.zhaunchang.TransLationActivity
import com.example.a11699.androidxallstudy.zhezhi.PaperActivity
import com.example.a11699.comp_base.SPContent
import com.example.a11699.comp_customview.QQStepViewActivity
import com.example.a11699.comp_im.activity.MessageActivity
import com.example.a11699.comp_imgpicker.ImgPickerActivity
import com.example.a11699.comp_live.MainLiveActivity
import com.example.a11699.comp_netstudyt.NetStudy
import com.example.a11699.comp_viewmodel.ViewModelStudyActivity
import com.example.a11699.lib_im.imloginutil.ImHelper
import com.example.a11699.lib_save.MMKVUtils
import com.example.a11699.module_flop.activity.FloapCardActivity
import com.example.a11699.module_smartrecycleview.SmartRecycleViewStudyActivity
import com.example.a11699.pack.NetStudyActivity
import com.tencent.imsdk.v2.V2TIMManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 *Create time 2020/6/17
 *Create Yu
 *description:
 */

class MainActivity : FloatActivity() {
    override fun getLayoutId(): Int {
     return R.layout.activity_main
    }

    override fun getToolBarTitle(): String {
        return "我是自定义的标题栏哦"
    }
    override fun isToolBarEnable(): Boolean {
        return false
    }

    override fun isTitleCenter(): Boolean {
        return true
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }

    override fun initView() {
        chkShowFloatWindow.setOnCheckedChangeListener { _, isChecked ->
            showFloatWindow(isChecked) //根据勾选值显示或隐藏悬浮窗
        }

        btn_permission.setOnClickListener { startActivity<PermissionStudyActivity>(this) }
        btn_webview.setOnClickListener { startActivity<WebViewStudy>(this) }
        btn_oss.setOnClickListener { startActivity<OssStudyActivity>(this) }
        // btn_greendao.setOnClickListener { startActivity<GreenDaoActivity>(this) }
        btn_barrage.setOnClickListener { startActivity<BarrageActivity>(this) }
        btn_bottomtab.setOnClickListener {
            var intent = Intent(this, BottomTabActivity::class.java)
            var bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, bundle)
            /*  startActivity<BottomTabActivity>(this) */
        }
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
        btn_battery.setOnClickListener { startActivity<BatteryActivity>(this) }
        btn_lizi.setOnClickListener { startActivity<LiZiActivity>(this) }
        btn_3d.setOnClickListener { startActivity<ThreedActivity>(this) }
        btn_3d_effect.setOnClickListener { startActivity<FunnyActivity>(this) }
        btn_zhezhi.setOnClickListener { startActivity<PaperActivity>(this) }
        btn_future.setOnClickListener { startActivity<FutureActivity>(this) }
        btn_codescroll.setOnClickListener { startActivity<CodeScrollActivity>(this) }
        btn_breath.setOnClickListener { startActivity<BreathActivity>(this) }
        btn_zhuanchang.setOnClickListener { startActivity<TransLationActivity>(this) }

        btn_floapcard.setOnClickListener { startActivity<FloapCardActivity>(this) }
        btn_db.setOnClickListener { startActivity<BitStudyActivity>(this) }
        btn_tvclick.setOnClickListener {
            startActivity<TvActivity>(this)
        }
        btn_ns.setOnClickListener {
            startActivity<NetStudyActivity>(this)
        }
        btn_weibo.setOnClickListener {
            startActivity<WeiBoActivity>(this)
        }
        btn_xml.setOnClickListener {
            startActivity<XmlStudyActivity>(this)
        }
        btn_quickclick.setOnClickListener {
            Toast.makeText(this,"按钮被点击了",Toast.LENGTH_SHORT).show()
        }

        btn_timepick.setOnClickListener {
            startActivity<TimePickActivity>(this)
        }

        btn_meinformation.setOnClickListener {
            startActivity<MeInforMationActivity>(this)
        }
    }

    override fun onResume() {
        super.onResume()
        chkShowFloatWindow.isChecked = FloatActivity.isShowing(this)

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