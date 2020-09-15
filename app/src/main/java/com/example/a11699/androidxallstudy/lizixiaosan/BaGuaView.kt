package com.example.a11699.androidxallstudy.lizixiaosan

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *Create time 2020/9/15
 *Create Yu
 *description:
 */
class BaGuaView : View {
    var path1 = Path()
    var path2 = Path()
    var paint = Paint()

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    /**
     * Path.Op.DIFFERENCE: 减去都存在的部分
     * Path.Op.INTERSECT : 留下都存在的部分
     * Path.Op.REVERSE_DIFFERENCE: 除去path1和path2都存在的部分留下path2剩下的部分
     * Path.Op.UNION: 取path1 和 path2的并集
     * Path.Op.XOR: 取path1和path2不重合的部分
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas.save()
      //  for (i in 0..2) {
            path1.addCircle(0f, 0f, 200f, Path.Direction.CW);
            path2.addRect(-200f, -200f, 0f, 200f, Path.Direction.CW);
            path1.op(path2, Path.Op.UNION);//留下相交的区域

           /* path2.reset();
            path2.addCircle(0f, -100f, 100f, Path.Direction.CCW);
            path1.op(path2, Path.Op.UNION);//去全部的区域
            path2.reset();
            path2.addCircle(0f, 100f, 100f, Path.Direction.CW);
            path1.op(path2, Path.Op.DIFFERENCE);//取Path1减去path2的区域*/
            canvas.drawPath(path1, paint);
           // canvas.rotate(180f, 0f, 0f);
     //   }
     //   canvas.restore()
        /* paint.setShader(RadialGradient(0f, -100f, 25f, Color.WHITE, Color.BLACK, Shader.TileMode.MIRROR));
         canvas.drawCircle(0f, -100f, 25f, paint);
         paint.setShader(  RadialGradient (0f, 100f, 25f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));
         canvas.drawCircle(0f, 100f, 25f, paint)*/
    }
}