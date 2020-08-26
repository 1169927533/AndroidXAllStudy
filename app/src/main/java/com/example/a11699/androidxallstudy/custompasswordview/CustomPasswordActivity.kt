package com.example.a11699.androidxallstudy.custompasswordview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.lzp.zedittex.ZEditText
import kotlinx.android.synthetic.main.activity_custonpasswordview.*

/**
 *Create time 2020/8/26
 *Create Yu
 *description:自定义密码输入框
 */
class CustomPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custonpasswordview)


        edit_1.setOnEditCompleteListener(object : ZEditText.OnEditCompleteListener {
            override fun onEditComplete(text: String) {
                Toast.makeText(this@CustomPasswordActivity, "edit_1 text: $text", Toast.LENGTH_SHORT).show()
            }

        })

        edit_2.setOnEditCompleteListener(object : ZEditText.OnEditCompleteListener {
            override fun onEditComplete(text: String) {
                Toast.makeText(this@CustomPasswordActivity, "edit_2 text: $text", Toast.LENGTH_SHORT).show()
            }

        })

        edit_3.setOnEditCompleteListener(object : ZEditText.OnEditCompleteListener {
            override fun onEditComplete(text: String) {
                Toast.makeText(this@CustomPasswordActivity, "edit_3 text: $text", Toast.LENGTH_SHORT).show()
            }

        })

    }
}