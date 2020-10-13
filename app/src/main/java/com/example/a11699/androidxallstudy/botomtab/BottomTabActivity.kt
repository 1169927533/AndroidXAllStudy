package com.example.a11699.androidxallstudy.botomtab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.botomtab.myanimal.ScaleTranslation
import com.example.a11699.androidxallstudy.botomtab.myanimal.ScaleTranslationOut
import com.example.a11699.androidxallstudy.customdropdown.DropDownActivity
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
        img.setImageResource(R.drawable.rotate_before);
        bottomTabLayout.addItemView(2, img)
        img.setOnClickListener {
            val intent = Intent(this, DropDownActivity::class.java)
            var bundle: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            startActivity(intent, bundle.toBundle())
            //     overridePendingTransition(0, R.anim.fade_out)
        }
    }

    private fun initEnterAndExitAnimal() {
        val enterTransition = ScaleTranslation(img1, this, "jin")
        val enterTransitionOut = ScaleTranslationOut(img1, this, "jin")

        enterTransition.addTarget(img1)
        enterTransition.duration = 500
        window.enterTransition = enterTransition
      //  window.exitTransition = enterTransitionOut



    }
}
