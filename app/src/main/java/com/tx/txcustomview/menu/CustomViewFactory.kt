package com.tx.txcustomview.menu
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.tx.txcustomview.databinding.BitmapShaderViewBinding
import com.tx.txcustomview.databinding.CheckedViewBinding
import com.tx.txcustomview.databinding.PathCaptureViewBinding
import com.tx.txcustomview.databinding.ShutterViewBinding


/**
 * create by xu.tian
 * @date 2021/9/9
 */
class CustomViewFactory {
    companion object {
        fun getPathCaptureView(context:Context) : View {
            return PathCaptureViewBinding.inflate(LayoutInflater.from(context),null,false).root
        }
        fun getBitmapShaderView(context:Context) : View {
            return BitmapShaderViewBinding.inflate(LayoutInflater.from(context),null,false).root
        }
        fun getShutterView(context:Context) : View {
            return ShutterViewBinding.inflate(LayoutInflater.from(context),null,false).root
        }
        fun getCheckedView(context:Context) : View {
            return CheckedViewBinding.inflate(LayoutInflater.from(context),null,false).root
        }
    }
}