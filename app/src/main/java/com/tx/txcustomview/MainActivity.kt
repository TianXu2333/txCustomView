package com.tx.txcustomview
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.tx.txcustomview.databinding.ActivityMainBinding
import com.tx.txcustomview.menu.CustomViewFactory
import com.tx.txcustomview.menu.MenuAdapter
import com.tx.txcustomview.menu.MenuName

class MainActivity : AppCompatActivity() , MenuAdapter.OnItemClickListener {
    private lateinit var binding : ActivityMainBinding

    var currentMenuName : ObservableField<String> = ObservableField()

    private var adapter = MenuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.model = this
        setContentView(binding.root)
        initAction()
        initMenu()
    }

    private fun initAction(){
        binding.menuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.menuList)
        }
    }
    private fun  initMenu(){
        binding.menuList.layoutManager = LinearLayoutManager(this)
        binding.menuList.adapter = adapter
        adapter.listener = this
        currentMenuName.set(MenuName.PATH_CAPTURE)
        addView(CustomViewFactory.getPathCaptureView(this))
    }

    override fun onClick(name : String) {
        if (currentMenuName.get() == name){
            return
        }
        currentMenuName.set(name)
        var view : View = when(name){
            MenuName.PATH_CAPTURE -> CustomViewFactory.getPathCaptureView(this)
            MenuName.BITMAP_SHADER -> CustomViewFactory.getBitmapShaderView(this)
            MenuName.SHUTTER_VIEW -> CustomViewFactory.getShutterView(this)
            MenuName.CHECKED_VIEW -> CustomViewFactory.getCheckedView(this)
            MenuName.CUSTOM_SWITCH -> CustomViewFactory.getCustomSwitch(this)
            MenuName.LARGE_IMAGE_VIEW -> CustomViewFactory.getLargeView(this)
            MenuName.CUSTOM_LAYOUT -> CustomViewFactory.getCustomLayout(this)
            else -> CustomViewFactory.getPathCaptureView(this)
        }
        addView(view)
        binding.drawerLayout.closeDrawer(binding.menuList)
    }

    private fun addView(view: View){
        if (binding.container.childCount!=0){
            binding.container.removeAllViews()
        }
        binding.container.addView(view)
    }
}