package com.fmt.ming.paotui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoming 设置
 */
public class SetActivity extends BaseActivity implements OnClickListener {

    private LinearLayout ll_password;
    private LinearLayout ll_service;
    private LinearLayout ll_feedback;
    private LinearLayout ll_about;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.separator_color));
        ViewUtils.inject(this);
        initTitle();
        title.setText("设置");
        initView();


    }

    private void initView() {
        ll_password = (LinearLayout) findViewById(R.id.ll_password);
        ll_password.setOnClickListener(this);
        ll_service = (LinearLayout) findViewById(R.id.ll_service);
        ll_service.setOnClickListener(this);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        ll_feedback.setOnClickListener(this);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                mSavePreferencesData.putStringData("token", "");
                mSavePreferencesData.putStringData("json", "");
                Tools.jump(this, LoginActivity.class, true);
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            // TODO: handle exception
            Tools.showToast(getApplicationContext(), "解析数据错误");
        }
    }

    private void changejson(com.alibaba.fastjson.JSONObject jsonObject) {
        storeBean.setAvatar(jsonObject.getJSONObject("data").getString("avatar"));
        String json = JSON.toJSONString(storeBean);
        mSavePreferencesData.putStringData("json", json);
        initAdmin();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                deleteOrderDialog();
                break;
            case R.id.ll_password:
                Tools.jump(this, ChangePasswordActivity.class, false);
                break;
            case R.id.ll_service:
                break;
            case R.id.ll_feedback:
                break;
            case R.id.ll_about:
                break;
        }
    }

    private void deleteOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定要退出登录?");
        builder.setTitle("提示");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mlogout();

            }
        });
        builder.create().show();
    }

    public void mlogout() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        mQueue.add(ParamTools.packParam(Const.mlogout, this, this, map));
        loading();
    }
}
