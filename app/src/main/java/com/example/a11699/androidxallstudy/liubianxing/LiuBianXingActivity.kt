package com.example.a11699.androidxallstudy.liubianxing

import com.bumptech.glide.Glide
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import com.example.a11699.comp_customview.pentagonal.PentagonaltTransform
import kotlinx.android.synthetic.main.activity_barrage.*

/**
 * @Author Yu
 * @Date 2020/12/17 15:05
 * @Description 六边形activity
 * 1. 自定义Glide展示图片形状
 * 2. 自定义六边形的imgview
 */
class LiuBianXingActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_liubianxing
    }

    override fun initView() {
        img.post{
            Glide.with(this).load("https://00imgmini.eastday.com/mobile/20201213/20201213183953_a405bf0a5068b68f0856e5e6c686bfb6_3_mwpm_05501609.jpg")
                    //  .apply(RequestOptions.bitmapTransform(PentagonaltTransform()))
                    .transform(PentagonaltTransform(img.width.toFloat()))
                    .into(img)
        }
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {
    }
}