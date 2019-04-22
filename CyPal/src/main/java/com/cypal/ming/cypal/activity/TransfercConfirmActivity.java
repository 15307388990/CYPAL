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

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.TransferEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author luoming
 * @Date 2019/4/7 3:23 PM
 * 转账 确认页面
 */
public class TransfercConfirmActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private EditText et_amount;
    private Button btn_next;
    private TextView tv_amount;
    private TextView right_view_text;
    private TextView tv_account;
    private CircleImageView myicon;
    private TextView tv_all;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();
    private TextView tv_pay_name;
    private TextView tv_nickname;
    private TransferEntity transferEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_confirm);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void postTransfer() {
        Map<String, String> map = new HashMap<>();
        map.put("money", et_amount.getText().toString().trim());
        map.put("account", transferEntity.data.friend.account);
        mQueue.add(ParamTools.packParam(Const.postTransfer, this, this, this, map));
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
        tv_account = (TextView) findViewById(R.id.tv_account);
        myicon = (CircleImageView) findViewById(R.id.myicon);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_pay_name = (TextView) findViewById(R.id.tv_pay_name);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_amount.setText(transferEntity.data.balance + "");
            }
        });
        initDate();
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(TransfercConfirmActivity.this, TransferRecordListActivity.class, false);
            }
        });

    }

    private void initDate() {
        String account = getIntent().getStringExtra("account");
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        mQueue.add(ParamTools.packParam(Const.getTransfer, this, this, map, Request.Method.GET, this));
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.postTransfer)) {
            Intent intent = new Intent(TransfercConfirmActivity.this, SucceedWithdrawAccessActivity.class);
            intent.putExtra("title", "转账结果");
            intent.putExtra("context", "转账成功");
            startActivity(intent);
            finish();
        }else if(url.contains(Const.getTransfer)){
            if (!TextUtils.isEmpty(data)) {
                transferEntity = JSON.parseObject(data, TransferEntity.class);
                tv_amount.setText("可用余额：：" + transferEntity.data.balance);
                if (TextUtils.isEmpty(transferEntity.data.friend.avatar)) {
                    imageLoader.displayImage(Const.USER_DEFAULT_ICON, myicon, options);
                } else {
                    imageLoader.displayImage(transferEntity.data.friend.avatar, myicon,
                            options);
                }
                tv_pay_name.setText("正在向" + transferEntity.data.friend.nickName + "付款");
                tv_nickname.setText(transferEntity.data.friend.account);
            }

        }
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
        postTransfer();

//        try {
//            if (Integer.valueOf(amount) >= 1) {
//                postTransfer();
//            } else {
//                Tools.showToast(TransfercConfirmActivity.this, "转账金额只能为大于0的整数");
//            }
//        } catch (NumberFormatException e) {
//            Tools.showToast(TransfercConfirmActivity.this, "转账金额只能为整数");
//            e.printStackTrace();
//            return;
//        }


    }
}
