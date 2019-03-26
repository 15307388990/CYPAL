package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.TopUpListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.TopUpState;
import com.cypal.ming.cypal.view.CircleImageView;
import com.lidroid.xutils.db.table.Id;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/26 7:21 PM
 */

public class TopUpRecordListAdapter extends RecyclerView.Adapter<TopUpRecordListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context mContext;
    private List<TopUpListEntity.DataBean.ContentBean> mList;
    private View mHeaderView;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();

    public TopUpRecordListAdapter(Context context, List<TopUpListEntity.DataBean.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
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
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.item_top_up_record, parent, false );
        return new ViewHoler( itemView );
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        if (getItemViewType( position ) == TYPE_HEADER) return;
        int pos = getRealPosition( holder );
        //TRADING交易中，CONFIRM待确认，SERVICE申诉客服处理，SUCCESS交易完成，CANCEL交易取消）
        final TopUpListEntity.DataBean.ContentBean contentBean = mList.get( pos );
        holder.tv_amount.setText( "￥" + contentBean.amount );
        holder.tv_timer.setText( contentBean.createTime );
        if (contentBean.statusEnum.equals( TopUpState.TRADING.toString() )) {
            holder.tv_staus.setText( "交易中" );
        } else if (contentBean.statusEnum.equals( TopUpState.SUCCESS.toString() )) {
            holder.tv_staus.setText( "交易完成" );
        } else if (contentBean.statusEnum.equals( TopUpState.SERVICE.toString())) {
            holder.tv_staus.setText( "申诉客服处理" );
        } else if (contentBean.statusEnum.equals( TopUpState.CONFIRM.toString() )) {
            holder.tv_staus.setText( "待确认" );
        } else if (contentBean.statusEnum.equals( TopUpState.CANCEL.toString() )) {
            holder.tv_staus.setText( "交易取消" );
        }
        if (contentBean.avatar == null) {
            imageLoader.displayImage( Const.USER_DEFAULT_ICON, holder.myicon, options );
        } else {
            imageLoader.displayImage( contentBean.avatar, holder.myicon,
                    options );

        }
        holder.tv_nickname.setText( contentBean.nickName );
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }


    public void updateAdapter(List<TopUpListEntity.DataBean.ContentBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private CircleImageView myicon;
        private TextView tv_nickname;
        private TextView tv_timer;
        private TextView tv_staus;
        private TextView tv_amount;

        public ViewHoler(View itemView) {
            super( itemView );
            tv_amount = (TextView) itemView.findViewById( R.id.tv_amount );
            tv_staus = (TextView) itemView.findViewById( R.id.tv_staus );
            tv_nickname = (TextView) itemView.findViewById( R.id.tv_nickname );
            tv_timer = (TextView) itemView.findViewById( R.id.tv_timer );
            myicon = (CircleImageView) itemView.findViewById( R.id.myicon );
        }
    }
}
