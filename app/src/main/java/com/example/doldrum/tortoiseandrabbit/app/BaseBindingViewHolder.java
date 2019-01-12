package com.example.doldrum.tortoiseandrabbit.app;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BaseBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T binding;

    public BaseBindingViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public T getBinding(){
        return binding;
    }
}
