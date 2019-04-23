package com.cypal.ming.cypal.activityTwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.BankList;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.adapter.TeamTopUpAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.BankListEntity;
import com.cypal.ming.cypal.bean.CategoryEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cypal.ming.cypal.config.Const.config;
import static com.cypal.ming.cypal.config.Const.loginAdvancedVerify;

/**
 * 团队充值
 */
public class TeamTopUpActivity extends BaseActivity implements TeamTopUpAdapter.OnClickListener {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private TeamTopUpAdapter teamTopUpAdapter;
    private List<BankListEntity.DataBean.BankCardsBean> list;
    private TextView right_view_text;
    private LinearLayout ll_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_top_up);
        initView();
    }

    /**
     * 团队充值 银行列表
     */
    public void teamBankCard() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.teamBankCard, this, this, map, Request.Method.GET, this));
        loading();
    }

    /**
     * 使用领导人银行卡
     */
    public void useBankCard(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        mQueue.add(ParamTools.packParam(Const.useBankCard, this, this, this, map));
        loading();
    }

    /**
     * 删除领导人银行卡
     */
    public void delBankCard(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        mQueue.add(ParamTools.packParam(Const.delBankCard, this, this, this, map));
        loading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        teamBankCard();
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
        teamTopUpAdapter = new TeamTopUpAdapter(this, list, this);
        recycleView.setAdapter(teamTopUpAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        right_view_text = (TextView) findViewById(R.id.right_view_text);
        ll_add = (LinearLayout) findViewById(R.id.ll_add);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(TeamTopUpActivity.this, AddBankActivity.class, false);

            }
        });
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.teamBankCard)) {
            final BankListEntity entity = JSON.parseObject(data, BankListEntity.class);
            teamTopUpAdapter.updateAdapter(entity.data.bankCards);

        } else if (url.contains(Const.useBankCard)) {
            teamBankCard();
        } else if (url.contains(Const.delBankCard)) {
            teamBankCard();
        }

    }


    @Override
    public void OnClick(BankListEntity.DataBean.BankCardsBean dataBean) {
        useBankCard(dataBean.id + "");

    }

    @Override
    public void onLongClick(final BankListEntity.DataBean.BankCardsBean dataBean) {
        new CancelTheDealDialog().setTitle("温馨提示").setQtext("取消").setOktext("确定").
                setContext("确定删除该银行卡？").setOnClickListener(new CancelTheDealDialog.OnClickListener() {
            @Override
            public void successful() {
                delBankCard(dataBean.id + "");
            }
        }).show(TeamTopUpActivity.this);


    }
}
