package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.MyCountTimer;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @author luoming 添加转转收款账号
 */
public class SaveZZAccountAcitity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private TextView top_view_text;
    private EditText et_accout;
    private EditText et_name;
    private Button btn_next;
    private String type;
    private String value;
    private TextView tv_account;
    private AccountListEntity.DataBean dataBean;
    private TextView right_view_text;
    private String accountId;
    private CountDownTimer countTimer;
    private EditText et_code;
    private TextView tv_code;
    private LinearLayout ll_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_save_zz_account);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("SelectCity", "onResume");
    }

    private void getBankBranchsByCityCode() {
        Map<String, String> map = new HashMap<>();
        map.put("accountName", et_accout.getText().toString().trim());
        map.put("realName", et_name.getText().toString().trim());
        map.put("accountData", et_code.getText().toString().trim());
        map.put("accountType", "ZZ");
        mQueue.add(ParamTools.packParam(Const.payAccountSave, this, this, this, map));
        loading();
    }

    private void del() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(accountId)) {
            map.put("account_id", accountId);
        }
        mQueue.add(ParamTools.packParam(Const.del, this, this, this, map));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        value = getIntent().getStringExtra("value");
        dataBean = (AccountListEntity.DataBean) getIntent().getSerializableExtra("date");
        top_view_text.setText("添加" + value);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        et_accout = (EditText) findViewById(R.id.et_accout);
        et_name = (EditText) findViewById(R.id.et_name);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_code = (TextView) findViewById(R.id.tv_code);
        btn_next = (Button) findViewById(R.id.btn_next);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);
        countTimer = new MyCountTimer(this, tv_code, "获取验证码", R.color.tab_text_color_select, R.color.darkgray);
        btn_next.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setText(value + "账号");
        et_accout.setHint("请输入" + value + "账号");
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveZZAccountAcitity.this);
                builder.setMessage("确定删除该账号?");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        del();

                    }
                });
                builder.create().show();

            }
        });
        if (dataBean != null) {
            InitDate(dataBean);
        }
    }

    private void InitDate(AccountListEntity.DataBean dataBean) {
        right_view_text.setVisibility(View.VISIBLE);
        et_name.setText(dataBean.realName);
        et_accout.setText(dataBean.accountName);
        accountId = dataBean.id + "";

        if (dataBean.enable) {
            btn_next.setText("已失效");
        } else {
            btn_next.setText("未失效");
        }
        btn_next.setEnabled(false);
        ll_code.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
            case R.id.tv_code:
                sendPhoneMsg();
                break;
        }
    }

    //手动输入的手机号
    private void sendPhoneMsg() {
        //防止重复点击操作
        if (!tv_code.getText().toString().equals("发送验证码")) {
            return;
        }
        if (et_accout.getText().toString().length() == 0) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Tools.isMobileNum(et_accout.getText().toString())) {
            Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", et_accout.getText().toString());
        mQueue.add(ParamTools.packParam(Const.zzSendCode, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void submit() {
        String accout = et_accout.getText().toString().trim();
        if (TextUtils.isEmpty(accout)) {
            Toast.makeText(this, "请输入" + value + "账号", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        getBankBranchsByCityCode();

    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.zzSendCode)) {
            countTimer.start();// 开启定时器
        } else {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countTimer.onFinish();
    }


}

