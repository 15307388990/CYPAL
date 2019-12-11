package com.cypal.ming.cypal.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.WbhbListEntity;
import com.cypal.ming.cypal.utils.Tools;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 */

public class WbhbListAdapter extends RecyclerView.Adapter<WbhbListAdapter.ViewHoler> {
    private final Context mContext;
    List<WbhbListEntity.DataBean.ContentBean> mList;

    public WbhbListAdapter(Context context, List<WbhbListEntity.DataBean.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.wbhb_list_item, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final WbhbListEntity.DataBean.ContentBean contentBean = mList.get(position);

        holder.tv_amount.setText("￥" + contentBean.getAmount());
        holder.tv_kou.setText("口令：" + contentBean.getPass());
        holder.tv_number.setText(contentBean.getOrderNo());
        holder.tv_kou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri url = Uri.parse(contentBean.getTakeUrl());
//                intent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
//                intent.setData(url);
//                mContext.startActivity(intent);
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(contentBean.getTakeUrl());
                Tools.showToast(mContext,"复制成功，请在UC，或者系统浏览器中打开");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(List<WbhbListEntity.DataBean.ContentBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_amount;
        private TextView tv_number;
        private TextView tv_kou;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_kou = (TextView) itemView.findViewById(R.id.tv_kou);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);

        }
    }
}
