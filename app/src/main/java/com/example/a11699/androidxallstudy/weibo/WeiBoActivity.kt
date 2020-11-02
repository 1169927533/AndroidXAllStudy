package com.example.a11699.androidxallstudy.weibo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R

import kotlinx.android.synthetic.main.activity_weibo.*

/**
 *Create time 2020/10/30
 *Create Yu
 *description:
 */
class WeiBoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weibo)
        tv_result.itemClick = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        tv_result1.itemClick = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                tv_result1.text = s
                tv_result.text = s
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

}
