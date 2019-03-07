package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.MagessListAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.MassageBean;
import com.fmt.ming.paotui.bean.UserBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.SavePreferencesData;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoming on 2018/11/16.
 */

public class MeassActivity extends BaseActivity {
    private RecyclerView recycleView;
    private MagessListAdapter magessListAdapter;
    private SpringView springView;
    private int page = 1;
    private int pagesize = 20;
    private List<MassageBean> massageBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messge);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.darkgray));
        initTitle();
        title.setText("消息中心");
        initView();
        Message();


    }

    /* 请求消息列表 */
    public void Message() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("pagesize", pagesize + "");
        map.put("page", page + "");
        map.put("israpp", "1");
        mQueue.add(ParamTools.packParam(Const.message, this, this, map));
        loading();
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                if (url.contains(Const.message)) {
                    JSONObject jsonObject = json.getJSONObject("data");
                    massageBeans = JSON.parseArray(jsonObject.optString("data"), MassageBean.class);
                    if (massageBeans != null) {
                        magessListAdapter.updateAdapter(massageBeans);
                    }
                    springView.onFinishFreshAndLoad();
                }
            } else if (stauts == 403) {
                Tools.showToast(MeassActivity.this, "登录过期请重新登录");
                mSavePreferencesData.putStringData("token", "");
                mSavePreferencesData.putStringData("json", "");
                Tools.jump(MeassActivity.this, LoginActivity.class, true);
            } else {
                Tools.showToast(MeassActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(MeassActivity.this, "发生错误,请重试!");

        }
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new DefaultHeader(MeassActivity.this));
        springView.setFooter(new DefaultFooter(MeassActivity.this));
        magessListAdapter = new MagessListAdapter(MeassActivity.this, null,mSavePreferencesData.getStringData("token"));
        recycleView.setLayoutManager(new LinearLayoutManager(MeassActivity.this));
        recycleView.setAdapter(magessListAdapter);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Message();
            }

            @Override
            public void onLoadmore() {
                page++;
                Message();
            }
        });
    }

}
