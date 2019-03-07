package com.fmt.ming.paotui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.bean.DeviceListBean;

import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class VenderListAdapter extends RecyclerView.Adapter<VenderListAdapter.ViewHoler> implements View.OnClickListener {

    private final Context mContext;
    private List<DeviceListBean> mList;
    private int selectId;
    private ItemClickListener itemClickListener;


    public VenderListAdapter(Context context, List<DeviceListBean> list, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mList = list;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_vender_list, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        DeviceListBean deviceListBean = mList.get(position);
        holder.tv_device_name.setText(deviceListBean.getDevice_name());
        holder.ll_layout.setTag(position);
        holder.ll_layout.setOnClickListener(this);
        if (selectId == position) {
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.off_select));
        } else {
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_select));
        }


    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }


    public void updateAdapter(List<DeviceListBean> mList, int selectId) {
        this.mList = mList;
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, (Integer) view.getTag());

    }

    class ViewHoler extends RecyclerView.ViewHolder {

        private final TextView tv_device_name;
        private final ImageView imageView;
        private final LinearLayout ll_layout;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_device_name = (TextView) itemView.findViewById(R.id.tv_device_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
        }
    }

    public interface ItemClickListener {

        /**
         * Item的普通点击
         */
        public void onItemClick(View view, int position);

        /**
         * Item长按
         */
        public void onItemLongClick(View view, int position);
    }
}
