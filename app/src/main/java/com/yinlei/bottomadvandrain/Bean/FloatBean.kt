package com.yinlei.bottomadvandrain.Bean

import android.graphics.Bitmap

class FloatBean (var bitmap: Bitmap,var x: Int,var y:Int,
                 var velocityX: Int, var velocityY: Int,var appearTimeStamp: Int){//velocity为速率，bitmap是要绘制的bitmap
//appearTimeStamp为图标开始下落的时间
}