package com.example.a11699.androidxallstudy.qiyutask

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_qiyutask.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author Yu
 * @Date 2020/12/3 11:07
 * @Description TODO
 */
class QiYuTaskACtivity : BaseActivity() {
    val job by lazy {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(4000)
            taskDialogFragment.show(supportFragmentManager, "taskDialog")
        }
    }
    private val taskDialogFragment by lazy {
        TaskDialogFragment()
    }

    override fun getLayoutId(): Int {
        return (R.layout.activity_qiyutask)
    }

    override fun initView() {
        btn_opentask.setOnClickListener {
            if (!taskDialogFragment.isAdded && supportFragmentManager.findFragmentByTag("taskDialog") == null) {
                taskDialogFragment.show(supportFragmentManager, "taskDialog")
            }
        }
        btn_opendialog.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                job.start()
            }
        }
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }

    override fun onPause() {
        super.onPause()
        Log.i("zjc", "onap")
        job.cancel()
    }
}