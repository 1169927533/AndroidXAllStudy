package com.example.a11699.androidxallstudy.customtab.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a11699.androidxallstudy.R
import kotlinx.android.synthetic.main.fragment_item.*

/**
 *Create time 2020/8/2
 *Create Yu
 *description:
 */
class ItemFragmetn : Fragment() {

    companion object {
        fun newInstance(item: String): ItemFragmetn {
            var fragment = ItemFragmetn()
            var bundle = Bundle()
            bundle.putString("title", item)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_item, null)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var content = arguments
        fragemnt_item_tv.text = content?.getString("title")
    }
}