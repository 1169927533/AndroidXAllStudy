package com.example.a11699.androidxallstudy.soul.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.soul.adapter.SoulAdapter
import kotlinx.android.synthetic.main.activity_soul.*

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
class SoulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soul)
        var adapter = SoulAdapter()
        soulviewGroup.setAdapter(adapter)
        soulviewGroup.itemClick = {
            Toast.makeText(this, it.name,Toast.LENGTH_SHORT).show()
        }
    }
}