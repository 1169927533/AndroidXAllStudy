package com.example.a11699.comp_im.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.a11699.comp_im.R
import com.example.a11699.comp_im.fragment.InputMoreFragment
import com.example.a11699.comp_im.util.InputUtil
import com.example.a11699.lib_im.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_aaaaa.*


/**
 *Create time 2020/9/9
 *Create Yu
 *description:
 */
class AAAA : AppCompatActivity() {
    private var mFragmentManager: FragmentManager? = null
    private var inputMoreFragment: InputMoreFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aaaaa)
        if (mFragmentManager == null) {
            mFragmentManager = supportFragmentManager
        }
        if (inputMoreFragment == null) {
            inputMoreFragment = InputMoreFragment()
        }
        mFragmentManager!!.beginTransaction().replace(R.id.aaa_fra, inputMoreFragment!!).commitAllowingStateLoss()

        btn_click.setOnClickListener {
            if (ScreenUtil.isSoftShowing(btn_click)) {
                InputUtil.hideSoftInput(edaaa, this)
            } else {
                InputUtil.showSoftInout(edaaa, this)
            }
        }
    }
}