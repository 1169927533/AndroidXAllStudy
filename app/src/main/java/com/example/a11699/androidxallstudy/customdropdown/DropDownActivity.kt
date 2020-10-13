package com.example.a11699.androidxallstudy.customdropdown

import android.R.array
import android.graphics.PointF.length
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.botomtab.myanimal.ScaleTranslation
import com.example.a11699.androidxallstudy.customdropdown.MySpinnerDialog.OnSpinnerItemClick
import kotlinx.android.synthetic.main.activity_bottom_tab.*
import kotlinx.android.synthetic.main.activity_dropdown.*
import org.json.JSONObject


/**
 *Create time 2020/7/23
 *Create Yu
 *description:
 */
class DropDownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropdown)
        initEnterAndExitAnimal()
        val list: MutableList<String> = ArrayList()

        list.add("123")
        list.add("12w13")
        list.add("123f")
        list.add("12sss3")
        my_spinner1.setDatas(list)
        my_spinner1.setOnSpinnerItemClick(OnSpinnerItemClick { i, str ->
            //得到返回
            finishAfterTransition()
        })
    }
    private fun initEnterAndExitAnimal() {
        val enterTransition = ScaleTranslation(my_spinner1,this,"jin")
        /*    enterTransition.addTarget(tvb)*/
        enterTransition.duration = 500
        window.enterTransition = enterTransition
      //  window.exitTransition = enterTransition

        /*    window.exitTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_right);
            window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_right);*/
    }
}