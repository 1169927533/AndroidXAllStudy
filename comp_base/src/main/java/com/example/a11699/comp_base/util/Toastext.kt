package com.kd.base.extension

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.hipi.vm.BaseViewModel
import com.pince.toast.ToastUtil


fun toastCenter(context: Context, msgStr :CharSequence ){
    ToastUtil.show(context, msgStr, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0)
}

fun BaseViewModel.toastCenter(msg: String?){
    if (!TextUtils.isEmpty(msg)) {
        ToastUtil.show(getAppContext(), msg, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0)
    }
}