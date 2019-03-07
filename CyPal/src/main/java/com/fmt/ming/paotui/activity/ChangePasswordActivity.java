package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.MD5Util;
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
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.ll_view_back)
    LinearLayout llViewBack;
    @Bind(R.id.right_view_text)
    ImageView rightViewText;
    @Bind(R.id.top_view_text)
    TextView topViewText;
    @Bind(R.id.et_old)
    EditText etOld;
    @Bind(R.id.cb_old)
    CheckBox cbOld;
    @Bind(R.id.et_old2)
    EditText etOld2;
    @Bind(R.id.cb_old2)
    CheckBox cbOld2;
    @Bind(R.id.et_new)
    EditText etNew;
    @Bind(R.id.cb_new)
    CheckBox cbNew;
    @Bind(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.separator_color));
        ButterKnife.bind(this);
        initOnclik();
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
        cbOld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    etOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        cbOld2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etOld2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    cbOld2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        cbNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    cbNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        llViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /* 修改密码 */
    public void Repassword() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        map.put("password", etOld.getText().toString());
        map.put("newpassword", etOld2.getText().toString());
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
                Tools.showToast(ChangePasswordActivity.this, "登录过期请重新登录");
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
