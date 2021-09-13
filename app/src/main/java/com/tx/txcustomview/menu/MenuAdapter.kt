package com.tx.txcustomview.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tx.txcustomview.databinding.LayoutMenuItemBinding

/**
 * create by xu.tian
 * @date 2021/9/9
 */
class MenuAdapter: RecyclerView.Adapter<MenuViewHolder>() {

    private var mData =  arrayListOf<MenuModel>()

    init {
        initMenuData()
    }

    interface OnItemClickListener{
        fun onClick(name:String)
    }

    lateinit var listener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        var binding = LayoutMenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.binding.itemBtn.text = mData[position].name
        holder.binding.itemBtn.setOnClickListener {
            if (listener!=null){
                listener.onClick(mData[position].name)
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    private fun initMenuData(){
        var pathCapture = MenuModel(MenuName.PATH_CAPTURE)
        mData.add(pathCapture)
        var bitmapShader = MenuModel(MenuName.BITMAP_SHADER)
        mData.add(bitmapShader)
        var shutterView = MenuModel(MenuName.SHUTTER_VIEW)
        mData.add(shutterView)
        var checkedView = MenuModel(MenuName.CHECKED_VIEW)
        mData.add(checkedView)
    }
}