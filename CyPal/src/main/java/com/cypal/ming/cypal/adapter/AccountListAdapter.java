package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.lidroid.xutils.db.sqlite.DbModelSelector;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<AccountListEntity.DataBean> mList;
    private OnClickListener onClickListener;


    public AccountListAdapter(Context context, List<AccountListEntity.DataBean> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void UseQie(String id);

        void OnClick(AccountListEntity.DataBean dataBean);


    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.account_list_item, parent, false );
        return new ViewHoler( itemView );
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final AccountListEntity.DataBean contentBean = mList.get( position );
        //支付类型
        if (contentBean.accountType.equals( "WXPAY" )) {
            holder.iv_img.setImageResource( R.drawable.icon_weixin );
        } else if (contentBean.accountType.equals( "ALIPAY" )) {
            holder.iv_img.setImageResource( R.drawable.icon_zhifubao );
        } else {
            holder.iv_img.setImageResource( R.drawable.icon_yun );
        }
        holder.tv_account.setText( contentBean.accountName );
        if (contentBean.used) {
            holder.tv_qie.setVisibility( View.GONE );
            holder.tv_staus.setVisibility( View.VISIBLE );
        } else {
            holder.tv_qie.setVisibility( View.VISIBLE );
            holder.tv_staus.setVisibility( View.GONE );
        }
        holder.tv_qie.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.UseQie( contentBean.id + "" );
            }
        } );
        holder.tv_account.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick( contentBean );
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

    public void updateAdapter(List<AccountListEntity.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private TextView tv_account;
        private TextView tv_staus;
        private TextView tv_qie;

        public ViewHoler(View itemView) {
            super( itemView );
            tv_account = (TextView) itemView.findViewById( R.id.tv_account );
            tv_staus = (TextView) itemView.findViewById( R.id.tv_staus );
            tv_qie = (TextView) itemView.findViewById( R.id.tv_qie );
            iv_img = (ImageView) itemView.findViewById( R.id.iv_img );

        }
    }
}
