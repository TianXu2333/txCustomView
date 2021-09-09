package com.tx.txcustomview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.tx.txcustomview.R

/**
 * create by xu.tian
 * @date 2021/9/7
 */
class BitmapShaderView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint = Paint()
    private var mBitmap :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hanikezi)
    private lateinit var mBitmapBg : Bitmap ;
    private var isInit = false
    private var centerX  = 0f
    private var centerY = 0f
    private var dx = 0f
    private var dy = 0f

    private var w = 0
    private var h = 0
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInit){
            var canvasBg = Canvas(mBitmapBg)
            canvasBg.drawBitmap(mBitmap,null,Rect(0,0,width,height),paint)
        }
        var layerId : Int = canvas?.saveLayer(0f,0f, w.toFloat(), h.toFloat(),null,Canvas.ALL_SAVE_FLAG)!!
        paint.shader = BitmapShader(mBitmapBg,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        canvas?.drawCircle(centerX+dx, centerY+dy,300f,paint)
        canvas.restoreToCount(layerId)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmapBg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        this.w = w
        this.h = h
        centerX = (w/2).toFloat()
        centerY = (h/2).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_DOWN -> {
                Log.d("BitmapShaderView","onTouchEvent ACTION_DOWN")
                dx = 0f
                dy = 0f
                centerX = event.x
                centerY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("BitmapShaderView","onTouchEvent ACTION_MOVE")
                dx = event.x - centerX
                dy = event.y - centerY
                postInvalidate()

            }
        }
        return true
    }

}