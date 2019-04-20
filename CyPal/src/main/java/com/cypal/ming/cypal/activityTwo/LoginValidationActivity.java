package com.cypal.ming.cypal.activityTwo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.RegisteredActivity;
import com.cypal.ming.cypal.activity.TabActivity;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.LoginEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoming
 * created at 2019/4/19 9:49 AM
 * 登陆验证
 */
public class LoginValidationActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_view_back;
    private TextView tv_account;
    private EditText et_code;
    private Button btn_next;


    private String accout;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_login_validation);
        initView();
    }

    private void initView() {
        accout = getIntent().getStringExtra("accout");
        pwd = getIntent().getStringExtra("pwd");
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        tv_account = (TextView) findViewById(R.id.tv_account);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_next = (Button) findViewById(R.id.btn_next);
        if (!TextUtils.isEmpty(accout)) {
            tv_account.setText("已向您的手机" + accout + "发送验证码");
        }
        btn_next.setOnClickListener(this);
        ll_view_back.setOnClickListener(this);
    }

    /* 验证吗登录安全验证 */
    public void loginVerifyCode() {
        Map<String, String> map = new HashMap<>();
        map.put("account", accout);
        pwd = MD5Util.getMD5String(pwd);
        map.put("password", pwd);
        map.put("inviteCode", et_code.getText().toString().trim());
        mQueue.add(ParamTools.packParam(Const.loginVerifyCode, LoginValidationActivity.this, this, this, map));
        loading();
    }

    @Override
    protected void returnData(String data, String url) {
        LoginEntity loginEntity = JSON.parseObject(data, LoginEntity.class);
        mSavePreferencesData.putStringData("token", loginEntity.data.loginToken);
        Tools.exit();//关闭登录页
        Tools.jump(this, TabActivity.class, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
            case R.id.ll_view_back:
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Tools.showToast(this, "请输入验证码");
            return;
        }
        // TODO validate success, do something

        loginVerifyCode();

    }
}
