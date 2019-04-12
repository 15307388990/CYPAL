package com.cypal.ming.cypal.activity;

import android.os.Bundle;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 帮助文档
 */
public class HelpActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initTitle();
        title.setText("帮助");
        //测试提交
    }

    /* 执行登录操作 */
    public void toLogin(String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("admin_account", name);
        String password = MD5Util.getMD5String(pwd);
        map.put("admin_pass", password);
        mQueue.add(ParamTools.packParam(Const.venderLogin, this, this, map));
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
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }


}
