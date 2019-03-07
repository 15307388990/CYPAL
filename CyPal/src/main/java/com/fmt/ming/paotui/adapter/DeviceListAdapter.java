package com.fmt.ming.paotui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.bean.DeviceListBean;

import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 * 设备列表
 */

public class DeviceListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<DeviceListBean> mList;

    public DeviceListAdapter(Context context, List<DeviceListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_device_list, null);
            holder.tv_device_name = (TextView) view.findViewById(R.id.tv_device_name);
            holder.tv_device_address = (TextView) view.findViewById(R.id.tv_device_address);
            holder.tv_device_status = (TextView) view.findViewById(R.id.tv_device_status);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        DeviceListBean deviceBean = mList.get(i);
        holder.tv_device_name.setText(deviceBean.getDevice_name());
        holder.tv_device_address.setText(deviceBean.getAddress());

        String device_status = deviceBean.getStatus();
        int black = mContext.getResources().getColor(R.color.two_gray);
        int red = mContext.getResources().getColor(R.color.red);
        //设备状态
        if ("ONLINE".equals(device_status)) {
            holder.tv_device_status.setText("在线");
            holder.tv_device_status.setTextColor(black);
        } else if ("OFFLINE".equals(device_status)) {
            holder.tv_device_status.setText("离线");
            holder.tv_device_status.setTextColor(black);
        } else if ("BREAKDOWN".equals(device_status)) {
            holder.tv_device_status.setText("故障");
            holder.tv_device_status.setTextColor(red);
        } else if ("UNKNOWN".equals(device_status)) {
            holder.tv_device_status.setText("未知");
            holder.tv_device_status.setTextColor(red);
        }
        return view;
    }

    static class ViewHolder {
        TextView tv_device_name, tv_device_address, tv_device_status;
    }
}
