package com.example.a11699.androidxallstudy.soul

import com.example.a11699.androidxallstudy.soul.bean.PointModel
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 *Create time 2020/7/28
 *Create Yu
 *description:
 */
class PlanetCalculator {
    var radius = 0
    var planetModelCloud: ArrayList<PointModel> = ArrayList()
    private var sinAngleX = 0f
    private var cosAngleX: kotlin.Float = 0f
    private var sinAngleY: kotlin.Float = 0f
    private var cosAngleY: kotlin.Float = 0f
    private var sinAngleZ: kotlin.Float = 0f
    private var cosAngleZ: kotlin.Float = 0f
    private var maxDelta = Float.MIN_VALUE
    private var minDelta = Float.MAX_VALUE

    /**
     * 默认设置是在云端均匀分布标签
     */
    var mAngleZ: Float = 0f//移动的弧长
    var mAngleX = 0.5f //每个点移动的弧长
    var mAngleY = 0.5f//移动的弧长
    private var isEvenly = true //是否均匀分布

    //初始化时添加每个view
    fun add(planetModel: PointModel) {
        planetModelCloud.add(planetModel)
    }

    private fun location(pointModel: PointModel) {
        val phi: Double = Math.random() * Math.PI
        val theta: Double = Math.random() * (2 * Math.PI)
        pointModel.locX_3 = (radius * cos(theta) * sin(phi)).toFloat()
        pointModel.locY_3 = (radius * sin(theta) * sin(phi)).toFloat()
        pointModel.locZ_3 = radius * cos(phi).toFloat()
        planetModelCloud.add(pointModel)
    }

    //初始化每个view
    fun create(issEvenly: Boolean) {
        isEvenly = issEvenly
        // 计算和设置每个Tag的位置
        locationAll(isEvenly)//设置每个的三位坐标系
        sineCosine(mAngleX, mAngleY, mAngleZ)//计算每个轴的旋转角度
        updateAll() //更具旋转角度计算他们对应的二维坐标轴
    }

    /**
     * 更新所有的
     */
    private fun updateAll() {
        // 更新标签透明度和比例
        val count = planetModelCloud.size
        for (i in 0 until count) {
            val planetModel: PointModel = planetModelCloud[i]
            // 此部分有两个选项：
            // 绕x轴旋转
            val rx1: Float = planetModel.locX_3
            val ry1: Float = planetModel.locY_3 * cosAngleX + planetModel.locZ_3 * -sinAngleX
            val rz1: Float = planetModel.locY_3 * sinAngleX + planetModel.locZ_3 * cosAngleX
            // 绕y轴旋转
            val rx2: Float = rx1 * cosAngleY + rz1 * sinAngleY
            val rz2: Float = rx1 * -sinAngleY + rz1 * cosAngleY
            // 绕z轴旋转
            val rx3: Float = rx2 * cosAngleZ + ry1 * -sinAngleZ
            val ry3: Float = rx2 * sinAngleZ + ry1 * cosAngleZ
            // 将数组设置为新位置
            planetModel.locX_3 = (rx3)
            planetModel.locY_3 = (ry3)
            planetModel.locZ_3 = (rz2)

            planetModel.locX_2 = (rx3)
            planetModel.locY_2 = (ry3)

            // 添加透视图
            val diameter = 2 * radius
            var per = diameter / (diameter + rz2)
            planetModel.scale = per
            //计算透明度
            var delta = diameter + rz2
            maxDelta = maxDelta.coerceAtLeast(delta)
            minDelta = minDelta.coerceAtMost(delta)
            var alpha = (delta - minDelta) / (maxDelta - minDelta)
            planetModel.alpha = 1 - alpha
        }
    }


    private fun locationAll(isEvenly: Boolean) {
        var phi: Double
        var theta: Double
        val count = planetModelCloud.size
        for (i in 1 until count + 1) {
            if (isEvenly) {
                // 平均（三维直角得Z轴等分[-1,1]） θ范围[-π/2,π/2])
                phi = acos(-1.0 + (2.0 * i - 1.0) / count)
                theta = sqrt(count * Math.PI) * phi
            } else {
                phi = Math.random() * Math.PI
                theta = Math.random() * (2 * Math.PI)
            }


            planetModelCloud[i - 1].locX_3 = ((radius * cos(theta) * sin(phi)).toFloat())
            planetModelCloud[i - 1].locY_3 = ((radius * sin(theta) * sin(phi)).toFloat())
            planetModelCloud[i - 1].locZ_3 = ((radius * cos(phi)).toFloat())
        }
    }

    fun sineCosine(mAngleX: Float, mAngleY: Float, mAngleZ: Float) {
        val degToRad = Math.PI / 180
        sinAngleX = sin(mAngleX * degToRad).toFloat()
        cosAngleX = cos(mAngleX * degToRad).toFloat()
        sinAngleY = sin(mAngleY * degToRad).toFloat()
        cosAngleY = cos(mAngleY * degToRad).toFloat()
        sinAngleZ = sin(mAngleZ * degToRad).toFloat()
        cosAngleZ = cos(mAngleZ * degToRad).toFloat()
    }


    //这个是在外部调用实现跟新
    fun updateByout() {
        sineCosine(mAngleX, mAngleY, mAngleZ)
        updateAll()
    }

}