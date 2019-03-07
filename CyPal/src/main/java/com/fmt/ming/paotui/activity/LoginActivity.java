package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.StoreBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.dialog.CustomDialogActivity;
import com.fmt.ming.paotui.service.VersionService;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.et_login_account)
    EditText etLoginAccount;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;

    @Bind(R.id.login)
    Button login;
    @Bind(R.id.tv_version)
    TextView tv_version;
    @Bind(R.id.tv_forgot)
    TextView tv_forgot;//忘记密码

    private String name, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.darkgray));
        Tools.webacts.add(this);
        ButterKnife.bind(this);
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(LoginActivity.this, RetrievePasswordActivity.class, false);
            }
        });
        Intent intent = new Intent();
        String auth_token = mSavePreferencesData.getStringData("token");
        if (auth_token != null && !auth_token.equals("")) {
            intent.setClass(LoginActivity.this, TabActivity.class);
            startActivity(intent);
        } else {
            String name = mSavePreferencesData.getStringData("name");
            String pwd = mSavePreferencesData.getStringData("pwd");
            if (name != null && !"".equals(name)) {
                etLoginAccount.setText(name);
                etLoginPassword.setText(pwd);
            }
        }

        // jiebianAppVersion();
//        etLoginPassword.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (etLoginPassword.getText().length() >= 1) {
//                    login.setBackgroundResource(R.drawable.login_btn_n);
//                    login.setTextColor(getResources().getColor(R.color.actionbar_title_color));
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private void loadVersion() {
        PackageManager pm = this.getPackageManager();// context为当前Activity上下
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tv_version.setText("当前版本：" + pi.versionName);
    }

    //检测版本
    private void jiebianAppVersion() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("israpp", "1");
        VersionParams.Builder builder = new VersionParams.Builder().setRequestMethod(HttpRequestMethod.POST).setRequestParams(httpParams)
                .setRequestUrl(Const.BASE_URL + Const.config)
                .setCustomDownloadActivityClass(CustomDialogActivity.class)
                .setService(VersionService.class);
        AllenChecker.startVersionCheck(this, builder.build());

    }

    @butterknife.OnClick(R.id.login)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (checkParams()) {
                    toLogin(name, pwd);
                }
                break;
        }
    }

    /* 执行登录操作 */
    public void toLogin(String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", name);
        map.put("password", pwd);
        map.put("israpp", "1");
        mQueue.add(ParamTools.packParam(Const.venderLogin, this, this, map));
        loading();
    }


    /**
     * 检测参数合法性
     */
    public boolean checkParams() {
        name = etLoginAccount.getText().toString();
        pwd = etLoginPassword.getText().toString();
        if (name == null || pwd == null || name.length() == 0 || pwd.length() == 0) {
            Tools.showToast(this, "请输入正确的用户名或密码!");
            return false;
        }
        return true;
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 200) {
                String token = json.optString("token");
                mSavePreferencesData.putStringData("token", token);
                mSavePreferencesData.putStringData("name", name);
                mSavePreferencesData.putStringData("pwd", pwd);

                String date = json.optString("data");
                mSavePreferencesData.putStringData("json", date);
                storeBean = JSON.parseObject(date, StoreBean.class);
                // 友盟统计
                MobclickAgent.onProfileSignIn(Tools.getDeviceBrand(), name);
                finish();
                Tools.jump(this, TabActivity.class, false);

            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }


}
