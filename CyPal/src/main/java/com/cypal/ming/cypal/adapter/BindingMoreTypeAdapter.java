package com.cypal.ming.cypal.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.cypal.ming.cypal.BR;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017/8/15
 * describe: 通用的DataBinding-RecyclerView-Adapter，兼容多种ItemType。
 */

public class BindingMoreTypeAdapter extends RecyclerView.Adapter<BindingMoreTypeAdapter.BindingHolder> {

    protected List<BindingAdapterItemType> mData = new ArrayList<>();
    protected BaseClickListener mListener;

    public BindingMoreTypeAdapter(BaseClickListener listener){
        this.mListener = listener;
    }

    public BindingMoreTypeAdapter(List<BindingAdapterItemType> data, BaseClickListener listener){
        this.mData = data;
        this.mListener = listener;
    }

    public List<BindingAdapterItemType> getData() {
        return mData;
    }

    public void setData(List<BindingAdapterItemType> data) {
        mData.clear();
        this.mData.addAll(data);
    }

    public void addData(List<BindingAdapterItemType> data) {
        this.mData.addAll(data);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new BindingHolder(binding,mListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindData(mData.get(position));
        holder.binding.executePendingBindings();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        BaseClickListener clickListener;

        public BindingHolder(ViewDataBinding binding, BaseClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = listener;
        }

        public void bindData(BindingAdapterItemType item) {
            binding.setVariable( BR.viewModel,item);
            if (clickListener != null) {
                binding.setVariable(BR.eventHandler,clickListener);
            }
        }
    }
}