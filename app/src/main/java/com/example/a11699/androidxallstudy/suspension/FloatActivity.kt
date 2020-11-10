package com.example.a11699.androidxallstudy.suspension

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.activity.BaseActivity
import kotlinx.android.synthetic.main.float_window.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Author Yu
 * @Date 2020/11/6 10:45
 * @Description 带悬浮窗操作的Activiti
 */
open abstract class FloatActivity : BaseActivity() {

    private var offX = 0
    private var offY = 0
    private var isPressing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                showFloatWindow(isShowing(application))
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                removeFloatWindow()
            }
        })
    }


    /**
     * 设置悬浮窗的展示和隐藏
     */
    protected fun showFloatWindow( isShow: Boolean) {
        val vgContent = findViewById<ViewGroup>(android.R.id.content)
     //   if (vgContent.childCount == 1) {
           vgContent.addView(FrameLayout(this))
       // }

        val vgFloatContainer = vgContent.getChildAt(3) as ViewGroup

        if (isShow) {
            if (vgFloatContainer.childCount == 0) {
                val viewFloat = buildFloadWindow()
                viewFloat.x = loadLocationX(this).toFloat()
                viewFloat.y = loadLocationY(this).toFloat()
                vgFloatContainer.addView(viewFloat)
            }
        } else {
            vgFloatContainer.removeAllViews()
        }
        setShowing(this, isFinishing)
    }

    /**
     * 定义一个悬浮窗 这个悬浮窗只是一个配普通的view
     * 可以自定义实现不同效果
     */
    fun buildFloadWindow(): View {
        val view = LayoutInflater.from(this).inflate(R.layout.float_window, null, false)
        view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.btnClose.setOnClickListener { showFloatWindow(false) }
        view.ll.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    offX = event.rawX.toInt() - view.x.toInt()
                    offY = event.rawY.toInt() - view.y.toInt()
                    isPressing = true
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    isPressing = false
                    saveLocation(this, view.x.toInt(), view.y.toInt())
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isPressing) {
                        view.x = event.rawX - offX
                        view.y = event.rawY - offY
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        return view
    }

    /**
     * 删除悬浮窗
     */
    protected fun removeFloatWindow() {
        val vgContent = findViewById<ViewGroup>(android.R.id.content)
        if (vgContent.childCount == 2)
            vgContent.removeViewAt(1)
    }

    /**
     * 保存和读取悬浮窗的参数到配置文件
     */
    companion object {
        private const val FILE = "floatWindow"
        fun isShowing(ctx: Context): Boolean {
            return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
                    .getBoolean("show", false)
        }

        private fun setShowing(ctx: Context, isShowing: Boolean) {
            ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
                    .edit { putBoolean("show", isShowing) }
        }

        private fun loadLocationX(ctx: Context): Int {
            return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
                    .getInt("x", 0)
        }

        private fun loadLocationY(ctx: Context): Int {
            return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
                    .getInt("y", 0)
        }

        private fun saveLocation(ctx: Context, x: Int, y: Int) {
            ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
                    .edit {
                        putInt("x", x)
                        putInt("y", y)
                    }
        }
    }
}