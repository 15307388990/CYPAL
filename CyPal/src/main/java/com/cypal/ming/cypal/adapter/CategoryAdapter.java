package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.CategoryEntity;
import com.cypal.ming.cypal.bean.TopUpEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.TradingDialog;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<CategoryEntity.DataBean> mList;
    private OnClickListener onClickListener;
    private View mHeaderView;


    public CategoryAdapter(Context context, List<CategoryEntity.DataBean> list, OnClickListener onClickListener) {
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
            return new ViewHoler( mHeaderView );
        View itemView = LayoutInflater.from( mContext ).inflate( R.layout.category_item, parent, false );

        return new ViewHoler( itemView );
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final CategoryEntity.DataBean contentBean = mList.get( position );
        holder.tv_pay.setText( contentBean.value );
        holder.tv_pay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.ConfirmReceipt( contentBean.value, contentBean.type );
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

    public void updateAdapter(List<CategoryEntity.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_pay;

        public ViewHoler(View itemView) {
            super( itemView );
            tv_pay = (TextView) itemView.findViewById( R.id.tv_pay );

        }
    }
}
