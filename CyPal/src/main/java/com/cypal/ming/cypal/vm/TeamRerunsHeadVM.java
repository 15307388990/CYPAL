package com.cypal.ming.cypal.vm;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.view.View;

import com.cypal.ming.cypal.BR;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;

public class TeamRerunsHeadVM implements BindingAdapterItemType, Observable {
    private int wuVisibility = View.GONE;


    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }

    @Override
    public int getViewType() {
        return R.layout.layout_team_head;
    }


    @Bindable
    public int getWuVisibility() {
        return wuVisibility;
    }

    public void setWuVisibility(int wuVisibility) {
        this.wuVisibility = wuVisibility;
        notifyChange(BR.wuVisibility);
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }
}
