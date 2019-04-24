package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.adapter.CommissionAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.CommissionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.RewardDialog;
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
 * @author luoming 我的佣金
 */
public class CommissionAcitity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private ImageView right_view_text;
    private TextView btn_next;
    private RadioButton rd_phone;
    private RadioButton rd_email;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private RecyclerView recycleView;
    private TextView tv_myCommisionBalance;
    private TextView tv_totalCommision;
    private TextView tv_todayCommision;
    private TextView tv_todayTeamCommision;
    private RelativeLayout.LayoutParams params;
    private int cursorWidth;
    private String currentSelectTab = "INCOME";//收入或提现，INCOME/WITHDRAW
    private CommissionEntity commissionEntity;

    private List<CommissionEntity.DataBean.ListBean.ContentBean> list;
    private CommissionAdapter commissionAdapter;

    private SpringView springView;
    private int pageNumber = 1;
    boolean isPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_commission);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        pageNumber = 1;
        Commision();
    }

    private void Commision() {
        Map<String, String> map = new HashMap<>();
        map.put("type", currentSelectTab);
        map.put("page", pageNumber + "");
        map.put("size", "10");
        mQueue.add(ParamTools.packParam(Const.commision, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        springView = (SpringView) findViewById(R.id.springView);
        right_view_text = (ImageView) findViewById(R.id.right_view_text);
        btn_next = (TextView) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        rd_phone = (RadioButton) findViewById(R.id.rd_phone);
        rd_email = (RadioButton) findViewById(R.id.rd_email);
        rd_group = (RadioGroup) findViewById(R.id.rd_group);
        cursor = (LinearLayout) findViewById(R.id.cursor);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        tv_myCommisionBalance = (TextView) findViewById(R.id.tv_myCommisionBalance);
        tv_totalCommision = (TextView) findViewById(R.id.tv_totalCommision);
        tv_todayCommision = (TextView) findViewById(R.id.tv_todayCommision);
        tv_todayTeamCommision = (TextView) findViewById(R.id.tv_todayTeamCommision);
        rd_phone.setChecked(true);
        initEvent();
        list = new ArrayList<>();
        commissionAdapter = new CommissionAdapter(this, list);
        recycleView.setAdapter(commissionAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardDialog.newInstance("").show(CommissionAcitity.this);
            }
        });
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                Commision();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isPage) {
                    Commision();
                } else {
                    Tools.showToast(CommissionAcitity.this, "没有更多数据了");
                    springView.onFinishFreshAndLoad();
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (!TextUtils.isEmpty(commissionEntity.data.myCommisionBalance + "")) {
                    Intent intent = new Intent(CommissionAcitity.this, WithdrawalActivity.class);
                    intent.putExtra("amount", commissionEntity.data.myCommisionBalance + "");
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    protected void returnData(String data, String url) {
        springView.onFinishFreshAndLoad();
        commissionEntity = JSON.parseObject(data, CommissionEntity.class);
        tv_myCommisionBalance.setText(commissionEntity.data.myCommisionBalance + "");
        tv_totalCommision.setText(commissionEntity.data.totalCommision + "");
        tv_todayTeamCommision.setText(commissionEntity.data.todayTeamCommision + "");
        tv_todayCommision.setText(commissionEntity.data.todayCommision + "");
        if (pageNumber == 1) {
            list = commissionEntity.data.list.content;
        } else {
            list.addAll(commissionEntity.data.list.content);
        }

        commissionAdapter.updateAdapter(list);
        if (pageNumber < commissionEntity.data.list.totalPages) {
            isPage = true;
        } else {
            isPage = false;
        }

    }

    public void initEvent() {
        params = (RelativeLayout.LayoutParams) cursor.getLayoutParams();
        cursorWidth = params.width = Tools.getScreenWidth(CommissionAcitity.this) / 2;
        cursor.setLayoutParams(params);
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_phone) {
                    currentSelectTab = "INCOME";
                    params.leftMargin = 0;
                    cursor.setLayoutParams(params);
                } else if (checkedId == R.id.rd_email) {
                    currentSelectTab = "WITHDRAW";
                    params.leftMargin = cursorWidth;
                    cursor.setLayoutParams(params);

                }
                pageNumber = 1;
                Commision();
            }
        });
    }
}
