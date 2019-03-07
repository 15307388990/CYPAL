package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.dialog.AuthCodeDialog;
import com.fmt.ming.paotui.utils.MyCountTimer;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 找回密码
 */
public class RetrievePasswordActivity extends BaseActivity {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ll_view_back)
    LinearLayout llViewBack;
    @Bind(R.id.right_view_text)
    ImageView rightViewText;
    @Bind(R.id.top_view_text)
    TextView topViewText;
    @Bind(R.id.et_iphone)
    EditText etIphone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.et_new)
    EditText etNew;
    @Bind(R.id.et_new2)
    EditText etNew2;
    @Bind(R.id.btn_next)
    Button btnNext;
    private AuthCodeDialog authCodeDialog;
    private CountDownTimer countTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrueve_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.separator_color));
        ButterKnife.bind(this);
        initView();
        initOnclik();
    }

    private void initView() {
        countTimer = new MyCountTimer(this, tvCode, "获取验证码", R.color.tab_text_color_select, R.color.darkgray);
    }

    private void initOnclik() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repassword();
            }
        });
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendPhoneMsg();
                if (authCodeDialog != null && authCodeDialog.isShowing()) {
                    authCodeDialog.dismiss();
                }
                countTimer.start();// 开启定时器
                tvCode.setVisibility(View.VISIBLE);
            }
        });
    }

    //手动输入的手机号
    private void sendPhoneMsg() {
        //防止重复点击操作
        if (!tvCode.getText().toString().equals("获取验证码")) {
            return;
        }
        if (etIphone.getText().toString().length() == 0) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Tools.isMobileNum(etIphone.getText().toString())) {
            Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("phone", etIphone.getText().toString());
        mQueue.add(ParamTools.packParam(Const.sendPhoneMsg, this, this, map));
        loading();
    }

    /* 修改密码 */
    public void Repassword() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        map.put("renewpassword", etNew.getText().toString());
        mQueue.add(ParamTools.packParam(Const.repassword, this, this, map));
        loading();
    }


    @Override
    public void onResponse(String response, String url) {
        // admin_agency: 341,
        // status: 0,
        // auth_token: "d8deb043-30a0-4a95-8d7d-2d653c56fe7f",
        // admin_id: 347,
        // agency_name: "罗富贵小吃店",
        // msg: "登录成功"
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                Tools.showToast(this, msg);
                finish();
            } else if (stauts == 403) {
                Tools.showToast(RetrievePasswordActivity.this, "登录过期请重新登录");
                Tools.jump(this, LoginActivity.class, true);
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }


}
