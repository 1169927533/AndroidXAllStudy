package com.example.a11699.comp_base.util

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.example.a11699.comp_base.util.toast.ToastUtil


fun toastCenter(context: Context, msgStr :CharSequence ){
    ToastUtil.show(context, msgStr, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0)
}
