package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.MagessListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.MassageBean;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
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
    private List<MassageBean.DataBean.ContentBean> massageBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messge);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        initTitle();
        title.setText("消息中心");
        initView();
        Message();


    }

    /* 请求消息列表 */
    public void Message() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.messageCenter, this, this, map, Request.Method.GET, this));
    }

    @Override
    protected void returnData(String data, String url) {
        springView.onFinishFreshAndLoad();
        MassageBean massageBean = JSON.parseObject(data, MassageBean.class);
        magessListAdapter.updateAdapter(massageBean.data.content);
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new DefaultHeader(MeassActivity.this));
        springView.setFooter(new DefaultFooter(MeassActivity.this));
        magessListAdapter = new MagessListAdapter(MeassActivity.this, null, mSavePreferencesData.getStringData("token"));
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
