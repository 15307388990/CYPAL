package com.fmt.ming.paotui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.activity.WebviewActivity;
import com.fmt.ming.paotui.bean.MassageBean;
import com.fmt.ming.paotui.config.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author luoming
 * @Date 2018/11/16 3:20 PM
 */

public class MagessListAdapter extends RecyclerView.Adapter<MagessListAdapter.ViewHoler> {

    private final Context mContext;
    private List<MassageBean> mList;
    private String token;

    public MagessListAdapter(Context context, List<MassageBean> list, String token) {
        this.mContext = context;
        this.mList = list;
        this.token = token;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_magss, parent, false);
        return new MagessListAdapter.ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final MassageBean massageBean = mList.get(position);
        holder.tv_titler.setText(massageBean.getTitle());
        holder.tv_timer.setText(massageBean.getSended_at());
        if (!massageBean.isIsread()) {
            holder.iv_img.setVisibility(View.INVISIBLE);
        } else {
            holder.iv_img.setVisibility(View.VISIBLE);
        }
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra("link_url", Const.BASE_URL + Const.messageshow + "?token=" + token + "&message_uuid=" + massageBean.getUuid());
                intent.putExtra("link_name", "消息详情");
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void updateAdapter(List<MassageBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends ViewHolder {
        //订单编号 金额  送达时间  配送信息 商家地址 距离 买家地址 备注
        public TextView tv_laiyuan, tv_titler, tv_timer;
        private LinearLayout ll_layout;
        private ImageView iv_img;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_laiyuan = (TextView) itemView.findViewById(R.id.tv_laiyuan);
            tv_titler = (TextView) itemView.findViewById(R.id.tv_title);
            tv_timer = (TextView) itemView.findViewById(R.id.tv_timer);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }

}
