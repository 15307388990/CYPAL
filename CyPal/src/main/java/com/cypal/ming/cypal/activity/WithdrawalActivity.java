package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoming 提现到余额
 */
public class WithdrawalActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private EditText et_amount;
    private Button btn_next;
    private TextView tv_amount;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void withdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("amount", et_amount.getText().toString().trim());
        mQueue.add(ParamTools.packParam(Const.withdraw, this, this, map, Request.Method.GET, this));
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
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_amount = (EditText) findViewById(R.id.et_amount);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        amount = getIntent().getStringExtra("amount");
        tv_amount.setText("可提取金额：" + amount + " CNY");
    }

    @Override
    protected void returnData(String data, String url) {
        Intent intent = new Intent(WithdrawalActivity.this, SucceedWithdrawAccessActivity.class);
        intent.putExtra("title", "提现到余额");
        intent.putExtra("context", "提现成功");
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String amount = et_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Integer.valueOf(amount) >= 1) {
                withdraw();
            } else {
                Tools.showToast(WithdrawalActivity.this, "充值金额只能为大于0的整数");
            }
        } catch (NumberFormatException e) {
            Tools.showToast(WithdrawalActivity.this, "提现金额只能为整数");
            e.printStackTrace();
            return;
        }


    }
}
