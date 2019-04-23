package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.BankListEntity;

import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 * 团队充值
 */

public class TeamTopUpAdapter extends RecyclerView.Adapter<TeamTopUpAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<BankListEntity.DataBean.BankCardsBean> mList;
    private OnClickListener onClickListener;


    public TeamTopUpAdapter(Context context, List<BankListEntity.DataBean.BankCardsBean> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {

        void OnClick(BankListEntity.DataBean.BankCardsBean dataBean);

        void onLongClick(BankListEntity.DataBean.BankCardsBean dataBean);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.team_top_up_list_item, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final BankListEntity.DataBean.BankCardsBean contentBean = mList.get(position);
        holder.tv_name.setText(contentBean.realName);
        holder.tv_account.setText(contentBean.accountName + ": " + contentBean.accountData);
        holder.iv_cb.setChecked(contentBean.used);
        holder.ll_yout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(contentBean);
            }
        });
        holder.iv_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(contentBean);
            }
        });
        holder.ll_yout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickListener.onLongClick(contentBean);
                return false;
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

    public void updateAdapter(List<BankListEntity.DataBean.BankCardsBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_account;
        private LinearLayout ll_yout;
        private CheckBox iv_cb;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_account = (TextView) itemView.findViewById(R.id.tv_account);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_yout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
            iv_cb = (CheckBox) itemView.findViewById(R.id.iv_cb);

        }
    }
}
