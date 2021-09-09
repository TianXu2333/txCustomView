package com.tx.txcustomview.menu
import androidx.recyclerview.widget.RecyclerView
import com.tx.txcustomview.databinding.LayoutMenuItemBinding


/**
 * create by xu.tian
 * @date 2021/9/9
 */
class MenuViewHolder: RecyclerView.ViewHolder {
    var binding :LayoutMenuItemBinding
    constructor(binding: LayoutMenuItemBinding) : super(binding.root) {
        this.binding = binding
    }
}