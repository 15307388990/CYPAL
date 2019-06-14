package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AmountDetaEntity;
import com.cypal.ming.cypal.bean.MassageBean;

import java.util.List;


/**
 * @Author luoming
 * @Date 2018/11/16 3:20 PM
 */

public class AmountDetailListAdapter extends RecyclerView.Adapter<AmountDetailListAdapter.ViewHoler> {

    private final Context mContext;
    private List<AmountDetaEntity.DataBean.ContentBean> mList;


    public AmountDetailListAdapter(Context context, List<AmountDetaEntity.DataBean.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_amount_detail, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final AmountDetaEntity.DataBean.ContentBean contentBean = mList.get(position);
        holder.tv_title.setText(contentBean.getTitle());
        holder.tv_timer.setText(contentBean.getCreateTime());
        holder.tv_balance.setText("余额：" + contentBean.getBalance());
        holder.tv_description.setText("描述：" + contentBean.getDescription());

        if (contentBean.getAmount() > 0) {
            holder.tv_amount.setTextColor(mContext.getResources().getColor(R.color.point_cash_green));
            holder.tv_amount.setText("+"+contentBean.getAmount());

        } else {
            holder.tv_amount.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.tv_amount.setText(contentBean.getAmount()+"");
        }


    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void updateAdapter(List<AmountDetaEntity.DataBean.ContentBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends ViewHolder {
        private TextView tv_title;
        private TextView tv_timer;
        private TextView tv_balance;
        private TextView tv_description;
        private TextView tv_amount;

        public ViewHoler(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_timer = (TextView) itemView.findViewById(R.id.tv_timer);
            tv_balance = (TextView) itemView.findViewById(R.id.tv_balance);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
        }
    }

}
