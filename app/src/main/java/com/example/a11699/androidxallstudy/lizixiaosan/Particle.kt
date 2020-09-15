package com.example.a11699.androidxallstudy.lizixiaosan

/**
 *Create time 2020/9/14
 *Create Yu
 *description:一个粒子类
 */
class Particle(
        var x: Float,//X坐标
        var y: Float,//Y坐标
        var radius: Float,//半径
        var offSetX: Float,
        var offSet:Float,
        var direction:Float,
        var speed: Float,//速度
        var angle: Double,//粒子角度
        var maxOffSet: Float = 300f//最大的移动距离
)
