package com.yinlei.bottomadvandrain.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.yinlei.bottomadvandrain.Bean.FloatBean
import java.util.*
import kotlin.math.ceil
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class FloatView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {


    private var isFloating = false
    private var mFloatHeight: Float = 0.0f
    private var mFloatWidth: Float = 0.0f
    private var random: Random = Random()
    private var mFloatBeanList: MutableList<FloatBean> = mutableListOf()
    private var mPaint: Paint = Paint()
    private var MyMatrix: Matrix = Matrix()

    private var mSatrtTimeStamp: Long = 0//开始落下的时间


    init {
        mPaint.color = resources.getColor(android.R.color.holo_purple)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //1.如果浮屏效果已经开始的情况
        //2.如果浮屏效果未开始的情况

        if(!isFloating || mFloatBeanList.size == 0){
            Log.d("yinlei","失败")
            return
        }

        var totalTimeStamp: Long = System.currentTimeMillis() - mSatrtTimeStamp
        for(index in 0 until mFloatBeanList.size){
            var bean = mFloatBeanList[index]
            var bitmap = bean.bitmap
            if(bean.bitmap.isRecycled || bean.y > height || totalTimeStamp < bean.appearTimeStamp){//bitmap被回收了或者说是单个浮萍图片已经落出了屏幕
                Log.d("yinlei","结束 $height  ${bean.y}")
                bean.y=0//当高度超过的时候重置，达到循环效果
                continue
            }


            //图片可能大小不一样,我们需要把他们弄成一样的大小
            MyMatrix.reset()
            val heightScale: Float = mFloatHeight / bitmap.height
            val widthScale: Float = mFloatWidth / bitmap.width
            MyMatrix.setScale(widthScale,heightScale)


            //让速率参与运算
            bean.x = bean.x + bean.velocityX
            bean.y = bean.y + bean.velocityY

            //让图标横向和纵向都有所移动
            MyMatrix.postTranslate(bean.x.toFloat(), bean.y.toFloat())

            Log.d("yinlei","当前x的值为:${bean.x},当前y的值为：${bean.y},当前vx为：${bean.velocityX}，当前vy为：${bean.velocityY}")
            canvas!!.drawBitmap(bitmap,MyMatrix,mPaint)
        }


        postInvalidate()//重绘
    }

    /**
     * 开始浮屏
     */
    fun startFloat(list: MutableList<Bitmap>){
        stopFloat()
        visibility = VISIBLE
        initData(list)
        isFloating = true
        invalidate()//使整个控件再次去onDraw
    }


    /**
     * 停止浮屏
     */
    fun stopFloat(){
        visibility = GONE
        isFloating = false
    }

    /**
     * 初始化数据Bean
     */
    private fun initData(list: MutableList<Bitmap>) {
            mFloatHeight  = dp2px(context, 50F).toFloat()
            mFloatWidth  = dp2px(context, 50F).toFloat()

        mSatrtTimeStamp =System.currentTimeMillis()
            mFloatBeanList.clear()


             var  maxDuration = 2000

        //当前图标下落的时间
        var currentDuration = 0
        var i = 0
        var size = list.size
        while(currentDuration < maxDuration){

            var duration: Float = (random.nextInt(500)+maxDuration).toFloat()
            val vx = random.nextFloat().roundToInt()
            val vy = (height*16/duration).toInt()//16是android它每16毫秒就刷新一次
            //- (Math.ceil(mFloatHeight.toDouble()).toInt())为负数表示在屏幕的最上方
            var bean = FloatBean(list[i % size],random.nextInt(height),- (ceil(mFloatHeight.toDouble()).toInt()),vx,vy,appearTimeStamp = currentDuration)//这里的width是getWidth屏幕的宽度因为match_parent
            currentDuration += random.nextInt(250)
            mFloatBeanList.add(bean)
            i++
        }
    }



    /**
     * 图片可能大小不一样,我们需要把他们弄成一样的大小
     * dp => px
     */
    companion object{
        fun dp2px(context: Context,dpValue: Float): Int = (dpValue*(context.resources.displayMetrics.density)+0.5f).toInt()
    }

}