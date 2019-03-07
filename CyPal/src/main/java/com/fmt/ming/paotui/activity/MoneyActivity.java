package com.fmt.ming.paotui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.MoneyListAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.FinanceWithdrawBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的钱包
 */
public class MoneyActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.top_view_text)
    TextView topViewText;
    @Bind(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.springView)
    SpringView springView;
    @Bind(R.id.ll_list_content)
    LinearLayout llListContent;
    @Bind(R.id.ll_list_none)
    LinearLayout llListNone;
    @Bind(R.id.ll_freeze)
    LinearLayout ll_freeze;

    private int pageNumber = 1;
    private List<FinanceWithdrawBean> mList;
    private MoneyListAdapter moneyListAdapter;
    private TextView tv_can_withdraw_price;
    private TextView tv_money;
    private TextView tv_total_income;
    private TextView tv_already_withdraw_price;
    private TextView tv_freeze_price;
    private int pagNumber = 1;// 页码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        initView();
        ButterKnife.bind(this);
        initViews();
        FinanceIndex();
    }

    @Override
    protected void onResume() {
        FinanceIndex();
        super.onResume();
    }

    private void initViews() {
        initTitle();
        tvMoney.setOnClickListener(this);
        ll_freeze.setOnClickListener(this);
        title.setText("我的钱包");
        mList = new ArrayList<FinanceWithdrawBean>();
        moneyListAdapter = new MoneyListAdapter(this, mList, 1);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(moneyListAdapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                completeWithdraw();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                completeWithdraw();
            }
        });
    }


    //我的钱包
    public void FinanceIndex() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
        mQueue.add(ParamTools.packParam(Const.FinanceIndex, this, this, map));
        loading();
    }


    public void completeWithdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
        map.put("pageNum", pagNumber + "");
        map.put("pageSize", 10 + "");
        mQueue.add(ParamTools.packParam(Const.completeWithdraw, this, this,
                map));
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                if (url.contains(Const.FinanceIndex)) {
                    JSONObject jsonObject = json.getJSONObject("result");
                    tv_can_withdraw_price.setText(Tools.getFenYuan(jsonObject.optInt("can_withdraw_price")) + "");
                    tv_freeze_price.setText(Tools.getFenYuan(jsonObject.optInt("freeze_price")) + "");
                    tv_already_withdraw_price.setText(Tools.getFenYuan(jsonObject.optInt("already_withdraw_price")) + "");
                    tv_total_income.setText(Tools.getFenYuan(jsonObject.optInt("total_income")) + "");
                    completeWithdraw();

                } else if (url.contains(Const.completeWithdraw)) {
                    String settle_list = json.optString("result");
                    springView.onFinishFreshAndLoad();
                    if (settle_list.length() > 4) {
                        mList = JSON.parseArray(settle_list, FinanceWithdrawBean.class);
                    }
                    showSellList();


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


    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("首次使用该功能\n请先设置提现账户");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)

            {
                // TODO Auto-generated method stub
                Intent it = new Intent(MoneyActivity.this, AddAccount2Activity.class);
                startActivity(it);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showSellList() {
        if (mList != null) {
            if (mList.size() > 0) {
                llListContent.setVisibility(View.VISIBLE);
                llListNone.setVisibility(View.GONE);
                moneyListAdapter.updateAdapter(mList);
            } else {
                llListContent.setVisibility(View.GONE);
                llListNone.setVisibility(View.VISIBLE);
            }
        } else {
            llListContent.setVisibility(View.GONE);
            llListNone.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money:
                Intent intent = new Intent(MoneyActivity.this, ApplyWithdrawActivity.class);
                intent.putExtra("sum", tv_can_withdraw_price.getText().toString());
                startActivity(intent);
                break;
            case R.id.ll_freeze:
               // Tools.jump(MoneyActivity.this, CashFreezeActivity.class, false);
                break;
        }
    }

    private void initView() {
        tv_can_withdraw_price = (TextView) findViewById(R.id.tv_can_withdraw_price);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_total_income = (TextView) findViewById(R.id.tv_total_income);
        tv_already_withdraw_price = (TextView) findViewById(R.id.tv_already_withdraw_price);
        tv_freeze_price = (TextView) findViewById(R.id.tv_freeze_price);
    }
}
