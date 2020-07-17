package com.example.a11699.androidxallstudy.cunstombarrage.netdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.collections.ArrayList


/**
 *Create time 2020/6/20
 *Create Yu
 *description:
 */
class BarrageView : View, Runnable {
    var items: MutableList<Textitem> = ArrayList()
    var random: Random = Random()
    var paint: TextPaint? = null
    var thread: Thread? = null

    constructor(context: Context) : super(context) {
        initpaint();
        thread = Thread(this)
        thread!!.start()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initpaint();
        thread = Thread(this)
        thread!!.start()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyAttr: Int) : super(context, attributeSet, defStyAttr) {
        initpaint();
        thread = Thread(this)
        thread!!.start()
    }

    fun initpaint() {
        paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        paint!!.setColor(Color.RED)
        paint!!.setTextSize(30f)
    }

    fun addTextitem(content: String?) {
        val x =  random.nextFloat() * width
        val y =  Math.abs(random.nextFloat() * (height - 50)) + 40
        val step =  random.nextFloat() * 50
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        val item = Textitem(content, x, y, step, Color.RED)
        items.add(item)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        for (item in items) {
            paint!!.color = item.textcolor
            canvas!!.drawText(item.content!!, item.fx, item.fy, paint!!)
        }
    }

    override fun run() {
        while (true) {
            Log.i("Zjc","线程还在跑")
            Thread.sleep(60)
            for (item in items) {
                item.setPersted()
            }
            postInvalidate()
        }

    }

}