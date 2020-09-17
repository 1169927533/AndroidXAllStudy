package com.example.a11699.androidxallstudy.funnyeffect

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
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
        studyMatirx()
    }


    private fun getMatrix() {
        var matrix = android.graphics.Matrix()
        var camera = Camera()
        camera.save()
        camera.rotateY(45f)
        camera.getMatrix(matrix)
        camera.restore()
    }

    private fun studyMatirx() {
        var bimap = BitmapFactory.decodeResource(resources, R.drawable.img1)
        img1.setImageBitmap(bimap)
        //旋转中心0,0
        var matrix = getMatrixx()
        var bit = getBit(bimap,matrix)
        img2.setImageBitmap(bit)
        /**
         * 旋转中心为(0,height/2)
         */
        matrix = getMatrixx();
        matrix.preTranslate(0f, (-bimap.getHeight() / 2).toFloat());
        matrix.postTranslate(0f, (bimap.getHeight() / 2).toFloat());
        bit = getBit(bimap, matrix);
        img3.setImageBitmap(bit);

        /**
         * 旋转中心为(0,height)
         */
        matrix = getMatrixx();
        matrix.preTranslate(0f, (-bimap.getHeight()).toFloat());
        matrix.postTranslate(0f, bimap.getHeight().toFloat());
        bit = getBit(bimap, matrix);
        img4.setImageBitmap(bit);
    }

    private fun getMatrixx(): Matrix {
        var matrix = Matrix()
        var camera = Camera()
        camera.save()
        camera.rotateY(45f)
        camera.getMatrix(matrix)
        camera.restore()
        return matrix
    }

    private fun getBit(bitmap: Bitmap, matrix: Matrix): Bitmap? {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

    }
}