package com.cypal.ming.cypal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.CategoryEntity;
import com.cypal.ming.cypal.bean.ManagerEntity;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 */

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<ManagerEntity> mList;
    private OnClickListener onClickListener;
    private View mHeaderView;


    public ManagerAdapter(Context context, List<ManagerEntity> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void Qiang(String orderId);


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
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.manager_item, parent, false );

        return new ViewHoler( itemView );
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final ManagerEntity contentBean = mList.get( position );
        holder.tv_amount.setText( "￥" + contentBean.content.amount );
        if (contentBean.content.isQian) {
            holder.tv_qiang.setText( "已抢光" );
            holder.tv_qiang.setTextColor( R.color.CY_999999 );
        } else {
            holder.tv_qiang.setText( "点击抢单" );
            holder.tv_qiang.setTextColor( R.color.CY_3776FB );
        }
        holder.ll_yun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.Qiang( contentBean.content.orderId + "" );
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

    public void updateAdapter(List<ManagerEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_amount;
        private TextView tv_qiang;
        private LinearLayout ll_yun;

        public ViewHoler(View itemView) {
            super( itemView );
            tv_amount = (TextView) itemView.findViewById( R.id.tv_amount );
            tv_qiang = (TextView) itemView.findViewById( R.id.tv_qiang );
            ll_yun = (LinearLayout) itemView.findViewById( R.id.ll_yun );
        }
    }
}
