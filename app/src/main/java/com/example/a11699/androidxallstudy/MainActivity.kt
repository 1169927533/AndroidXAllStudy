package com.example.a11699.androidxallstudy

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.activity.NavigationActivity
import com.example.a11699.androidxallstudy.botomtab.BottomTabActivity
import com.example.a11699.androidxallstudy.cunstombarrage.BarrageActivity
import com.example.a11699.androidxallstudy.ossstudy.OssStudyActivity
import com.example.a11699.androidxallstudy.permissionstudy.PermissionStudyActivity
import com.example.a11699.androidxallstudy.util.startActivity
import com.example.a11699.androidxallstudy.viewdraghelper.ViewDragerStudyActivity
import com.example.a11699.androidxallstudy.webview.WebViewStudy
import com.example.a11699.comp_customview.QQStepViewActivity
import com.example.a11699.comp_imgpicker.ImgPickerActivity
import com.example.a11699.comp_viewmodel.ViewModelStudyActivity

import kotlinx.android.synthetic.main.activity_main.*

/**
 *Create time 2020/6/17
 *Create Yu
 *description:
 */
class MainActivity : AppCompatActivity() {

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
        btn_myviewmodel.setOnClickListener { startActivity<ViewModelStudyActivity>(this)}
        btn_navigation.setOnClickListener { startActivity<NavigationActivity>(this)}

    }


}