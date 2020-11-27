package com.example.mylibrary

import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.*
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_base.arouter.RouterConstant
import kotlinx.android.synthetic.main.activity_webp.*

/**
 * @Author Yu
 * @Date 2020/11/27 10:09
 * @Description webp资源
 */
@Route(path = RouterConstant.Main.MAIN)
class LoadWebpActivity:BaseActivity() {
    var url = "https://lk20.oss-cn-hongkong.aliyuncs.com/gift/effects/liner.webp"
    private var mBitmapTrans: Transformation<Bitmap>?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_webp
    }

    override fun initView() {
        trans_selector.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mBitmapTrans = when (position) {
                    0 -> null
                    1 -> CenterCrop()
                    2 -> CircleCrop()
                    3 -> RoundedCorners(24)
                    4 -> CenterInside()
                    5 -> FitCenter()
                    else -> null
                }
                loadImageWithTransformation(webp_image,url)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        loadImage(webp_image,url)
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }

    private fun loadImage(imageView: ImageView, url: String) {
        GlideApp.with(this) //.asBitmap()
                .load(url)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error) //.set(WebpFrameLoader.FRAME_CACHE_STRATEGY, WebpFrameCacheStrategy.AUTO)
                .into(imageView)
    }
    private fun loadImageWithTransformation(imageView: ImageView, url: String) {
        mBitmapTrans?.let {
            GlideApp.with(this) //.asBitmap()
                    .load(url)
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .optionalTransform(it)
                    .optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(mBitmapTrans)) //.set(WebpFrameLoader.FRAME_CACHE_STRATEGY, WebpFrameCacheStrategy.AUTO)
                    .into(imageView)
        }
    }
}