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
 * @Author luoming
 * @Date 2019/4/7 3:23 PM
 * 好友转账
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private EditText et_amount;
    private Button btn_next;
    private TextView tv_amount;
    private String amount;
    private TextView right_view_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void getTransfer() {
        Map<String, String> map = new HashMap<>();
        map.put("account", et_amount.getText().toString().trim());
        mQueue.add(ParamTools.packParam(Const.getTransfer, this, this, map, Request.Method.GET, this));
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
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(TransferActivity.this, TransferRecordListActivity.class, false);
            }
        });
    }

    @Override
    protected void returnData(String data, String url) {
        Intent intent = new Intent(TransferActivity.this, TransfercConfirmActivity.class);
        intent.putExtra("data", data);
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
        getTransfer();
//        try {
//            if (Integer.valueOf( amount ) >= 1) {
//                withdraw();
//            } else {
//                Tools.showToast( TransferActivity.this, "转账金额只能为大于0的整数" );
//            }
//        } catch (NumberFormatException e) {
//            Tools.showToast( TransferActivity.this, "转账金额只能为整数" );
//            e.printStackTrace();
//            return;
//        }


    }
}
