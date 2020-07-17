package com.example.a11699.androidxallstudy.botomtab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a11699.androidxallstudy.R

class BottomTabActivity : AppCompatActivity() {
    private var titles: ArrayList<String> = ArrayList()//菜单标题
    private var unSelectedImage: ArrayList<Int> = ArrayList()
    private var selectedImage: ArrayList<Int> = ArrayList()
    private fun initBottomData(title: String, unselected: Int, seleceImg: Int) {
        titles.add(title)
        unSelectedImage.add(unselected)
        selectedImage.add(seleceImg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tab)
        val dm = windowManager.defaultDisplay
        val screenWidth = dm.width
     //   bottomTabLayout.initBottomTabData(titles, selectedImage, unSelectedImage, screenWidth, this)

    }
}
