package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.TopUpRecordListAdapter;
import com.cypal.ming.cypal.adapter.TransferRecordListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.TopUpListEntity;
import com.cypal.ming.cypal.bean.TransferRecordEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转账记录
 */
public class TransferRecordListActivity extends BaseActivity {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private TransferRecordListAdapter topUpListAdapter;
    private List<TransferRecordEntity.DataBean.ContentBean> list;
    private SpringView springView;
    private int pageNumber = 1;
    boolean isPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_record_list);
        initView();
    }

    private void record() {
        Map<String, String> map = new HashMap<>();
        map.put("page", pageNumber + "");
        map.put("size", "10");
        mQueue.add(ParamTools.packParam(Const.record, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData("token")));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        topUpListAdapter = new TransferRecordListAdapter(TransferRecordListActivity.this, list);
        recycleView.setAdapter(topUpListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                record();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isPage) {
                    record();
                } else {
                    Tools.showToast(TransferRecordListActivity.this, "没有更多数据了");
                    springView.onFinishFreshAndLoad();
                }

            }
        });
        record();
    }


    @Override
    protected void returnData(String data, String url) {
        springView.onFinishFreshAndLoad();
        TransferRecordEntity transferRecordEntity = JSON.parseObject(data, TransferRecordEntity.class);
        if (pageNumber < transferRecordEntity.data.totalPages) {
            isPage = true;
        } else {
            isPage = false;
        }
        if (pageNumber == 1) {
            list = transferRecordEntity.data.content;
        } else {
            list.addAll(transferRecordEntity.data.content);
        }

        topUpListAdapter.updateAdapter(list);

    }

}
