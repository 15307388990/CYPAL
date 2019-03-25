package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.ContentEntity;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.utils.SavePreferencesData;

import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class SellDetailListAdapter extends RecyclerView.Adapter<SellDetailListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    private List<IndexEntity.DataBean.UndoOrderBean.ContentBean> mList;
    private OnClickListener onClickListener;
    private SavePreferencesData mSavePreferencesData;
    private View mHeaderView;


    public SellDetailListAdapter(Context context, List<IndexEntity.DataBean.UndoOrderBean.ContentBean> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
        mSavePreferencesData = new SavePreferencesData( context );
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
        notifyItemInserted( 0 );
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
            return new ViewHoler( mHeaderView );
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.item_recyler_sell, parent, false );
        return new ViewHoler( itemView );
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        if (getItemViewType( position ) == TYPE_HEADER) return;
        int pos = getRealPosition( holder );
        final IndexEntity.DataBean.UndoOrderBean.ContentBean sellBean = mList.get( pos );
        holder.tv_amount.setText( "￥" + sellBean.amount );
        holder.tv_timer.setText( sellBean.takeTime );
        holder.tv_staus.setText( sellBean.statusDesc );
        if (sellBean.payType.equals( "WXPAY" )) {
            holder.tv_paytype.setText( "微信" );
        } else if (sellBean.payType.equals( "ALIPAY" )) {
            holder.tv_paytype.setText( "支付宝" );
        } else {
            holder.tv_paytype.setText( "云闪付" );
        }
        holder.tv_number.setText( sellBean.orderNo );
        holder.btn_ok.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.ConfirmReceipt( sellBean.id + "" );
            }
        } );
        holder.btn_shen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.Complaint( sellBean.id + "" );
            }
        } );

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }


    public void updateAdapter(List<IndexEntity.DataBean.UndoOrderBean.ContentBean> mList) {
        this.mList = mList;
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
            super( itemView );
            tv_amount = (TextView) itemView.findViewById( R.id.tv_amount );
            tv_staus = (TextView) itemView.findViewById( R.id.tv_staus );
            tv_paytype = (TextView) itemView.findViewById( R.id.tv_paytype );
            tv_number = (TextView) itemView.findViewById( R.id.tv_number );
            tv_timer = (TextView) itemView.findViewById( R.id.tv_timer );
            btn_ok = (TextView) itemView.findViewById( R.id.btn_ok );
            btn_shen = (TextView) itemView.findViewById( R.id.btn_shen );
        }
    }
}
