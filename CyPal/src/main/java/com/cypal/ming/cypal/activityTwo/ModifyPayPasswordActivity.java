package com.cypal.ming.cypal.activityTwo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.base.BaseView;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.MyCountTimer;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * @author luoming
 * created at 2019/4/21 1:10 AM
 * 找回支付密码
 */
public class ModifyPayPasswordActivity extends BaseActivity implements BaseView {


    private LinearLayout ll_view_back;
    private TextView et_new;
    private TextView et_new2;
    private Button btn_next;
    private TextView et_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pay_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_background));
        ButterKnife.bind(this);
        initView();
        initOnclik();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_new = (TextView) findViewById(R.id.et_new);
        et_new2 = (TextView) findViewById(R.id.et_new2);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_pay = (TextView) findViewById(R.id.et_pay);
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
        et_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inoutPsw("输入原支付密码");
            }
        });
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
    }


    /* 修改支付密码 */
    public void modifyPayPassword() {
        Map<String, String> map = new HashMap<>();
        String pwd = MD5Util.getMD5String(et_new.getText().toString().trim());
        String pwd2 = MD5Util.getMD5String(et_new2.getText().toString().trim());
        String old = MD5Util.getMD5String(et_pay.getText().toString().trim());
        map.put("oldPayPassword", old);
        map.put("newPayPassword", pwd);
        map.put("newTwoPayPassword", pwd2);
        mQueue.add(ParamTools.packParam(Const.modifyPayPassword, ModifyPayPasswordActivity.this, this, this, map));
        loading();
    }


    @Override
    public void returnData(String data, String url) {
        if (url.contains(Const.modifyPayPassword)) {
            Tools.showToast(ModifyPayPasswordActivity.this, "修改成功");
            finish();

        }

    }

    private void submit() {

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
        modifyPayPassword();


    }

    //打开输入密码的对话框
    public void inoutPsw(final String title) {
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if ("设置新密码".equals(title)) {
                        et_new.setText(psw);
                    } else if ("输入原支付密码".equals(title)) {
                        et_pay.setText(psw);
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
