package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.bean.OtcOrderListEntity;
import com.cypal.ming.cypal.utils.OrderListState;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;

import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class OtcOrderListAdapter extends RecyclerView.Adapter<OtcOrderListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    private List<OtcOrderListEntity.DataBean.ContentBean> mList;
    private OnClickListener onClickListener;
    private SavePreferencesData mSavePreferencesData;
    private View mHeaderView;
    private long serverTime;


    public OtcOrderListAdapter(Context context, List<OtcOrderListEntity.DataBean.ContentBean> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
        mSavePreferencesData = new SavePreferencesData(context);
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        //确认收款
        void ConfirmReceipt(String order_uuid);

        //申诉订单
        void Complaint(String order_uuid);

    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyler_sell, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        int pos = getRealPosition(holder);
        final OtcOrderListEntity.DataBean.ContentBean sellBean = mList.get(pos);
        holder.tv_amount.setText("￥" + sellBean.amount);
        holder.tv_timer.setText(sellBean.takeTime);

        if (sellBean.payType.equals("WXPAY")) {
            holder.tv_paytype.setText("微信");
        } else if (sellBean.payType.equals("ALIPAY")) {
            holder.tv_paytype.setText("支付宝");
        } else if (sellBean.payType.equals("ALIPAY_PID")) {
            holder.tv_paytype.setText("支付宝PID");
        } else if (sellBean.payType.equals("PDD")) {
            holder.tv_paytype.setText("拼多多");
        } else {
            holder.tv_paytype.setText("云闪付");
        }
        holder.tv_number.setText(sellBean.orderNo);
        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.ConfirmReceipt(sellBean.id + "");
            }
        });
        holder.btn_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.Complaint(sellBean.id + "");
            }
        });
        //                A_PROCESS,//处理中，
//                B_BEAPPEAL,//被申诉
//                C_APPEAL,//主动申诉
//                D_SUCCESS,//支付成功
//                E_FAIL //失败取消
        holder.btn_ok.setVisibility(View.GONE);
        holder.btn_shen.setVisibility(View.GONE);
        if (sellBean.status.equals(OrderListState.A_PROCESS.toString())) {
            holder.btn_ok.setVisibility(View.VISIBLE);
            holder.btn_shen.setVisibility(View.VISIBLE);
            holder.tv_staus.setText("待确认");
            holder.btn_shen.setTextColor(mContext.getResources().getColor(R.color.CY_888888));
            holder.btn_shen.setEnabled(false);
            // 判断订单是否超时
            if (serverTime - Tools.getLongformat(sellBean.createTime) > 600000) {
                holder.tv_staus.setText("超时等待中");
                holder.btn_ok.setVisibility(View.VISIBLE);
                holder.btn_shen.setVisibility(View.VISIBLE);
                holder.btn_shen.setTextColor(mContext.getResources().getColor(R.color.CY_3776FB));
                holder.btn_shen.setEnabled(true);
            }
        } else if (sellBean.status.equals(OrderListState.B_BEAPPEAL.toString())) {
            holder.btn_ok.setVisibility(View.VISIBLE);
            holder.tv_staus.setText("被申诉");
        } else if (sellBean.status.equals(OrderListState.C_APPEAL.toString())) {
            holder.btn_ok.setVisibility(View.VISIBLE);
            holder.tv_staus.setText("申诉中");
        } else if (sellBean.status.equals(OrderListState.D_SUCCESS.toString())) {
            holder.tv_staus.setText("支付成功");
        } else if (sellBean.status.equals(OrderListState.E_FAIL.toString())) {
            holder.tv_staus.setText("失败取消");
        }


    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }


    public void updateAdapter(List<OtcOrderListEntity.DataBean.ContentBean> mList, long serverTime) {
        this.mList = mList;
        this.serverTime = serverTime;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_amount;
        private TextView tv_staus;
        private TextView tv_paytype;
        private TextView tv_number;
        private TextView tv_timer;
        private TextView btn_ok;
        private TextView btn_shen;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_staus = (TextView) itemView.findViewById(R.id.tv_staus);
            tv_paytype = (TextView) itemView.findViewById(R.id.tv_paytype);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_timer = (TextView) itemView.findViewById(R.id.tv_timer);
            btn_ok = (TextView) itemView.findViewById(R.id.btn_ok);
            btn_shen = (TextView) itemView.findViewById(R.id.btn_shen);
        }
    }
}
