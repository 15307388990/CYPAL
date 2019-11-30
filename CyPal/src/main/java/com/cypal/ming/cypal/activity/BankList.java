package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.AccountListAdapter;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.CategoryEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoming 支行列表
 */
public class BankList extends BaseActivity implements AccountListAdapter.OnClickListener {

    private LinearLayout ll_view_back;
    private TextView top_view_text;
    private RecyclerView recycleView;
    private LinearLayout ll_add;
    private String type;
    private String value;
    private AccountListAdapter accountListAdapter;
    private List<AccountListEntity.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banl_list);
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
        map.put("payAccountEnum", type);
        mQueue.add(ParamTools.packParam(Const.payAccount, this, this, map, Request.Method.GET, this));
        loading();
    }

    private void Use(String account_id) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", account_id);
        mQueue.add(ParamTools.packParam(Const.use, this, this, this, map));
        loading();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        value = getIntent().getStringExtra("value");
        top_view_text.setText(value);

        list = new ArrayList<>();
        accountListAdapter = new AccountListAdapter(this, list, this);
        View bottomView = LayoutInflater.from(this).inflate(R.layout.account_list_bottom_item, null);
        ll_add = (LinearLayout) bottomView.findViewById(R.id.ll_add);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (type.equals("PDD")) {
                    intent = new Intent(BankList.this, SavePddAccountAcitity.class);
                } else if (type.equals("CNP")) {
                    intent = new Intent(BankList.this, SaveCnpAccountAcitity.class);
                }else {
                    intent = new Intent(BankList.this, SaveAccountAcitity.class);
                }
                intent.putExtra("type", type);
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });
        accountListAdapter.setBottomView(bottomView);
        recycleView.setAdapter(accountListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.payAccount)) {
            AccountListEntity accountListEntity = JSON.parseObject(data, AccountListEntity.class);
            accountListAdapter.updateAdapter(accountListEntity.data);

        } else if (url.contains(Const.use)) {
            getBankBranchsByCityCode();
        }
    }

    @Override
    public void UseQie(String id) {
        Use(id);

    }

    @Override
    public void OnClick(AccountListEntity.DataBean dataBean) {
        Intent intent;
        if (type.equals("PDD")) {
            intent = new Intent(BankList.this, SavePddAccountAcitity.class);
        } else if (type.equals("CNP")) {
            intent = new Intent(BankList.this, SaveCnpAccountAcitity.class);
        } else {
            intent = new Intent(BankList.this, SaveAccountAcitity.class);
        }
        intent.putExtra("type", type);
        intent.putExtra("value", value);
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", dataBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
