package com.tx.txcustomview.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.LinearInterpolator


/**
 * create by xu.tian
 * @date 2021/9/14
 */
class CustomSwitch(context: Context?, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatCheckBox(
    context!!, attrs) {
    // 初始化画笔
    var paint = Paint()
    // 定义绘制背景的路径
    var path = Path()
    // 定义动画
    private lateinit var animator : ValueAnimator
    init {
        paint.isAntiAlias = true
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }
    // 控件的宽
    var w = 0f
    // 控件的高
    var h = 0f
    // 圆形按钮的半径
    var radius = 0f
    // 圆形按钮的中心的x坐标
    var centerX = 0f
    // 圆形按钮中心的y坐标
    var centerY = 0f
    // 底层的颜色背景
    var color = Color.rgb(255,255,255)
    // 当前状态的圆形按钮的中心的x坐标
    var currentCenterX = 0f
    // 动画执行时间
    var animatorDuration = 200L
    // 定义的未选中时的阴影偏移量
    var shadowOffSet = 10f
    // 绘制的当前状态的阴影偏移量
    var currentShadow = 10f

    override fun onDraw(canvas: Canvas) {
        drawBg(canvas)
        drawSwitch(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h= h.toFloat()
        radius = (w/4).toFloat()
        centerX = radius
        centerY = (h/2).toFloat()
        currentCenterX = centerX
        initPath()
        initAnimator()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_UP -> {
                isChecked = !isChecked
                startAnim()
            }
        }
        return true
    }
    /**
     * 绘制背景
     */
    private fun drawBg(canvas: Canvas){
        paint.style = Paint.Style.FILL
        paint.setShadowLayer(5f,2f,2f,Color.BLACK)
        paint.color = Color.WHITE
        canvas.drawPath(path,paint)
        paint.color = this.color
        canvas.drawPath(path,paint)
    }
    /**
     * 初始化绘制背景的路径
     */
    private fun initPath(){
        path.moveTo(radius,centerY-radius)
        path.lineTo(radius*3,centerY-radius)
        var rightRect = RectF()
        rightRect.left = radius*2
        rightRect.top = centerY-radius
        rightRect.right = radius*4
        rightRect.bottom = centerY+radius
        path.arcTo(rightRect,-90f,180f)
        path.lineTo(radius,centerY+radius)
        var leftRect = RectF()
        leftRect.left = 0f
        leftRect.top = centerY-radius
        leftRect.right = radius*2
        leftRect.bottom = centerY+radius
        path.arcTo(leftRect,90f,180f)
    }
    /**
     * 绘制中心的圆形按钮
     */
    private fun drawSwitch(canvas: Canvas){
        paint.shader = null
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 5f
        paint.setShadowLayer(10f,currentShadow,shadowOffSet,Color.parseColor("#DDA69E9E"))
        canvas.drawCircle(centerX,centerY,radius/10*9,paint)
    }
    /**
     * 初始化动画
     */
    private fun initAnimator(){
        animator = ValueAnimator.ofFloat(0f,100f)
        animator.duration = animatorDuration
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { valueAnimator ->
            var currentValue = valueAnimator.animatedValue as Float
            Log.d("animator","currentValue $currentValue")
            if (isChecked){
                centerX = (3*radius-currentCenterX)*(currentValue/100f)+currentCenterX
                color = Color.argb((currentValue/100f*255).toInt(),0,255,0)
                currentShadow = (shadowOffSet-20*((currentValue/100f)))
            }else{
                centerX = currentCenterX - (currentCenterX-radius)*(currentValue/100f)
                color = Color.argb((255-currentValue/100f*255).toInt(),0,255,0)
                currentShadow = 20*((currentValue/100f))-shadowOffSet
            }
            postInvalidate()
        }
        animator.addListener(object  : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
                Log.d("animator","onAnimationStart")
            }

            override fun onAnimationEnd(p0: Animator?) {
                currentCenterX = centerX
                Log.d("animator","onAnimationEnd")
            }

            override fun onAnimationCancel(p0: Animator?) {
                Log.d("animator","onAnimationCancel")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                Log.d("animator","onAnimationRepeat")
            }
        })
    }
    /**
     * 开始执行动画
     */
    private fun startAnim(){
        animator.start()
    }
    /**
     * remove view时取消动画
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }
}