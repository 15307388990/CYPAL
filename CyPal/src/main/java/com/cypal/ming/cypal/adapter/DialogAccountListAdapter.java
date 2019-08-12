package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AccountListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author luoming
 * @Date 2019/3/23 3:08 PM
 */

public class DialogAccountListAdapter extends RecyclerView.Adapter<DialogAccountListAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final Context mContext;
    List<AccountListEntity.DataBean> mList;
    private OnClickListener onClickListener;


    public DialogAccountListAdapter(Context context, List<AccountListEntity.DataBean> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void add(AccountListEntity.DataBean contentBean);

        void delete(AccountListEntity.DataBean contentBean);


    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.diglog_account_list_item, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHoler holder, final int position) {
        final AccountListEntity.DataBean contentBean = mList.get(position);
        //支付类型
        if (contentBean.accountType.equals("WXPAY")) {
            holder.tv_account.setText("微信账号：" + contentBean.realName + "+" + contentBean.accountName);
            holder.tv_type_pay.setText("微信");
            holder.tv_type.setText("微信");
            holder.iv_img.setImageResource(R.drawable.icon_weixin);

        } else if (contentBean.accountType.equals("ALIPAY")) {
            holder.iv_img.setImageResource(R.drawable.icon_zhifubao);
            holder.tv_account.setText("支付宝账号：" + contentBean.realName + "+" + contentBean.accountName);
            holder.tv_type_pay.setText("支付宝");
            holder.tv_type.setText("支付宝");
        } else if ( contentBean.accountType.equals("ALIPAY_PID")) {
            holder.iv_img.setImageResource(R.drawable.icon_zhifubao);
            holder.tv_account.setText("支付宝账号：" + contentBean.realName + "+" + contentBean.accountName);
            holder.tv_type_pay.setText("支付宝PID");
            holder.tv_type.setText("支付宝");
        }else if ( contentBean.accountType.equals("PDD")) {
            holder.iv_img.setImageResource(R.drawable.icon_pdd);
            holder.tv_account.setText("拼多多账号：" + contentBean.realName + "+" + contentBean.accountName);
            holder.tv_type_pay.setText("拼多多");
            holder.tv_type.setText("拼多多");
        }
        else {
            holder.iv_img.setImageResource(R.drawable.icon_yun);
            holder.tv_account.setText("云闪付账号：" + contentBean.realName + "+" + contentBean.accountName);
            holder.tv_type_pay.setText("云闪付");
            holder.tv_type.setText("云闪付");
        }
        if (contentBean.isx) {
            holder.tv_type_pay.setVisibility(View.VISIBLE);
        } else {
            holder.tv_type_pay.setVisibility(View.GONE);
        }
        holder.iv_cb.setChecked(contentBean.used);

        holder.iv_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onClickListener.add(contentBean);
                } else {
                    onClickListener.delete(contentBean);
                }
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

    public void updateAdapter(List<AccountListEntity.DataBean> mList) {
        this.mList = mList;
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);

    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private CheckBox iv_cb;
        private TextView tv_account;
        private TextView tv_type;
        private TextView tv_type_pay;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_account = (TextView) itemView.findViewById(R.id.tv_account);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_type_pay = (TextView) itemView.findViewById(R.id.tv_type_pay);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            iv_cb = (CheckBox) itemView.findViewById(R.id.iv_cb);

        }
    }
}
