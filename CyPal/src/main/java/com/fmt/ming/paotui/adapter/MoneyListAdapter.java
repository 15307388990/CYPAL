package com.fmt.ming.paotui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.activity.MoneyDetailsActivity;
import com.fmt.ming.paotui.bean.FinanceWithdrawBean;
import com.fmt.ming.paotui.utils.Tools;

import java.util.List;

/**
 * 打款列表
 */

public class MoneyListAdapter extends RecyclerView.Adapter<MoneyListAdapter.ViewHoler> {

    private final Context mContext;
    private List<FinanceWithdrawBean> mList;
    private final int mType;
    private String[] payType = {"", "微信", "支付宝"};


    public MoneyListAdapter(Context context, List<FinanceWithdrawBean> list, int type) {
        this.mContext = context;
        this.mList = list;
        this.mType = type;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_money, parent, false);

        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, final int position) {
        FinanceWithdrawBean moneyModel = mList.get(position);
        holder.tv_withdraw_info.setText(Tools.getFenYuan(moneyModel.getWithdraw_price()) + "");
        if (moneyModel.getWithdraw_stauts() == 1)//1； 提现中 2；提现成功 -1提现失败"
        {
            holder.tv_withdraw_end_date.setText("待打款");
            holder.tv_withdraw_end_date.setTextColor(mContext.getResources().getColor(R.color.yellow_f4));
            holder.tv_withdraw_price.setText(Tools.getDateformat(Long.parseLong(moneyModel
                    .getWithdraw_date())));
            holder.tv_withdraw_stauts.setText(Tools.getDateformat3(Long.parseLong(moneyModel
                    .getWithdraw_date())));
        } else if (moneyModel.getWithdraw_stauts() == 2) {
            holder.tv_withdraw_end_date.setText("已打款");
            holder.tv_withdraw_end_date.setTextColor(mContext.getResources().getColor(R.color.actionbar_title_color));
            holder.tv_withdraw_price.setText(Tools.getDateformat(Long.parseLong(moneyModel
                    .getWithdraw_end_date())));
            holder.tv_withdraw_stauts.setText(Tools.getDateformat3(Long.parseLong(moneyModel
                    .getWithdraw_end_date())));
        } else {
            holder.tv_withdraw_end_date.setText("打款失败");
            holder.tv_withdraw_end_date.setTextColor(mContext.getResources().getColor(R.color.jiujiujiu));
            holder.tv_withdraw_price.setText(Tools.getDateformat(Long.parseLong(moneyModel
                    .getWithdraw_date())));
            holder.tv_withdraw_stauts.setText(Tools.getDateformat3(Long.parseLong(moneyModel
                    .getWithdraw_date())));
        }
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoneyDetailsActivity.class);
                intent.putExtra("moneyModel", mList.get(position));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else if (mType == 0 && mList.size() > 0) {
            return 15;
        } else {
            return mList.size();
        }
    }

    public void updateAdapter(List<FinanceWithdrawBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {

        private final TextView tv_withdraw_info, tv_withdraw_price, tv_withdraw_end_date, tv_withdraw_stauts;
        private final LinearLayout ll_layout;

        public ViewHoler(View itemView) {
            super(itemView);

            tv_withdraw_info = (TextView) itemView.findViewById(R.id.tv_withdraw_info);
            tv_withdraw_price = (TextView) itemView.findViewById(R.id.tv_withdraw_price);
            tv_withdraw_end_date = (TextView) itemView.findViewById(R.id.tv_withdraw_end_date);
            tv_withdraw_stauts = (TextView) itemView.findViewById(R.id.tv_withdraw_stauts);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
        }
    }
}
