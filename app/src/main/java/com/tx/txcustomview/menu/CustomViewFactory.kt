package com.tx.txcustomview.menu
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.tx.txcustomview.databinding.*


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
        fun getCustomSwitch(context:Context) : View {
            return CustomSwitchBinding.inflate(LayoutInflater.from(context),null,false).root
        }
        fun getLargeView(context:Context) : View {
            return LargeImageViewBinding.inflate(LayoutInflater.from(context),null,false).root
        }
        fun getCustomLayout(context:Context) : View {
            return LayoutCustomBinding.inflate(LayoutInflater.from(context),null,false).root
        }
    }
}