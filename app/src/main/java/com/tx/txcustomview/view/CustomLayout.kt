package com.tx.txcustomview.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

/**
 * create by xu.tian
 * @date 2021/10/6
 */
class CustomLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val tag = "CustomLayout"
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        var measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        var measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        var measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        Log.d(tag,"onMeasure measureWidth +$measureWidth +\n " +
                "measureHeight +$measureHeight +\n" +
                "measureWidthMode +$measureWidthMode +\n" +
                "measureHeightMode +$measureHeightMode +\n")

        var w = 0
        var h = 0
        w = if (measureWidthMode==MeasureSpec.EXACTLY){
            measureWidth
        }else {
            width
        }
        h = if (measureHeightMode==MeasureSpec.EXACTLY){
            measureHeight
        }else {
            height
        }
        for (index in 0 until childCount){
            var child = getChildAt(index)
            measureChild(child,widthMeasureSpec,heightMeasureSpec)
        }
        setMeasuredDimension(w,h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(tag,"w $w h $h oldw $oldw oldh $oldh ")
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d(tag,"onLayout ")
        var w = 0
        var h = 0
        for (index in 0 until childCount){
            var child = getChildAt(index)
            var top = 0
            var left = 0
            var right = 0
            var bottom = 0
            // 获取margin
            var lp = child.layoutParams as MarginLayoutParams
            var childWith = child.measuredWidth+lp.leftMargin+lp.rightMargin
            var childHeight = child.measuredHeight+lp.topMargin+lp.bottomMargin
            if (w+childWith<=measuredWidth){
                left = w+lp.leftMargin
                top = h+lp.topMargin
                right = w+childWith-lp.rightMargin
                bottom = h+childHeight-lp.bottomMargin
                w+=childWith
            }else{
                w=0
                h+=childHeight
                left = w+lp.leftMargin
                top = h+lp.topMargin
                right = w+childWith-lp.rightMargin
                bottom = h+childHeight-lp.bottomMargin
                w+=childWith
            }
            child.layout(left,top,right,bottom)
        }

    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(tag,"onDraw")
    }

    /**
     * 获取margin必须重写以下3个方法
     */
    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
    }

}