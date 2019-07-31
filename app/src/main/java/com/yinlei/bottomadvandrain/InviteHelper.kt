package com.yinlei.bottomadvandrain

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * 底部广告条的控制
 */
class InviteHelper (var mContext: Context,var mInviteLayout: View){
    private var isShow: Boolean = false
    init {
        mInviteLayout.setOnClickListener {
            Toast.makeText(mContext,"你点击了底部广告条!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun startAnim(view: View,shouldShow: Boolean){
        view.visibility=View.VISIBLE
        view.post {
            var distance: Float = view.height.toFloat()
           var translation: ObjectAnimator
            if(shouldShow){
                if(isShow){
                    return@post
                }
                //向上移动
                translation = ObjectAnimator.ofFloat(view,"translationY",distance,0f)
                isShow =true
            }else{//隐藏的
                if(!isShow){
                    return@post
                }
                translation = ObjectAnimator.ofFloat(view,"translationY",0f,distance)
                isShow =false
            }
            translation.duration = 1000
            translation.start()
        }
    }

    fun start(){
        mInviteLayout.visibility = View.INVISIBLE
        startAnim(mInviteLayout,true)
    }


    fun hide(){
        startAnim(mInviteLayout,false)
    }
}