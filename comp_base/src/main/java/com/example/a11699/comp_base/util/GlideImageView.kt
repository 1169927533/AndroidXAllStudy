package com.example.a11699.comp_base.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 *Create time 2020/9/2
 *Create Yu
 *description:
 */
class GlideImageView @kotlin.jvm.JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun loadUrl(url: String?, options: RequestOptions? = null) {
        var requestBuilder = Glide.with(context)
                .load(url)
        if (options != null) {
            requestBuilder.apply(options)
        }
        requestBuilder.into(this)
    }

    fun loadCircleUrl(url: String, options: RequestOptions? = null) {
        var requestBuilder = Glide.with(context)
                .load(url)
        var ops = options
        if (ops == null) {
            ops = RequestOptions()
        }
        ops.circleCrop()
        requestBuilder.apply(ops)
                .into(this)
    }
}