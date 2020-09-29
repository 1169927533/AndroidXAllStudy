package com.example.a11699.comp_customview.zhezhi

import android.graphics.Bitmap

/**
 *Create time 2020/9/19
 *Create Yu
 *description:
 */
class PaperInfo(var visible: Boolean,
                val x: Float,
                var y: Float,
                var angle: Float,
                val fg: Bitmap,
                val bg: Bitmap,
                var prev: PaperInfo?,
                var next: PaperInfo?)