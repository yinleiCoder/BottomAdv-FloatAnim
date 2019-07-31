package com.yinlei.bottomadvandrain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_adv.*

class MainActivity : AppCompatActivity() {

    private var isHide: Boolean = false
    private val commonStr = "底部广告条"
    private lateinit var mInviteHelper: InviteHelper


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //底部广告条
        mInviteHelper = InviteHelper(this,invite_layout)
        mInviteHelper.start()
        hideBottom.setOnClickListener {
            if(!isHide){//没有隐藏
                hideBottom.text = "显示$commonStr"
                mInviteHelper.hide()
                floatView.stopFloat()
            }else{
                hideBottom.text = "隐藏$commonStr"
                mInviteHelper.start()
                startFloat()//浮屏幕开始
            }
            isHide = !isHide
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun startFloat(){
        floatView.startFloat(getBitmaps())
    }

    private fun getBitmaps(): MutableList<Bitmap>{
        var bitmaps =  mutableListOf<Bitmap>()
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.pic1))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.pic2))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.pic3))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.bb_invite_img))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.bb_home_balloon_img))

        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm1))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm2))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm3))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm4))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm5))
        bitmaps.add(BitmapFactory.decodeResource(resources,R.mipmap.mm6))
        return bitmaps
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        floatView?.let {
            it.stopFloat()
        }
    }
}
