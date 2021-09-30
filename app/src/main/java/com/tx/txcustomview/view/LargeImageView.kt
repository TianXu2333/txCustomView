package com.tx.txcustomview.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.tx.txcustomview.R
import java.io.InputStream

/**
 * create by xu.tian
 * @date 2021/9/28
 */
public class LargeImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // 解析的Bitmap对象
    private var bitmap:Bitmap? = null
    // 获取到的Bitmap矩阵
    private lateinit var bitmapRect : Rect
    // 解析的区域在Bitmap矩阵中的位置矩阵区域
    private lateinit var positionRect : Rect
    // 控件View矩阵
    private lateinit var viewRect : Rect
    // 从资源文件中获取的输入流，因为要支持滑动刷新bitmap,为了避免重复创建对象，进行复用
    private lateinit var inputStream : InputStream
    // 解析器
    private lateinit var decoder: BitmapRegionDecoder

    // 手指按下的初始位置x左标
    private var startX = 0f
    // 手指按下的初始位置的y坐标
    private var startY = 0f

    // 绘制bitmap
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
/*        var matrix = Matrix()
        matrix.postTranslate(0f, (height/2).toFloat()-bitmap!!.height)
        canvas.drawBitmap(bitmap!!,matrix,null)*/
   /*     paint.shader = bitmapShader
        canvas.drawRect(Rect(0,0,width,height),paint)*/
        canvas.drawBitmap(bitmap!!,viewRect,viewRect,null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE ->{
                var dx = event.x - startX
                var dy = event.y - startY

                startX = event.x
                startY = event.y

                var left = positionRect.left-1.5*dx
                var top = positionRect.top-1.5*dy

                when {
                    left<0 -> {
                        positionRect.left = 0
                    }
                    left>bitmapRect.width()-width -> {
                        positionRect.left = bitmapRect.width()-positionRect.width()
                    }
                    else -> {
                        positionRect.left = left.toInt()
                    }
                }

                when {
                    top<0 -> {
                        positionRect.top = 0
                    }
                    top>bitmapRect.height()-height -> {
                        positionRect.top = bitmapRect.height()-positionRect.height()
                    }
                    else -> {
                        positionRect.top = top.toInt()
                    }
                }
                positionRect.right = positionRect.left+width
                positionRect.bottom = positionRect.top+height
                loadBitmapRegion()
                invalidate()
            }
        }
        return true
    }

    @SuppressLint("ResourceType")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    //    loadBitmap()
        viewRect = Rect(0,0,width,height)
        positionRect = Rect(0,0,width,height)
        inputStream = context.resources.openRawResource(R.drawable.girl)
        decoder = BitmapRegionDecoder.newInstance(inputStream,false)
        loadBitmapRegion()
   //     loadBitmapCompletely()
    }

    private fun loadBitmap(){
        // 获取图片的宽高
        var option = BitmapFactory.Options()
        // 设置inJustDecodeBounds为true可以解析出Bitmap的图片信息，比如宽高，但是不会获取bitmap对象，设为true以后调用
        // BitmapFactory.decodeResource()解析会返回null对象
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, R.drawable.girl,option)
        // 开始计算获取到的图片的宽高比，我们这里采用宽度优先，优先保证按宽度来显示图片，如果是垂直方向上的长图那就要再看情况了
        var fraction = option.outHeight.toFloat()/option.outWidth.toFloat()
        // 根据获取到的图片的宽高，和我们的实际控件的宽高来做对比，计算采样率
        // 比如我们有一张500x500的图，但是我们只需要显示100x100，那么我们采样率就可以设为4
        // 这样获取到的图片宽高就是125*125,然后我们可以再进行一次bitmap缩放来获取合适的bitmap，也可以缩小bitmap的占用内存
        option.inSampleSize = getSampleSize(option.outWidth,option.outHeight,width,(fraction*width).toInt())
        // 这里记住一定要设回false，否则获取不到图片
        option.inJustDecodeBounds = false
        // 开始获取压缩后的图片
        bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.girl,option)
        // 对获取后的图片进行缩放处理，获取一个新的适合控件的图片
        var bmpTemp = postBitmap(bitmap!!,width, (fraction*width).toInt())
        if (bitmap!=null && !bitmap!!.isRecycled){
            bitmap!!.recycle()
        }
        bitmap = bmpTemp
        Log.d("LargeImageView","sampleSize ---> ${option.inSampleSize}")
        Log.d("LargeImageView","byteCount ---> ${bitmap!!.byteCount}")
    }

    private fun loadBitmapCompletely(){
        bitmap = BitmapFactory.decodeResource(resources,R.drawable.girl)
        Log.d("LargeImageView","bitmap size ---> ${bitmap!!.byteCount}")
    }

     /**
      * 获取采样率
      */
    private fun getSampleSize(bmpW :Int ,bmpH : Int,targetW:Int ,targetH:Int) : Int{
        var sampleSize = 1
        while ((bmpW/sampleSize>=targetW)||(bmpH/sampleSize>=targetH)){
            sampleSize *= 2
        }
        return sampleSize
    }

    private fun postBitmap(bitmap:Bitmap, targetW: Int, targetH: Int):Bitmap{
        return Bitmap.createScaledBitmap(bitmap,targetW,targetH,false)
    }

    @SuppressLint("ResourceType")
    private fun loadBitmapRegion(){
        var option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, R.drawable.girl,option)
        bitmapRect = Rect(0,0,option.outWidth,option.outHeight)
        try {
            option.inJustDecodeBounds = false
            bitmap?.recycle()
            bitmap = decoder.decodeRegion(positionRect,option)
            Log.d("LargeImageView","byteCount ---> ${bitmap!!.byteCount}")
        }catch (e: Exception){
            e.printStackTrace()
        }

    }
    /**
     * 关闭输入流，回收bitmap
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (inputStream!=null){
            inputStream.close()
        }
        if(bitmap!=null && !bitmap!!.isRecycled){
            bitmap!!.recycle()
        }
    }
}