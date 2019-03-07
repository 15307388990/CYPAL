package com.fmt.ming.paotui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.PopupMenuCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.activity.MapWebviewActivity;
import com.fmt.ming.paotui.activity.OrderDetailsActivity;
import com.fmt.ming.paotui.activity.WebviewActivity;
import com.fmt.ming.paotui.bean.OrderModel;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.SavePreferencesData;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class SellDetailListAdapter extends RecyclerView.Adapter<SellDetailListAdapter.ViewHoler> {

    private final Context mContext;
    private List<OrderModel> mList;
    private OnClickListener onClickListener;
    private SavePreferencesData mSavePreferencesData;
    private int ordertype;//订单类型

    public SellDetailListAdapter(Context context, List<OrderModel> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
        mSavePreferencesData = new SavePreferencesData(context);
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void Accept(String order_uuid);

        void Refuse(String order_uuid);

        void Details(String order_uuid);

        void callPhone(String phoneNum);

        // 取件 或者送达
        void takeandda(String order_uuid, String typestring);

    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyler_sell, parent, false);
        return new SellDetailListAdapter.ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        final OrderModel sellBean = mList.get(position);
        if (ordertype == 2) {
            holder.ll_layout.setVisibility(View.GONE);
            holder.ll_layout2.setVisibility(View.VISIBLE);
            holder.tv_complete_price.setText("订单金额：" + sellBean.getTprice() + "元");
            holder.tv_complete_number.setText("订单号：" + sellBean.getUuid());
            holder.ll_layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.Details(sellBean.getUuid());

                }
            });
        } else {
            holder.ll_layout.setVisibility(View.VISIBLE);
            holder.ll_layout2.setVisibility(View.GONE);
            holder.tv_uuid.setText(sellBean.getUuid());
            holder.tv_created_at.setText(sellBean.getCreated_at());
            holder.tv_tprice.setText(sellBean.getTprice());
            holder.tv_dtime.setText(sellBean.getDtime());
            holder.tv_seller_addr.setText(sellBean.getSeller_addr());
            holder.tv_distance.setText(sellBean.getDistance() + "米");
            holder.tv_user_addr.setText(sellBean.getUser_addr());
            holder.tv_remark.setText(sellBean.getRemark());
            // otype 订单类型(0:外卖,1:配送,2:跑腿)
            switch (sellBean.getOtype()) {
                case "0":
                    holder.tv_type.setText("外卖");
                    break;
                case "1":
                    holder.tv_type.setText("配送");
                    break;
                case "2":
                    holder.tv_type.setText("跑腿");
                    break;
            }
            String dasd = "";
            for (OrderModel.GoodsBean goodsesBean : sellBean.getGoods()) {
                dasd = dasd + goodsesBean.getGood().getName() + " 数量" + goodsesBean.getNums();

            }
            holder.tv_information.setText(dasd);
            //根据订单状态改变按钮名字
            switch (ordertype) {
                case 0://新订单
                    holder.btn_accept.setText("拒单");
                    holder.btn_refuse.setText("接单");
                    break;
                case 1://配送中

                    if (sellBean.getStatus().equals("4")) {
                        holder.btn_refuse.setText("送达");
                        holder.btn_accept.setText("联系客户");
                    } else {
                        holder.btn_refuse.setText("取件");
                        holder.btn_accept.setText("联系商家");
                    }

                    break;
                default:
                    break;
            }


            holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (ordertype) {
                        case 0://新订单
                            onClickListener.Accept(sellBean.getUuid());
                            break;
                        case 1://配送中
                            if (sellBean.getStatus().equals("4")) {
                                onClickListener.callPhone(sellBean.getUser_mobile());
                            } else {
                                onClickListener.callPhone(sellBean.getSeller_tel());
                            }

                            break;
                        default:
                            break;
                    }


                }
            });
            holder.btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (ordertype) {
                        case 0://新订单
                            onClickListener.Refuse(sellBean.getUuid());
                            break;
                        case 1://配送中
                            if (sellBean.getStatus().equals("4")) {
                                onClickListener.takeandda(sellBean.getUuid(), "送达");
                            } else {
                                onClickListener.takeandda(sellBean.getUuid(), "取件");
                            }
                            break;
                        default:
                            break;
                    }

                }
            });
            holder.ll_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.Details(sellBean.getUuid());

                }
            });

            holder.ll_local.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MapWebviewActivity.class);
                    intent.putExtra("link_url", Const.BASE_URL + Const.route + "?token=" + mSavePreferencesData.getStringData("token") + "&seller_lon=" + sellBean.getSeller_lon() + "&seller_lat=" + sellBean.getSeller_lat() + "&user_lon=" + sellBean.getUser_lon() + "&user_lat=" + sellBean.getUser_lat());
                    intent.putExtra("link_name", "查看路线");
                    intent.putExtra("order", sellBean);
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public void updateAdapter(ArrayList<OrderModel> mList, int ordertype) {
        this.mList = mList;
        this.ordertype = ordertype;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        //订单编号 金额  送达时间  配送信息 商家地址 距离 买家地址 备注
        public TextView tv_uuid, tv_tprice, tv_dtime, tv_seller_addr, tv_distance, tv_user_addr, tv_remark, tv_created_at, tv_information, tv_type;
        //拒单 接单
        public Button btn_accept, btn_refuse;
        public LinearLayout ll_layout, ll_layout2, ll_local;
        public TextView tv_complete_price, tv_complete_number;

        public ViewHoler(View itemView) {
            super(itemView);
            tv_uuid = (TextView) itemView.findViewById(R.id.tv_uuid);
            tv_tprice = (TextView) itemView.findViewById(R.id.tv_tprice);
            tv_dtime = (TextView) itemView.findViewById(R.id.tv_dtime);
            tv_seller_addr = (TextView) itemView.findViewById(R.id.tv_seller_addr);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            tv_user_addr = (TextView) itemView.findViewById(R.id.tv_user_addr);
            btn_accept = (Button) itemView.findViewById(R.id.btn_accept);
            btn_refuse = (Button) itemView.findViewById(R.id.btn_refuse);
            tv_created_at = (TextView) itemView.findViewById(R.id.tv_created_at);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
            tv_information = (TextView) itemView.findViewById(R.id.tv_information);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
            ll_layout2 = (LinearLayout) itemView.findViewById(R.id.ll_layout2);
            tv_complete_price = (TextView) itemView.findViewById(R.id.tv_complete_price);
            tv_complete_number = (TextView) itemView.findViewById(R.id.tv_complete_number);
            ll_local = (LinearLayout) itemView.findViewById(R.id.ll_local);
        }
    }
}
