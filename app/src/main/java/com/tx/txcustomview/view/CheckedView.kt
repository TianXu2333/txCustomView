package com.tx.txcustomview.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


/**
 * create by xu.tian
 * @date 2021/9/10
 */
class CheckedView(context: Context?, attrs: AttributeSet?) : View(context, attrs){
    // 当前选中状态
    var checked = false
    // 创建画笔对象
    private var paint = Paint()
    // 圆心x坐标
    private var centerX = 0f
    // 圆心y坐标
    private var centerY = 0f
    // 圆半径
    private var radius = 0f
    // 实际绘制的真实圆半径
    private var drawRadius = 0f

    // 按下去执行的动画
    private lateinit var pressAnimator: ValueAnimator
    // 按下去动画执行的时间
    private var pressAnimDuration = 100L
    // 当前的按下去的动画值0~100
    var pressCurrentValue = 0F
    // 按下去时的缩放值
    private var pressScale = 0.8f
    
    // 松手执行的动画
    lateinit var upAnimator : ValueAnimator
    // 松手动画执行的时间
    private var upAnimDuration = 300L
    // 当前的松手的动画值0~100
    var upCurrentValue = 0F

    // 判断是否是外部设置
    var isSet = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE,paint)
        if (!checked){
            drawUnchecked(canvas)
        }else{
            drawChecked(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.centerX = (w/2).toFloat()
        this.centerY = (h/2).toFloat()
        radius = if (centerX<centerY){
            centerX/10*9
        }else{
            centerY/10*9
        }
        this.drawRadius = radius
        initPressAnimator()
        initUpAnimator()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> actionPress()
            MotionEvent.ACTION_UP -> actionUp()
        }
        return true
    }

    private fun initPressAnimator(){
        pressAnimator = ValueAnimator.ofFloat(0f,100f)
        pressAnimator.duration = pressAnimDuration
        pressAnimator.addUpdateListener { valueAnimator ->
            pressCurrentValue = valueAnimator.animatedValue as Float
            drawRadius = radius*(1-(pressCurrentValue/100)*(1-pressScale))
            postInvalidate()
        }
        pressAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                Log.d("CheckView", "pressAnimator onAnimationEnd --->$pressCurrentValue")
                if (isSet){
                    upAnimator.start()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        })

    }
    private fun initUpAnimator(){
        upAnimator = ValueAnimator.ofFloat(0f,100f)
        upAnimator.duration = upAnimDuration
        upAnimator.addUpdateListener { valueAnimator ->
            upCurrentValue = valueAnimator.animatedValue as Float
            drawRadius = radius*pressScale+(1-pressScale)*(upCurrentValue/100)*radius
            postInvalidate()
        }
        upAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                if (!checked){
                    pressCurrentValue = 0f
                    upCurrentValue = 0f
                }else{
                    pressCurrentValue = 100f
                    upCurrentValue = 100f
                }
                upAnimator.cancel()
                Log.d("CheckView", "upAnimator onAnimationEnd --->$upCurrentValue")
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        })

    }

    private fun actionPress(){
        isSet = false
        pressAnimator.start()
    }

    private fun actionUp() {
        checked = !checked
        upAnimator.start()
    }

    private fun drawChecked(canvas: Canvas){
        // 绘制选中时的圆
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX,centerY,drawRadius,paint)
        var path = Path()
        path.moveTo(centerX-drawRadius/2,centerY-drawRadius/10);
        path.lineTo(centerX-drawRadius/15,centerY+drawRadius/3);
        path.lineTo(centerX+drawRadius/2,centerY-drawRadius/3);
        var dstPah = Path()
        var pathMeasure = PathMeasure()
        pathMeasure.setPath(path,false)
        pathMeasure.getSegment(0f,pathMeasure.length*(upCurrentValue/100),dstPah,true)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = drawRadius / 6
        paint.color = Color.WHITE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        canvas.drawPath(dstPah,paint)
    }

    private fun drawUnchecked(canvas: Canvas){
        // 绘制最外层的圆
        paint.style = Paint.Style.FILL
        paint.color = Color.GRAY
        paint.setShadowLayer(5f,5f,5f,Color.parseColor("#4D000000"))
        paint.alpha = 150
        canvas.drawCircle(centerX,centerY,drawRadius,paint)
    }

    fun setStatusChecked(checked : Boolean){
        this.checked  = checked
        isSet = true
        pressAnimator.start()
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pressAnimator.cancel()
        upAnimator.cancel()
    }

}