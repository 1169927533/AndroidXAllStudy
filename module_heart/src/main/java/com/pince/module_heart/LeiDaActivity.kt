package com.pince.module_heart

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.a11699.comp_base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_leida.*

/**
 * @Author Yu
 * @Date 2020/12/28 14:02
 * @Description TODO
 */
class LeiDaActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_leida
    }

    override fun initView() {
        id_scan_circle.post {
            id_scan_circle.radarScanAnimal.start()
        }

        speadview.post {

        }

    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }


    override fun onDestroy() {
        super.onDestroy()
        id_scan_circle.radarScanAnimal.cancel()

    }
}