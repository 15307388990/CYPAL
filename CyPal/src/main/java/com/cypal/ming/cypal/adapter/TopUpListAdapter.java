package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.OrderModel;
import com.cypal.ming.cypal.dialogfrment.TradingDialog;
import com.cypal.ming.cypal.utils.SavePreferencesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class TopUpListAdapter extends RecyclerView.Adapter<TopUpListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    private List<OrderModel> mList;
    private OnClickListener onClickListener;
    private View mHeaderView;

    public TopUpListAdapter(Context context, List<OrderModel> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
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
            return new TopUpListAdapter.ViewHoler( mHeaderView );
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.item_top_up, parent, false );

        return new TopUpListAdapter.ViewHoler( itemView );
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final OrderModel sellBean = mList.get( position );
        holder.tv_topup_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TradingDialog tradingDialog = TradingDialog.newInstance( "" );
                tradingDialog.show( mContext );
            }
        } );


    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public void updateAdapter(ArrayList<OrderModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        //订单编号 金额  送达时间  配送信息 商家地址 距离 买家地址 备注
//        public TextView tv_uuid, tv_tprice, tv_dtime, tv_seller_addr, tv_distance, tv_user_addr, tv_remark, tv_created_at, tv_information, tv_type;
//        //拒单 接单
//        public Button btn_accept, btn_refuse;
//        public LinearLayout ll_layout, ll_layout2, ll_local;
//        public TextView tv_complete_price, tv_complete_number;
        private TextView tv_topup_btn;

        public ViewHoler(View itemView) {
            super( itemView );
            tv_topup_btn = (TextView) itemView.findViewById( R.id.tv_topup_btn );
//            tv_tprice = (TextView) itemView.findViewById(R.id.tv_tprice);
//            tv_dtime = (TextView) itemView.findViewById(R.id.tv_dtime);
        }
    }
}
