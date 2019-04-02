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
import com.cypal.ming.cypal.bean.ContentEntity;
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
    List<ContentEntity> mList;
    private OnClickListener onClickListener;
    private View mHeaderView;


    public ManagerAdapter(Context context, List<ContentEntity> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void Qiang(String orderId, String amount);


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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.manager_item, parent, false);
        return new ViewHoler(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final ContentEntity contentBean = mList.get(position);
        holder.tv_amount.setText("￥" + contentBean.amount);
        if (contentBean.isQian) {
            holder.tv_qiang.setVisibility(View.GONE);
            holder.tv_qian2.setVisibility(View.VISIBLE);
        } else {
            holder.tv_qiang.setVisibility(View.VISIBLE);
            holder.tv_qian2.setVisibility(View.GONE);
        }
        holder.tv_qian2.setText(contentBean.Text);
        holder.ll_yun.setEnabled(!contentBean.isQian);
        holder.ll_yun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.Qiang(contentBean.orderId + "", contentBean.amount + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public void updateAdapter(List<ContentEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_amount;
        private TextView tv_qiang, tv_qian2;

        private LinearLayout ll_yun;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_qiang = (TextView) itemView.findViewById(R.id.tv_qiang);
            tv_qian2 = (TextView) itemView.findViewById(R.id.tv_qian2);
            ll_yun = (LinearLayout) itemView.findViewById(R.id.ll_yun);
        }
    }
}
