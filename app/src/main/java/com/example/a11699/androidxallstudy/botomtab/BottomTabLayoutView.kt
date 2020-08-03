package com.example.a11699.androidxallstudy.botomtab

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.a11699.androidxallstudy.R


/**
 *Create time 2020/6/22
 *Create Yu
 *description:底部导航栏
 */
class BottomTabLayoutView : LinearLayout {
    private var mContext: Context? = null
    var titles: ArrayList<String>? = null
    var window_height = 0
    var screenWidth = 0//屏幕宽度
    var viewaddNum = 0 //通过addItemView方法添加的view数量
    var widthScale = 0 //一个item的宽度
    private var image_width = 0f //图片的宽度
    private var image_height = 0f //图片的高度
    private var text_size = 0f //文字的大小
    private var text_color_s = 0//文字选中的颜色
    private var text_color_n = 0//文字没选中的颜色
    private var text_image_margintop = 0f //文字距离图片的高度
    private var image_center_height = 0f //中间图标的高度
    private var image_center_width = 0f //中间图标的宽度
    private var selectedImage: ArrayList<Int>? = null
    private var unSelectedImage: ArrayList<Int>? = null
    private var textViews = ArrayList<TextView>()
    private var imageViews = ArrayList<ImageView>()
    private var img_item_height = 0f
    var itemClick: ((position: Int, view: LinearLayout) -> Unit)? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        init(attributeSet, 0)
    }

    //
    fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationVieww, defStyleAttr, 0)
        image_height = typedArray.getDimension(R.styleable.NavigationVieww_image_height, 40f)
        image_width = typedArray.getDimension(R.styleable.NavigationVieww_image_width, 40f)
        text_size = px2dip(typedArray.getDimension(R.styleable.NavigationVieww_text_size, 12f)).toFloat()
        text_color_n = typedArray.getColor(R.styleable.NavigationVieww_text_noneselectcolor, 999999)
        text_color_s = typedArray.getColor(R.styleable.NavigationVieww_text_selectcolor, 0)
        text_image_margintop = typedArray.getDimension(R.styleable.NavigationVieww_text_image_margintop, 2f)
        image_center_height = typedArray.getDimension(R.styleable.NavigationVieww_image_center_height, 46f)
        img_item_height = typedArray.getDimension(R.styleable.NavigationVieww_img_item_height, 49f)
        image_center_width = typedArray.getDimension(R.styleable.NavigationVieww_image_center_width, 46f)
        typedArray.recycle()
    }

    fun initBottomTabData(titles: ArrayList<String>, selectedImage: ArrayList<Int>, unSelectedImage: ArrayList<Int>, screenWidth: Int, context: Context) {
        mContext = context
        this.titles = titles
        this.screenWidth = screenWidth
        this.selectedImage = selectedImage
        this.unSelectedImage = unSelectedImage
        setLayout()
    }

    fun removeBottomTab(index: Int) {
        titles?.removeAt(index)
        selectedImage?.removeAt(index)
        unSelectedImage?.removeAt(index)
        setLayout()
    }

    fun setLayout() {
        imageViews.clear()
        textViews.clear()
        removeAllViews()
        orientation = HORIZONTAL
        if (titles != null && titles!!.size != 0) {
            widthScale = screenWidth / (titles!!.size + viewaddNum)
            viewaddNum = 0
            for (i in titles!!.indices) {
                var layout = LinearLayout(context)
                layout.orientation = VERTICAL
                layout.gravity = Gravity.CENTER
                var layoutLp = LayoutParams(widthScale, img_item_height.toInt())
                layoutLp.gravity = Gravity.CENTER
                layout.layoutParams = layoutLp
                var image = ImageView(context)
                var layoutParams1: LayoutParams
                layoutParams1 = LayoutParams(image_width.toInt(), image_height.toInt())
                image.setImageDrawable(context.resources.getDrawable(unSelectedImage!![i]))
                image.layoutParams = layoutParams1
                var tv_title = TextView(context)
                var textLp = LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT)
                textLp.topMargin = px2dip(text_image_margintop)
                tv_title.textSize = text_size
                tv_title.text = titles!![i]
                tv_title.layoutParams = textLp

                layout.addView(image)
                layout.addView(tv_title)
                layout.setOnClickListener { v ->
                    var position = v.tag as Int
                    showTabSelectAnima(layout)
                    setColorLing(position)
                    itemClick?.let {
                        it.invoke(position, layout)
                    }
                }
                layout.tag = i
                addView(layout, widthScale, img_item_height.toInt())
                imageViews.add(image)
                textViews.add(tv_title)
            }
        }
    }

    /**
     * 底部导航点击接口回调
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int, view: LinearLayout?)
    }

    /**
     * 选中
     */
    fun setColorLing(position: Int) {
        setColorDark()
        for (i in textViews.indices) {
            if (position == i) {
                textViews[i].setTextColor(text_color_s)
                imageViews[i].setImageDrawable(context.resources.getDrawable(selectedImage!![i]))
                break
            }
        }
    }

    /**
     * 没选中
     */
    fun setColorDark() {
        for (i in textViews.indices) {
            textViews[i].setTextColor(text_color_n)
            imageViews[i].setImageDrawable(context.resources.getDrawable(unSelectedImage!![i]))
        }
    }

    //展示动画
    private fun showTabSelectAnima(view: View) {
        var scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f)
        var scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f)
        var setAnima = AnimatorSet()
        setAnima.play(scaleX).with(scaleY)
        setAnima.duration = 500
        setAnima.start()
    }

    //默认选中的item选项设置
    fun selectItem(position: Int): View? {
        setColorLing(position)
        return getChildAt(0)
    }

    fun addItemView(position: Int, view: ImageView) {
        viewaddNum = 1
        setLayout()
        var layout = LinearLayout(context)
        layout.orientation = VERTICAL
        layout.gravity = Gravity.CENTER
        var layoutLp = LayoutParams(widthScale,
                LayoutParams.WRAP_CONTENT)
        layoutLp.gravity = Gravity.CENTER
        layout.layoutParams = layoutLp
        layoutLp = LayoutParams(image_center_width.toInt(), image_center_height.toInt())
        //  view.loadRes(R.drawable.botmenu_btn_start_live_n);
        view.layoutParams = layoutLp
        view.scaleType = ImageView.ScaleType.FIT_XY
        layout.addView(view)
        addView(layout, position)
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        var scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}
