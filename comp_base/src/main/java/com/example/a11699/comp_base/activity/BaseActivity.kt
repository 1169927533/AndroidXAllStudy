package com.example.a11699.comp_base.activity

/**
 * @Author Yu
 * @Date 2020/11/10 16:58
 * @Description 基类  可以动态展示or隐藏标题栏
 */

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.a11699.comp_base.R
import com.example.a11699.comp_base.util.BarUtils
import com.example.a11699.comp_base.util.ClickUtil

/**
 *Create time 2020/9/11
 *Create Yu
 *description:基类 做了状态栏适配方案
 */
abstract class BaseActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    var mContentView: View? = null
    /**
     * 根布局
     */
    private lateinit var mRootView: ViewGroup

    var mToolbar: Toolbar? = null
        private set
    private var centerTitle: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentView = createContainerView(layoutInflater, getLayoutId())
        if (mContentView == null) {
            finish()
            return
        }
        setContentView(mContentView)
        initToolBar()
        initToolBatStyle()
        initView()
        observeLiveData()
        initViewData()

    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun observeLiveData() //订阅vm

    abstract fun initViewData()

    /**
     * 创建containerview
     *
     * @param inflater
     * @param layoutResID 你自己的布局id
     * @return
     */
    open fun createContainerView(inflater: LayoutInflater, layoutResID: Int): View? {
        return if (layoutResID <= 0) {
            null
        } else inflater.inflate(layoutResID, null)
    }

    //初始化顶部状态栏样式
    private fun initToolBatStyle() {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)
        mToolbar?.let { BarUtils.addMarginTopEqualStatusBarHeight(it) }
    }

    private fun initToolBar() {
        val mInflater = getLayInflater()

        mRootView = findViewById(android.R.id.content)
        if (isToolBarEnable()) {

            var toolbarLayoutId = 0
            toolbarLayoutId = if (isTitleCenter()) {
                R.layout.activity_base_toolbar_center
            } else {
                R.layout.activity_base_toolbar_normal
            }
            mToolbar = mInflater.inflate(toolbarLayoutId, null) as Toolbar
            var barHeight =
                    resources.getDimensionPixelOffset(R.dimen.abc_action_bar_default_height_material)
            mRootView.addView(
                    mToolbar, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    barHeight
            )
            )
            if (isTitleCenter()) {
                centerTitle = mToolbar?.findViewById(R.id.toolbar_title_tv)
            }

            val mode = requestToolbarMode()
            if (mode == 1) {
                val params = mContentView!!.layoutParams as FrameLayout.LayoutParams
                params?.let {
                    it.setMargins(
                            params.leftMargin,
                            barHeight + if (isToolBarEnable()) {
                                BarUtils.getStatusBarHeight()
                            } else {
                                0
                            },
                            params.rightMargin,
                            params.bottomMargin
                    )
                    mContentView!!.layoutParams = it

                }

            }

            setToolbarTitle(getToolBarTitle(), getToolBarTitleColor(), getToolBarTitleSize())
            setupToolbar()
        }
    }

    open fun getToolBarTitle(): String {
        return title as String
    }

    open fun getToolBarTitleSize(): Float {
        return 19f
    }

    open fun getToolBarTitleColor(): Int {
        return R.color.color_black
    }

    /**
     * 是否展示toolbar
     *
     * @return
     */
    open fun isToolBarEnable(): Boolean {
        return false
    }

    /**
     * 设置是否toolbar标题居中
     *
     * @return
     */
    open fun isTitleCenter(): Boolean {
        return false
    }

    /**
     * 是否显示返回键。默认显示
     *
     * @return
     */
    open fun homeAsUpEnable(): Boolean {
        return true
    }

    private fun getLayInflater(): LayoutInflater {
        return LayoutInflater.from(this)
    }

    open fun requestToolbarMode(): Int {
        return 1
    }

    private fun setToolbarTitle(title: CharSequence, color: Int, size: Float) {
        if (mToolbar != null) {
            if (isTitleCenter()) {
                centerTitle?.text = title
                centerTitle?.setTextColor(resources.getColor(color))
                centerTitle?.textSize = size
            } else {
                if (mToolbar != null) {
                    mToolbar?.title = title
                }
            }
        }
    }


    /**
     * setup  toolbar
     */
    /**
     * 返回键
     *
     * @return
     */
    @DrawableRes
    open fun requestNavigationIcon(): Int {
        return -1
    }

    private fun setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            //显示返回键
            supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnable())
            //可响应返回键点击事件
            supportActionBar?.setDisplayShowHomeEnabled(true)
            //返回键
            requestNavigationIcon().let {
                if (it > 0) {
                    supportActionBar?.setHomeAsUpIndicator(it)
                }
            }

            //是否展示默认标题
            if (isTitleCenter()) {
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            val toolbarBg = requestToolBarBackground()
            mToolbar?.background = toolbarBg
            mToolbar?.setOnTouchListener(toolbarTouch)
            mToolbar?.setOnMenuItemClickListener(this)
            mToolbar?.setNavigationOnClickListener(View.OnClickListener {
                if (ClickUtil.isClickAvalible()) {
                    onBackPressed()
                }
            })
        }
    }

    /**
     * 监听toolbar的touch事件，隐藏键盘
     */
    private val toolbarTouch = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            //   hideSoftInputView()
        }
        false
    }

    /**
     * 设置toolbar 背景样式
     */
    open fun requestToolBarBackground(): Drawable? {
        return null
    }

    /**
     * 监听menu的点击事件
     */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
            }
        }
        return false
    }
}