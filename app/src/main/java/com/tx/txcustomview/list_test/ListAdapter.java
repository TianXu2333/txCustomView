package com.tx.txcustomview.list_test;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tx.txcustomview.databinding.TestItemBinding;

import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create by xu.tian
 *
 * @date 2021/9/22
 */
public class ListAdapter extends RecyclerView.Adapter<ViewHolder>{

    private ObservableArrayList<String> data ;

    public Handler handler = new Handler(Looper.getMainLooper());

    public ListAdapter(ObservableArrayList<String> data){
        this.data = data ;
        this.data.addOnListChangedCallback(new itemListener());
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TestItemBinding binding = TestItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.getBinding().text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private class itemListener extends ObservableList.OnListChangedCallback{

        @Override
        public void onChanged(ObservableList sender) {
            Log.d("ListAdapter","onChanged");
            ListAdapter.this.onChanged(sender);
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            Log.d("ListAdapter","onItemRangeChanged");
            ListAdapter.this.onItemRangeChanged(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            Log.d("ListAdapter","onItemRangeInserted");
            ListAdapter.this.rangeInsert(sender,positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            Log.d("ListAdapter","onItemRangeMoved");
            ListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            Log.d("ListAdapter","onItemRangeRemoved");
            ListAdapter.this.rangeRemoved(sender,positionStart,itemCount);
        }
    }

    private void onChanged(ObservableList sender){
        this.data = (ObservableArrayList<String>) sender;
        notifyDataSetChanged();
    }
    private void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount){
        this.data = (ObservableArrayList<String>) sender;
        notifyItemRangeChanged(positionStart, positionStart);
    }
    private void rangeRemoved(ObservableList sender, int positionStart, int itemCount){
        this.data = (ObservableArrayList<String>) sender;
        this.notifyItemRangeRemoved(positionStart, itemCount);
        Log.d("","rangeRemoved ---> "+positionStart);
    }
    private void rangeInsert(ObservableList sender, int positionStart, int itemCount){
        this.data = (ObservableArrayList<String>) sender;
        this.notifyItemRangeInserted(positionStart, itemCount);
        Log.d("","notifyItemRangeInserted ---> "+positionStart);
    }
}
