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
class PathCaptureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint : Paint = Paint()
    private var bitmapBg = BitmapFactory.decodeResource(resources, R.drawable.hanikezi)
    private var bitmapSrc = BitmapFactory.decodeResource(resources, R.drawable.dilireba)
    private var bitmapDst = Bitmap.createBitmap(bitmapSrc.width,bitmapSrc.height,Bitmap.Config.ARGB_8888)
    var w = 0
    var h = 0
    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 100f
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }

    var preX = 0f
    var preY = 0f
    var path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制底层图片
        canvas?.drawBitmap(bitmapBg,null,Rect(0,0,bitmapDst.width,bitmapDst.height),paint)
        var layerId : Int = canvas?.saveLayer(0f,0f, w.toFloat(), h.toFloat(),null,Canvas.ALL_SAVE_FLAG)!!
        // 把手势路径绘制到目标图像上
        var c = Canvas(bitmapDst)
        c.drawPath(path,paint)

        // 把目标图像绘制到画布上
        canvas?.drawBitmap(bitmapDst,0f,0f,paint)

        // 计算源图像区域
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas?.drawBitmap(bitmapSrc,0f,0f,paint)
        // 取消混合模式
        paint.xfermode = null
        // 恢复画布状态

        canvas?.restoreToCount(layerId)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_DOWN -> actionDown(event)
            MotionEvent.ACTION_MOVE -> bezierPathMove(event)
        }
        return true
    }

    private fun actionDown(event: MotionEvent){
        preX = event.x
        preY = event.y
        path.moveTo(preX,preY)
    }

    private fun linePathMove(event: MotionEvent){
        path.lineTo(event.x,event.y)
        postInvalidate()
    }

    private fun bezierPathMove(event: MotionEvent){
        path.quadTo(preX,preY,(event.x+preX)/2,(event.y+preY)/2)
        preX = event.x
        preY = event.y
        postInvalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("PathCaptureView","onDetachedFromWindow")
        bitmapBg.recycle()
        bitmapDst.recycle()
        bitmapSrc.recycle()
    }
}