package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.AccountListAdapter;
import com.cypal.ming.cypal.adapter.WbhbListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.WbhbListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoming 微博红包
 */
public class WbhbListActivity extends BaseActivity {

    private LinearLayout ll_view_back;
    private TextView top_view_text;
    private RecyclerView recycleView;
    private WbhbListAdapter wbhbListAdapter;
    private LinearLayout ll_wu;
    private List<WbhbListEntity.DataBean.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbhb_list);
        initView();
        getBankBranchsByCityCode();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getBankBranchsByCityCode();
    }

    private void getBankBranchsByCityCode() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.wbhbList, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        ll_wu = (LinearLayout) findViewById(R.id.ll_wu);

        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
        wbhbListAdapter = new WbhbListAdapter(this, list);
        recycleView.setAdapter(wbhbListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.wbhbList)) {
            WbhbListEntity wbhbListEntity = JSON.parseObject(data, WbhbListEntity.class);
            if (wbhbListEntity.getData().getContent().size() > 0) {
                wbhbListAdapter.updateAdapter(wbhbListEntity.getData().getContent());
            } else {
                ll_wu.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
            }

        }
    }


}
