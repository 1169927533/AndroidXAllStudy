package com.example.a11699.androidxallstudy.funnyeffect

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.comp_base.Util
import kotlinx.android.synthetic.main.activity_funny.*

/**
 *Create time 2020/9/16
 *Create Yu
 *description:
 */
class FunnyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funny)
        studyMatrix()
        setAllViewGone()
        btn_startrotate.setOnClickListener {
            val animal = ValueAnimator.ofFloat(360f, 180f).apply {
                duration = 4000
                interpolator = LinearInterpolator()
                addUpdateListener {
                    camera3.setRotate((it.animatedValue) as Float)
                }
            }
            animal.start()
        }

        btn_startrotate2.setOnClickListener {
            val animal = ValueAnimator.ofFloat(0f,290f).apply {
                duration = 4000
                interpolator = LinearInterpolator()
                addUpdateListener {
                    Log.i("zjc", "animatedValue : ${it.animatedValue}")
                    jihebianhuan.setRotate((it.animatedValue) as Float)
                }
            }
            animal.start()
        }

        //开始展开动画
        btn_startrotate3.setOnClickListener {
            zhepaperview.startUnfoldAnimal()
        }
        //开始折叠东爱护
        btn_startrotate4.setOnClickListener {
            zhepaperview.startFoldAnimal()
        }

    }

    private fun setAllViewGone() {
        img1.visibility = View.GONE
        img2.visibility = View.GONE

        img3.visibility = View.GONE

        img4.visibility = View.GONE

        img5.visibility = View.GONE

        //  img6.visibility = View.GONE
        img7.visibility = View.GONE
        img8.visibility = View.GONE

    }

    private fun getMatrix(): Matrix {
        var matrix = Matrix()
        var camera = Camera()
        camera.save()
        camera.rotateY(45f)
        camera.getMatrix(matrix)
        camera.restore()
        return matrix
    }


    private fun studyMatrix() {
        var bimap = BitmapFactory.decodeResource(resources, R.drawable.img1)
        img1.setImageBitmap(bimap)
        //旋转中心0,0
        var matrix = getMatrix()
        var bit = getBit(bimap, matrix)
        img2.setImageBitmap(bit)
        /**
         * 旋转中心为(0,height/2)
         */
        matrix = getMatrix();
        matrix.preTranslate(0f, (-bimap.getHeight() / 2).toFloat());
        matrix.postTranslate(0f, (bimap.getHeight() / 2).toFloat());
        bit = getBit(bimap, matrix);
        img3.setImageBitmap(bit);

        /**
         * 旋转中心为(0,height)
         */
        matrix = getMatrix()
        matrix.preTranslate(0f, (-bimap.getHeight()).toFloat());
        matrix.postTranslate(0f, bimap.getHeight().toFloat());
        bit = getBit(bimap, matrix);
        img4.setImageBitmap(bit)


        var scaleMatirx = Matrix()
        scaleMatirx.postScale(1.5f, 1.5f)
        var startx = (bimap.width - bimap.width / 2) / 2
        var bitg = Bitmap.createBitmap(bimap, startx.toInt(), 0, (bimap.width / 2).toInt(), (bimap.height / 2).toInt(), scaleMatirx, true)
        img5.setImageBitmap(bitg)


        Util.printLog("图片宽高")
        Util.printLog("宽：${bimap.width}  高：${bimap.height}")

        var rotateMatrix = Matrix()
        //   rotateMatrix.setRotate(45f)
        // rotateMatrix.preTranslate(bimap.width.toFloat(), bimap.height.toFloat())
        rotateMatrix.postTranslate(-bimap.width.toFloat(), -bimap.height.toFloat())
        var bitgRotate = Bitmap.createBitmap(bimap, 0, 0, (bimap.width).toInt(), (bimap.height).toInt(), rotateMatrix, true)
        img6.setImageBitmap(bitgRotate)



        matrix2()
    }


    private fun getBit(bitmap: Bitmap, matrix: Matrix): Bitmap? {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    private var canvas: Canvas? = null
    private var paint: Paint? = null
    private fun matrix2() {
        val bitmap1 = BitmapFactory.decodeResource(resources,
                R.drawable.img1)

        val updateBitmap = Bitmap.createBitmap(bitmap1.width * 2,
                bitmap1.height * 2, bitmap1.config)

        canvas = Canvas(updateBitmap)
        paint = Paint()
        paint!!.setColor(Color.BLACK)
        val matrix = Matrix()
        canvas!!.drawBitmap(bitmap1, matrix, paint)
        setImageSynthesis(matrix)
        img7.setImageBitmap(bitmap1);
        img8.setImageBitmap(updateBitmap)
    }

    /**
     * 设置倒影效果
     *
     * @param bitmap1
     * @param matrix
     */
    private fun setInvertedImage(bitmap1: Bitmap, matrix: Matrix) {
        matrix.setScale(1f, (-1).toFloat())
        matrix.postTranslate(0f, bitmap1.height.toFloat())
    }

    /**
     * 设置图片的合成
     *
     * @param matrix
     */
    private fun setImageSynthesis(matrix: Matrix) {
        val bitmap2 = BitmapFactory.decodeResource(resources,
                android.R.drawable.alert_dark_frame)
        // 设置图片合成时的各种模式
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DARKEN)
        // 图片的合成很简单，就是再以bitmap2为基图往目标图片上画一次
        canvas!!.drawBitmap(bitmap2, matrix, paint)
    }
}