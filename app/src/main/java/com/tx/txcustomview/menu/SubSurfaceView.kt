package com.tx.txcustomview.menu

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * create by xu.tian
 * @date 2021/9/11
 */
class SubSurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs) {
    var paint = Paint()
    init {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
    }
    var centerX = 0f
    var centerY = 0f
    var radius = 0f


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerX,centerY,radius,paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w/2).toFloat()
        centerY = (h/2).toFloat()
        radius = centerX/10*9
    }

    fun startDraw(){

    }

}