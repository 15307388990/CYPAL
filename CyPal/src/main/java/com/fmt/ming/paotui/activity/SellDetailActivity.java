package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.SellDetailListAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.OrderModel;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.fmt.ming.paotui.view.CustomDatePicker;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 售货明细
 */
public class SellDetailActivity extends BaseActivity {

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.springView)
    SpringView springView;

    @Bind(R.id.ll_list_content)
    LinearLayout llListContent;
    @Bind(R.id.ll_list_none)
    LinearLayout llListNone;
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Bind(R.id.tv_star_timer)
    TextView tv_star_timer;//开始日期
    @Bind(R.id.tv_end_timer)
    TextView tv_end_timer;//结束日期
    @Bind(R.id.tv_ok)
    TextView tv_ok;//确定
    private CustomDatePicker customDatePicker1, customDatePicker2;

    private String starTimer, endTimer, boxId;

    private int pageNumber = 1;
    private SellDetailListAdapter sellDetailListAdapter;
    private ArrayList<OrderModel> orderModels;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    String now = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_detail);
        ButterKnife.bind(this);
        starTimer = getIntent().getStringExtra("starTime");
        endTimer = getIntent().getStringExtra("endTime");
        boxId = getIntent().getStringExtra("boxId");

        tv_star_timer.setText(starTimer);
        tv_end_timer.setText(endTimer);
        initViews();
        orderList();
    }

    private void initViews() {
        initTitle();
        imgBack.setVisibility(View.VISIBLE);
        title.setText("订单明细");
        rightView.setVisibility(View.GONE);
        orderModels = new ArrayList<OrderModel>();
      //  sellDetailListAdapter = new SellDetailListAdapter(this, orderModels);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(sellDetailListAdapter);

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                orderList();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                orderList();
            }
        });
        initDatePicker();
        tv_star_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_star_timer.getText().toString().equals("")) {
                    customDatePicker1.show(now);
                } else {
                    customDatePicker1.show(tv_star_timer.getText().toString());
                }
            }
        });
        tv_end_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_end_timer.getText().toString().equals("")) {
                    customDatePicker2.show(now);
                } else {
                    customDatePicker2.show(tv_end_timer.getText().toString());
                }
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderList();
            }
        });
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initDatePicker() {
        final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间

                if (!endTimer.equals("")) {
                    try {
                        if (sdf2.parse(endTimer).getTime() < sdf2.parse(time).getTime()) {
                            Tools.showToast(SellDetailActivity.this, "开始时间不能大于等于结束时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                starTimer = time;
                tv_star_timer.setText(time);
            }
        }, "2018-01-01 00:00", "2025-12-28 00:00"); // 初始化日期格式请用：yyyy-MM-dd，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                if (!starTimer.equals("")) {
                    try {
                        if (sdf2.parse(starTimer).getTime() > sdf2.parse(time).getTime()) {
                            Tools.showToast(SellDetailActivity.this, "结束时间不能小于等于开始时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                endTimer = time;
                tv_end_timer.setText(time);
            }
        }, "2018-01-01 00:00", "2025-12-28 00:00"); // 初始化日期格式请用：yyyy-MM-dd，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    //售货明细列表
    public void orderList() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
        map.put("pageNumber", pageNumber + "");
        map.put("page_size", 10 + "");
        map.put("order_state", "4");
        map.put("start_time", tv_star_timer.getText().toString());
        map.put("end_time", tv_end_timer.getText().toString());
        mQueue.add(ParamTools.packParam(Const.getVenderOrderList, this, this, map));
        loading();
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                if (url.contains(Const.getVenderOrderList)) {
                    String result = json.optString("result");
                    ArrayList<OrderModel> list = (ArrayList<OrderModel>) JSON.parseArray(result, OrderModel.class);
                    springView.onFinishFreshAndLoad();
                    showSellList(list);
                }
            } else {
                Tools.showToast(this, msg);
                springView.onFinishFreshAndLoad();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }

    private void showSellList(ArrayList<OrderModel> list) {
        if (orderModels.size() > 0 || list.size() > 0) {
            llListContent.setVisibility(View.VISIBLE);
            llListNone.setVisibility(View.GONE);
            if (pageNumber != 1) {
                orderModels.addAll(list);
            } else {
                orderModels = list;
            }
          //  sellDetailListAdapter.updateAdapter(orderModels);
        } else {
            llListContent.setVisibility(View.GONE);
            llListNone.setVisibility(View.VISIBLE);
        }
    }

}
