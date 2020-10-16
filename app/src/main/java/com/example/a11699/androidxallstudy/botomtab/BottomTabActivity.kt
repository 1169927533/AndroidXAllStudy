package com.example.a11699.androidxallstudy.botomtab

import android.animation.TimeInterpolator
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Transition
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.customdropdown.DropDownActivity
import com.example.a11699.comp_animalmehod2.TransitionHelper
import kotlinx.android.synthetic.main.activity_bottom_tab.*


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
        initEnterAndExitAnimal()


        titles.add("新秀")
        titles.add("觅友")
        titles.add("广场")
        titles.add("我的")
        unSelectedImage.add(R.drawable.table_icon_show_n)
        unSelectedImage.add(R.drawable.table_icon_chat_n)
        unSelectedImage.add(R.drawable.table_icon_home_n)
        unSelectedImage.add(R.drawable.table_icon_wode_n)
        selectedImage.add(R.drawable.table_icon_show_s)
        selectedImage.add(R.drawable.table_icon_chat_s)
        selectedImage.add(R.drawable.table_icon_home_s)
        selectedImage.add(R.drawable.table_icon_wode_s)
        val dm = windowManager.defaultDisplay
        val screenWidth = dm.width
        bottomTabLayout.initBottomTabData(titles, selectedImage, unSelectedImage, screenWidth, this)
        bottomTabLayout.itemClick = { position, view ->
            Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        }
        var img = ImageView(this)
        img.transitionName = "liquid"
        img.setImageResource(R.drawable.rotate_before);
        bottomTabLayout.addItemView(2, img)
        img.setOnClickListener {
            val intent = Intent(this, CopyTangApp::class.java)
            val pairs: Array<androidx.core.util.Pair<View, String>> = TransitionHelper.createSafeTransitionParticipants(this, true)
            var bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this@BottomTabActivity, *pairs).toBundle()
            // var bundle: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            startActivity(intent, bundle)
            /*  val intent = Intent(this, CopyTangApp::class.java)
              startActivity(intent)
              overridePendingTransition(0, R.anim.anim_zoom_in)*/
        }
    }

    private fun initEnterAndExitAnimal() {
        val enterTransition = Fade2()
        enterTransition.duration = 600
        enterTransition.excludeTarget(android.R.id.statusBarBackground, true);
        enterTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        window.exitTransition = enterTransition

    }
}
