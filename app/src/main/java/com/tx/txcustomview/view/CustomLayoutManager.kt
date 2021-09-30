package com.tx.txcustomview.view

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * create by xu.tian
 * @date 2021/9/15
 */
public class CustomLayoutManager : RecyclerView.LayoutManager() {

    var verticalOffset = 0
    var firstVisPos = 0
    var lastVisPos = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        // 如果为空，不填充
        if (itemCount == 0){
            detachAndScrapAttachedViews(recycler)
            return
        }
        //
        if (childCount == 0 && state.isPreLayout){
            return
        }
        detachAndScrapAttachedViews(recycler)
        verticalOffset =0
        firstVisPos = 0
        lastVisPos = itemCount
    }



}