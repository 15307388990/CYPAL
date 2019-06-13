package com.cypal.ming.cypal.activityTwo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.OrderListActivity;
import com.cypal.ming.cypal.adapter.AmountDetailListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AmountDetaEntity;
import com.cypal.ming.cypal.bean.CityEntity;
import com.cypal.ming.cypal.bean.OtcOrderListEntity;
import com.cypal.ming.cypal.bean.SelectCityEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoming
 * created at 2019/6/11 9:18 PM
 * 金额明细
 */
public class AmountDetailActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private SpringView springView;
    private LinearLayout ll_wu;
    private int pageNumber = 1;
    boolean isPage = true;
    private AmountDetailListAdapter amountDetailListAdapter;
    private List<AmountDetaEntity.DataBean.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_detail_list);
        initView();
        getCityList();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        springView = (SpringView) findViewById(R.id.springView);
        ll_view_back.setOnClickListener(this);
        ll_wu = (LinearLayout) findViewById(R.id.ll_wu);
        list = new ArrayList<>();
        amountDetailListAdapter = new AmountDetailListAdapter(this, list);
        recycleView.setAdapter(amountDetailListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                getCityList();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isPage) {
                    getCityList();
                } else {
                    Tools.showToast(AmountDetailActivity.this, "没有更多数据了");
                    springView.onFinishFreshAndLoad();
                }

            }
        });
    }

    /* 余额明细 */
    public void getCityList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", pageNumber + "");
        map.put("size", "10");
        mQueue.add(ParamTools.packParam(Const.balance, this, this, map, Request.Method.GET, this));
        loading();
    }


    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.balance)) {
            springView.onFinishFreshAndLoad();
            AmountDetaEntity entity = JSON.parseObject(data, AmountDetaEntity.class);
            if (pageNumber == 1) {
                list =entity.getData().getContent();
                if (list.size() < 1) {
                    springView.setVisibility(View.GONE);
                    ll_wu.setVisibility(View.VISIBLE);
                } else {
                    ll_wu.setVisibility(View.GONE);
                    springView.setVisibility(View.VISIBLE);
                }
            } else {
                list.addAll(entity.getData().getContent());
            }

            amountDetailListAdapter.updateAdapter(list);
            if (pageNumber < entity.getData().getTotalPages()) {
                isPage = true;
            } else {
                isPage = false;
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_view_back:
                finish();
                break;
        }
    }


}
