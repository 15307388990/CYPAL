package com.fmt.ming.paotui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.OrderModel;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ImageLoaderUtil;
import com.fmt.ming.paotui.utils.MD5Util;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ll_view_back)
    LinearLayout llViewBack;
    @Bind(R.id.right_view_text)
    ImageView rightViewText;
    @Bind(R.id.top_view_text)
    TextView topViewText;
    @Bind(R.id.rl_layout)
    RelativeLayout rlLayout;
    @Bind(R.id.tv_uuid)
    TextView tvUuid;
    @Bind(R.id.tv_created_at)
    TextView tvCreatedAt;
    @Bind(R.id.tv_tprice)
    TextView tvTprice;
    @Bind(R.id.tv_dtime)
    TextView tvDtime;
    @Bind(R.id.tv_seller_addr)
    TextView tvSellerAddr;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_user_addr)
    TextView tvUserAddr;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    @Bind(R.id.tv_bagging)
    TextView tvBagging;
    @Bind(R.id.tv_freight)
    TextView tvFreight;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.btn_accept)
    Button btnAccept;
    @Bind(R.id.btn_refuse)
    Button btnRefuse;
    @Bind(R.id.tv_type)
    TextView tv_type;
    @Bind(R.id.tv_information)
    TextView tv_information;

    private String order_uuid;
    private OrderModel orderModel;
    private int ordertype;//订单类型
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.separator_color));
        ButterKnife.bind(this);
        order_uuid = getIntent().getStringExtra("order_uuid");
        ordertype = getIntent().getIntExtra("ordertype", 0);
        llViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ordershow();
    }

    /* 订单详情 */
    public void ordershow() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("order_uuid", order_uuid);
        map.put("israpp", "1");
        mQueue.add(ParamTools.packParam(Const.ordershow, this, this, map));
        loading();
    }

    public void initDate() {
        tvUuid.setText(orderModel.getUuid());
        tvCreatedAt.setText(orderModel.getCreated_at());
        tvTprice.setText(orderModel.getTprice());
        tvDtime.setText(orderModel.getDtime());
        tvSellerAddr.setText(orderModel.getSeller_addr());
        tvDistance.setText(orderModel.getDistance() + "米");
        tvUserAddr.setText(orderModel.getUser_addr());
        tvRemark.setText(orderModel.getRemark());
        tvPrice.setText("￥" + orderModel.getTprice() + "元");
        tvBagging.setText("￥" + orderModel.getBagging() + "元");
        tvFreight.setText("￥" + orderModel.getFreight() + "元");
        switch (orderModel.getOtype()) {
            case "0":
                tv_type.setText("外卖");
                break;
            case "1":
                tv_type.setText("配送");
                break;
            case "2":
                tv_type.setText("跑腿");
                break;
        }
        String dasd = "";
        for (OrderModel.GoodsBean goodsesBean : orderModel.getGoods()) {
            dasd = dasd + goodsesBean.getGood().getName() + " 数量" + goodsesBean.getNums();

        }
        tv_information.setText(dasd);
        for (OrderModel.GoodsBean goodsesBean : orderModel.getGoods()) {
            View itemView = LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.item_goods, llLayout, false);
            TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            TextView tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            TextView tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name.setText(goodsesBean.getGood().getName());
            tv_number.setText("X" + goodsesBean.getNums());
            tv_price.setText("￥" + goodsesBean.getPrice() + "元");
            imageLoader.displayImage(goodsesBean.getGood().getCover(), imageView,
                    options);
            llLayout.addView(itemView);

        }
        switch (ordertype) {
            case 0://新订单
                btnAccept.setText("拒单");
                btnRefuse.setText("接单");
                break;
            case 1://配送中
                btnAccept.setText("联系商家");
                if (orderModel.getStatus().equals("4")) {
                    btnRefuse.setText("送达");
                } else {
                    btnRefuse.setText("取件");
                }
                break;
            case 2://已完成
                btnAccept.setVisibility(View.GONE);
                btnRefuse.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordertype == 1) {
                    if (orderModel.getStatus().equals("4")) {
                        callPhone(orderModel.getUser_mobile());
                    } else {
                        callPhone(orderModel.getSeller_tel());
                    }
                } else if (ordertype == 0) {
                    deleteOrderDialog("拒单", orderModel.getUuid());

                }
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordertype == 1) {
                    if (orderModel.getStatus().equals("4")) {
                        takeandda(orderModel.getUuid(), "送达");
                    } else {
                        takeandda(orderModel.getUuid(), "取件");
                    }
                } else if (ordertype == 0) {
                    deleteOrderDialog("接单", orderModel.getUuid());
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, WebviewActivity.class);
                intent.putExtra("link_url", Const.BASE_URL + Const.route + "?token=" + mSavePreferencesData.getStringData("token") + "&seller_lon=" + orderModel.getSeller_lon() + "&seller_lat=" + orderModel.getSeller_lat() + "&user_lon=" + orderModel.getUser_lon() + "&user_lat=" + orderModel.getUser_lat());
                intent.putExtra("link_name", "查看路线");
                startActivity(intent);
            }
        });
    }

    public void takeandda(String order_uuid, String typestring) {
        Intent intent = new Intent(OrderDetailsActivity.this, TakeActivity.class);
        intent.putExtra("order_uuid", order_uuid);
        intent.putExtra("typestring", typestring);
        startActivity(intent);

    }



    private void callPhone(String phoneNum) {
        if (phoneNum.length() > 2) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        } else {
            Tools.showToast(OrderDetailsActivity.this, "没有提供手机");
        }

    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                String result = json.optString("data");
                orderModel = JSON.parseObject(result, OrderModel.class);
                initDate();
            } else if (stauts == 403) {
                Tools.showToast(getApplicationContext(), "登录过期请重新登录");
                Tools.jump(OrderDetailsActivity.this, LoginActivity.class, true);
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }

    private void deleteOrderDialog(final String text, final String order_uuid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder.setMessage("您确定要" + text + "?");
        builder.setTitle(text);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (text.equals("接单")) {
                    update(order_uuid, 1);

                } else if (text.equals("拒单")) {
                    update(order_uuid, 0);
                }

            }
        });
        builder.create().show();
    }

    /**
     * @param order_uuid
     * @param action     操作类型{0:拒单,1:接单,2:取件,3:完成
     */
    public void update(String order_uuid, int action) {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        map.put("order_uuid", order_uuid);
        map.put("action", action + "");//操作类型{0:拒单,1:接单,2:取件,3:完成
        mQueue.add(ParamTools.packParam(Const.update, this, this, map));
        loading();
    }

}
