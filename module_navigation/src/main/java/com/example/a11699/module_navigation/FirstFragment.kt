package com.example.a11699.module_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_first.*

/**
 *Create time 2020/7/17
 *Create Yu
 *description:
 */
class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_first.setOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                        .navigate(R.id.action_placeholder_to_placeholder3)
            }

        }
    }
}