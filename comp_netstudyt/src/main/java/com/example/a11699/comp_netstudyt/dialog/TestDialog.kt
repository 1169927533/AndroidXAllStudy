package com.example.a11699.comp_netstudyt.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a11699.comp_netstudyt.R
import com.example.a11699.comp_netstudyt.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.dialog_test.*

/**
 *Create time 2020/7/27
 *Create Yu
 *description:
 */
class TestDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Transparent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_test, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var viewModel =   ViewModelProvider(this).get(MyViewModel::class.java)
        btn_click.setOnClickListener {
            viewModel?.testLiveData?.value = "哈哈哈"
        }
        viewModel?.testLiveData?.observe(viewLifecycleOwner, Observer {
            dismiss()
        })

    }
}