package com.example.a11699.androidxallstudy.zhaunchang.drawable

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import com.example.a11699.androidxallstudy.R

/**
 *Create time 2020/10/16
 *Create Yu
 *description:支持中间出现透明度的drawable
 * 通过设置setSrcPath设定透明区域的形状
 */
class CustomDrawable : Drawable {
    private var srcPaint: Paint? = null
    private var srcPath: Path = Path()

    private var innerDrawable: Drawable? = null

    constructor(context: Context) {
        this.innerDrawable = innerDrawable;
        srcPath.addRect(100f, 100f, 200f, 200f, Path.Direction.CW);
        srcPaint = Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint?.color =context.resources.getColor (R.color.white)
    }

    /**
     * 设置内部透明的部分
     *
     * @param srcPath
     */
    fun setSrcPath(srcPath: Path?) {
        this.srcPath = srcPath!!
    }

    override fun draw(canvas: Canvas) {
        innerDrawable!!.bounds = bounds
        if (srcPath == null || srcPath.isEmpty) {
            innerDrawable!!.draw(canvas)
        } else {
            //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
            val saveCount = canvas.saveLayer(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), srcPaint, Canvas.ALL_SAVE_FLAG)

            //dst 绘制目标图
            innerDrawable!!.draw(canvas)

            //设置混合模式
            srcPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            //src 绘制源图
            canvas.drawPath(srcPath, srcPaint!!)
            //清除混合模式
            srcPaint!!.xfermode = null
            //还原画布
            canvas.restoreToCount(saveCount)
        }
    }

    override fun setAlpha(alpha: Int) {
        innerDrawable?.setAlpha(alpha);
    }

    override fun getOpacity(): Int {
        return innerDrawable?.getOpacity()!!
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        innerDrawable?.setColorFilter(colorFilter);
    }
}