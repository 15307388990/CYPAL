package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.CityEntity;
import com.cypal.ming.cypal.databinding.AddressItemContactBinding;
import com.cypal.ming.cypal.databinding.AddressItemIndexContactBinding;

import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * Created by luoming on 2018/9/29.
 */

public class SelectCityAapter extends IndexableAdapter<CityEntity> {

    private Context context;

    public SelectCityAapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        AddressItemIndexContactBinding banding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.address_item_index_contact, parent, false);
        return new IndexVH(banding);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        AddressItemContactBinding banding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.address_item_contact, parent, false);
        return new ContentVH(banding);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH indexVH = (IndexVH) holder;
        indexVH.getBinding().tvIndex.setText(indexTitle);

    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CityEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.getBinding().tvName.setText(entity.getCityName());



    }

    public class IndexVH extends RecyclerView.ViewHolder {
        private AddressItemIndexContactBinding binding;

        public IndexVH(AddressItemIndexContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public AddressItemIndexContactBinding getBinding() {
            return binding;
        }

        public void setBinding(AddressItemIndexContactBinding binding) {
            this.binding = binding;
        }
    }

    public class ContentVH extends RecyclerView.ViewHolder {
        private AddressItemContactBinding binding;

        public ContentVH(AddressItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public AddressItemContactBinding getBinding() {
            return binding;
        }

        public void setBinding(AddressItemContactBinding binding) {
            this.binding = binding;
        }
    }
}
