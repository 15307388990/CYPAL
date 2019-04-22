package com.cypal.ming.cypal.activityTwo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.TabActivity;
import com.cypal.ming.cypal.activity.WebviewActivity;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.base.BaseView;
import com.cypal.ming.cypal.bean.LoginEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.MyCountTimer;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * @author luoming
 * created at 2019/4/21 1:10 AM
 * 找回支付密码
 */
public class BackPayPasswordActivity extends BaseActivity implements BaseView {


    //  private AuthCodeDialog authCodeDialog;
    private CountDownTimer countTimer;
    private LinearLayout ll_view_back;
    private EditText et_code;
    private TextView tv_code;
    private TextView et_new;
    private TextView et_new2;
    private Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_pay_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_background));
        ButterKnife.bind(this);
        initView();
        initOnclik();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_code = (TextView) findViewById(R.id.tv_code);
        et_new = (TextView) findViewById(R.id.et_new);
        et_new2 = (TextView) findViewById(R.id.et_new2);
        btn_next = (Button) findViewById(R.id.btn_next);
        countTimer = new MyCountTimer(this, tv_code, "发送验证码", R.color.darkgray, R.color.CY_9B9B9B);
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_code = (TextView) findViewById(R.id.tv_code);
        et_new = (TextView) findViewById(R.id.et_new);
        et_new2 = (TextView) findViewById(R.id.et_new2);
        btn_next = (Button) findViewById(R.id.btn_next);

    }


    private void initOnclik() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhoneMsg();
            }
        });
        et_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inoutPsw("设置新密码");
            }
        });
        et_new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inoutPsw("确认密码");
            }
        });

    }

    //手动输入的手机号
    private void sendPhoneMsg() {
        //防止重复点击操作
        if (!tv_code.getText().toString().equals("发送验证码")) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.findPayPasswordcode, this, this, map, Request.Method.GET, this));
        loading();
    }

    /* 找回支付密码 */
    public void findPayPassword() {
        Map<String, String> map = new HashMap<>();
        String pwd = MD5Util.getMD5String(et_new.getText().toString().trim());
        String pwd2 = MD5Util.getMD5String(et_new2.getText().toString().trim());
        map.put("verifyCode", et_code.getText().toString().trim());
        map.put("newPayPassword", pwd);
        map.put("newTwoPayPassword", pwd2);
        mQueue.add(ParamTools.packParam(Const.findPayPassword, BackPayPasswordActivity.this, this, this, map));
        loading();
    }


    @Override
    public void returnData(String data, String url) {
        if (url.contains(Const.findPayPasswordcode)) {

//            if (authCodeDialog != null && authCodeDialog.isShowing()) {
//                authCodeDialog.dismiss();
//            }
            countTimer.start();// 开启定时器
            tv_code.setVisibility(View.VISIBLE);
        } else if (url.contains(Const.findPayPassword)) {
            Tools.showToast(BackPayPasswordActivity.this, "修改成功");
            finish();

        }

    }

    private void submit() {
        // validate
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newtext = et_new.getText().toString().trim();
        if (TextUtils.isEmpty(newtext)) {
            Toast.makeText(this, "设置新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String new2 = et_new2.getText().toString().trim();
        if (TextUtils.isEmpty(new2)) {
            Toast.makeText(this, "确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        findPayPassword();


    }

    //打开输入密码的对话框
    public void inoutPsw(final String title) {
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if ("设置新密码".equals(title)) {
                        et_new.setText(psw);
                    } else {
                        et_new2.setText(psw);
                    }
                }

            }
        });
        menuWindow.setTitle(title);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }


}
