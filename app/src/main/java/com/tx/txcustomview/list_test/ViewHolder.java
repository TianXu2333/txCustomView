package com.tx.txcustomview.list_test;

import android.view.View;

import com.tx.txcustomview.databinding.TestItemBinding;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create by xu.tian
 *
 * @date 2021/9/22
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TestItemBinding getBinding() {
        return binding;
    }

    private TestItemBinding binding;
    public ViewHolder(@NonNull @NotNull TestItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding ;
    }
}
