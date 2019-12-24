package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.CommissionEntity;
import com.cypal.ming.cypal.utils.Tools;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 * 佣金
 */

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<CommissionEntity.DataBean.ListBean.ContentBean> mList;
    private OnClickListener onClickListener;
    private View mHeaderView;


    public CommissionAdapter(Context context, List<CommissionEntity.DataBean.ListBean.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void ConfirmReceipt(String value, String type);


    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new ViewHoler(mHeaderView);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.commission_item, parent, false);

        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final CommissionEntity.DataBean.ListBean.ContentBean contentBean = mList.get(position);
        if (!TextUtils.isEmpty(contentBean.commisionEnum)) {
            if (contentBean.commisionEnum.equals("OTC")) {
                if (TextUtils.equals(contentBean.payType, "WXPAY")) {
                    holder.tv_type.setText("接单分佣：微信");
                } else if (TextUtils.equals(contentBean.payType, "ALIPAY") || TextUtils.equals(contentBean.payType, "ALIPAY_PID")) {
                    holder.tv_type.setText("接单分佣：支付宝");
                } else if (TextUtils.equals(contentBean.payType, "PDD")) {
                    holder.tv_type.setText("接单分佣：拼多多");
                } else if (TextUtils.equals(contentBean.payType, "CNP")) {
                    holder.tv_type.setText("接单分佣：吹牛皮");
                } else if (TextUtils.equals(contentBean.payType, "ZZ")) {
                    holder.tv_type.setText("接单分佣：转转");
                }else if (TextUtils.equals(contentBean.payType, "WBHB")) {
                    holder.tv_type.setText("接单分佣：微博红包");
                }else {
                    holder.tv_type.setText("接单分佣：云闪付");
                }
            } else if (contentBean.commisionEnum.equals("ATEAM")) {
                holder.tv_type.setText("A级好友接单的分佣");
            } else if (contentBean.commisionEnum.equals("BTEAM")) {
                holder.tv_type.setText("B级好友接单的分佣");
            }
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_amount.setText("交易金额：" + contentBean.amount);
            holder.tv_income.setText("+" + contentBean.incomeMoney);
            holder.tv_ratio.setVisibility(View.VISIBLE);
            holder.tv_ratio.setText(Tools.getDoubleformat(contentBean.rate * 100) + "%");
        } else {
            holder.tv_amount.setText("佣金提现转出");
            holder.tv_income.setText("-" + contentBean.amount);
            holder.tv_type.setText("");
            holder.tv_type.setVisibility(View.GONE);
            holder.tv_ratio.setVisibility(View.GONE);
        }
        holder.tv_timer.setText(contentBean.createTime);


    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public void updateAdapter(List<CommissionEntity.DataBean.ListBean.ContentBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_type;
        private TextView tv_amount;
        private TextView tv_timer;
        private TextView tv_income;
        private TextView tv_ratio;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_timer = (TextView) itemView.findViewById(R.id.tv_timer);
            tv_income = (TextView) itemView.findViewById(R.id.tv_income);
            tv_ratio = (TextView) itemView.findViewById(R.id.tv_ratio);

        }
    }
}
