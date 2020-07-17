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
    lateinit var contextt: Context
    var titles: ArrayList<String>? = null
    var window_height = 0
    var screenWidth //屏幕宽度
            = 0
    var viewaddNum = 0 //通过addItemView方法添加的view数量

    var widthScale //一个item的宽度
            = 0
    private var image_width //图片的宽度
            = 0f
    private var image_height //图片的高度
            = 0f
    private var text_size //文字的大小
            = 0f
    private var text_color_s //文字选中的颜色
            = 0
    private var text_color_n //文字没选中的颜色
            = 0
    private var text_image_margintop //文字距离图片的高度
            = 0f
    private var image_center_height //中间图标的高度
            = 0f
    private var image_center_width //中间图标的宽度
            = 0f
    private var selectedImage: ArrayList<Int>? = null
    private var unSelectedImage: ArrayList<Int>? = null
    private var textViews = ArrayList<TextView>()
    private var imageViews: ArrayList<ImageView> = ArrayList<ImageView>()
    private var img_item_height = 0f
    var onItemClick: ((position: Int, view: LinearLayout) -> Unit)? = null//item的点击事件


    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet, 0)
    }


    fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationVieww, defStyleAttr, 0)
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

    fun initBottomTabData(titles: ArrayList<String>, selectedImage: ArrayList<Int>, unSelectedImage: ArrayList<Int>, screenWidth: Int, contextt: Context) {
        this.contextt = contextt
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
        if (titles != null && titles!!.size !== 0) {
            widthScale = screenWidth / (titles!!.size + viewaddNum)
            viewaddNum = 0
            for (i in 0 until titles!!.size) {
                val layout = LinearLayout(context)
                layout.orientation = VERTICAL
                layout.gravity = Gravity.CENTER
                val layoutLp = LayoutParams(widthScale, img_item_height as Int)
                layoutLp.gravity = Gravity.CENTER
                layout.layoutParams = layoutLp
                val image = ImageView(context)
                var layoutParams1: LayoutParams
                layoutParams1 = LayoutParams(image_width as Int, image_height as Int)
                image.setImageDrawable(unSelectedImage?.get(i)?.let { context.resources.getDrawable(it) })
                image.setLayoutParams(layoutParams1)
                val tv_title = TextView(context)
                val textLp = LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT)
                textLp.topMargin = px2dip(text_image_margintop)
                tv_title.textSize = text_size
                tv_title.setText(titles!!.get(i))
                tv_title.layoutParams = textLp
                layout.addView(image)
                layout.addView(tv_title)
                layout.setOnClickListener { v ->
                    val position = v.tag as Int
                    showTabSelectAnima(layout)
                    setColorLing(position)
                    onItemClick?.let {
                        it.invoke(position, layout)
                    }
                }
                layout.tag = i
                addView(layout, widthScale, img_item_height as Int)
                imageViews.add(image)
                textViews.add(tv_title)
            }
        }
    }
    /**
     * 选中
     */
    fun setColorLing(position: Int) {
        setColorDark()
        for (i in 0 until textViews.size) {
            if (position == i) {
                textViews.get(i).setTextColor(text_color_s)
                imageViews.get(i).setImageDrawable(context.resources.getDrawable(selectedImage!!.get(i)))
                break
            }
        }
    }

    /**
     * 没选中
     */
    fun setColorDark() {
        for (i in 0 until textViews.size) {
            textViews.get(i).setTextColor(text_color_n)
            imageViews.get(i).setImageDrawable(context.resources.getDrawable(unSelectedImage!!.get(i)))
        }
    }

    //展示动画
    private fun showTabSelectAnima(view: View) {
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f)
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f)
        val setAnima = AnimatorSet()
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
        val layout = LinearLayout(context)
        layout.orientation = VERTICAL
        layout.gravity = Gravity.CENTER
        var layoutLp = LayoutParams(widthScale,
                LayoutParams.WRAP_CONTENT)
        layoutLp.gravity = Gravity.CENTER
        layout.layoutParams = layoutLp
        layoutLp = LayoutParams(image_center_width as Int, image_center_height as Int)
        //  view.loadRes(R.drawable.botmenu_btn_start_live_n);
        view.setLayoutParams(layoutLp)
        view.setScaleType(ImageView.ScaleType.FIT_XY)
        layout.addView(view)
        addView(layout, position)
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}
