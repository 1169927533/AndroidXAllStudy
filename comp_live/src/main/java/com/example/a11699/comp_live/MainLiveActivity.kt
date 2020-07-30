package com.example.a11699.comp_live

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.comp_base.Util
import com.example.a11699.comp_live.adapter.BaseAdapter
import com.example.a11699.comp_live.customview.MyVideoView
import com.example.a11699.comp_live.manager.PagerLayoutManager
import kotlinx.android.synthetic.main.activity_main_live.*

/**
 *Create time 2020/7/30
 *Create Yu
 *description:
 */
class MainLiveActivity : AppCompatActivity() {
    var listData = ArrayList<Int>()
    private val VIDEO_PATH = "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4"

    private val intarray = intArrayOf(R.drawable.hzw_a, R.drawable.hzw_b,
            R.drawable.hzw_d, R.drawable.hzw_e, R.drawable.hzw_f, R.drawable.hzw_h,
            R.drawable.hzw_i, R.drawable.hzw_j, R.drawable.hzw_k)

    private val adapter by lazy {
        BaseAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_live)
        initData()
        initRecycleview()
    }

    private fun initData() {
        for (value in intarray) {
            listData.add(value)
        }
    }

    val myVideoView by lazy {
        MyVideoView(this@MainLiveActivity)
    }

    private fun initRecycleview() {
        mainrecycleview.adapter = adapter
        mainrecycleview.layoutManager = PagerLayoutManager(this).apply {
            setViewGroup(myVideoView, object : PagerLayoutManager.IreloadInterface {
                override fun onReloadPage(position: Int, isBottom: Boolean, view: View?) {
                    if (position == -1000) {
                        Util.printLog(0.toString())
                    } else {
                        Util.printLog(position.toString())
                    }
                    showVideo(VIDEO_PATH)
                }

                override fun onDestroyPage(isNext: Boolean, position: Int, view: View?) {

                }
            })
        }
        adapter.setNewData(listData)
    }

    private fun showVideo(videoPath: String) {
        myVideoView.stop()
        myVideoView.setVideoPath(videoPath)
        myVideoView.start()
    }
}