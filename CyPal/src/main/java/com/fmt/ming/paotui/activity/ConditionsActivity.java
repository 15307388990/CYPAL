package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.VenderListAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.DeviceListBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.TimerDateUtil;
import com.fmt.ming.paotui.utils.Tools;
import com.fmt.ming.paotui.view.CustomDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 条件筛选
 */
public class ConditionsActivity extends BaseActivity implements VenderListAdapter.ItemClickListener {


    private TextView right_text;
    private RadioButton rb_seven_days;
    private RadioButton rb_seven_month;
    private RadioButton rb_up_month;
    private TextView tv_star_timer;
    private TextView tv_end_timer;
    private RecyclerView recycleView;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private List<DeviceListBean> deviceListBeans;
    private VenderListAdapter venderListAdapter;
    private int boxid = -1;
    private String starTimer, endTimer;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    String now = sdf.format(new Date());
    TimerDateUtil dateUtil = new TimerDateUtil();
    int type = 1;// 7天  30天  上月

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);
        initView();
        initTitle();
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!rb_seven_days.isChecked() && !rb_seven_month.isChecked() && !rb_up_month.isChecked()) {
                    if (tv_star_timer.getText().toString().equals("") || tv_end_timer.getText().toString().equals("")) {
                        Tools.showToast(getApplication(), "请选择范围");
                        return;
                    }
                }
                if (compare_date(starTimer, endTimer)) {
                    Intent intent = new Intent();
                    intent.putExtra("starTimer", starTimer);//开始日期
                    intent.putExtra("endTimer", endTimer);//结束时间
                    intent.putExtra("boxId", boxid + "");//选中的设备ID
                    intent.putExtra("type", type);//选中的设备ID
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        rb_seven_days.setChecked(true);
        initDatePicker();
        initOnclik();
        title.setText("条件筛选");

    }

    /* 获取列表 */
    public void venderList() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
       // mQueue.add(ParamTools.packParam(Const.venderList, this, this, map));
        loading();
    }

    public void initOnclik() {

        tv_star_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 4;
                if (tv_star_timer.getText().toString().equals("")) {
                    customDatePicker1.show(now);
                } else {
                    customDatePicker1.show(tv_star_timer.getText().toString());
                }
            }
        });
        tv_end_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_end_timer.getText().toString().equals("")) {
                    customDatePicker2.show(now);
                } else {
                    customDatePicker2.show(tv_end_timer.getText().toString());
                }
            }
        });
        rb_seven_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_star_timer.setText("");
                tv_end_timer.setText("");
                type = 1;
                endTimer = dateUtil.getNowDate();
                starTimer = dateUtil.getDate(6);

            }
        });
        rb_seven_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_star_timer.setText("");
                tv_end_timer.setText("");
                type = 2;
                endTimer = dateUtil.getNowDate();
                starTimer = dateUtil.getDate(29);
            }
        });
        rb_up_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_star_timer.setText("");
                tv_end_timer.setText("");
                type = 3;
                endTimer = dateUtil.getDate(29);
                starTimer = dateUtil.getDate(59);

            }
        });
    }

    private void initDatePicker() {

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                starTimer = time;
                tv_star_timer.setText(time);
                rb_seven_days.setChecked(false);
                rb_seven_month.setChecked(false);
                rb_up_month.setChecked(false);
            }
        }, "2018-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                endTimer = time;
                tv_end_timer.setText(time);
            }
        }, "2018-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
//                if (url.contains(Const.venderList)) {
//                    deviceListBeans = JSON.parseArray(json.optString("result"), DeviceListBean.class);
//                    venderListAdapter.updateAdapter(deviceListBeans, -1);
//
//                }
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }


    private void initView() {
        right_text = (TextView) findViewById(R.id.right_text);
        rb_seven_days = (RadioButton) findViewById(R.id.rb_seven_days);
        rb_seven_month = (RadioButton) findViewById(R.id.rb_seven_month);
        rb_up_month = (RadioButton) findViewById(R.id.rb_up_month);
        tv_star_timer = (TextView) findViewById(R.id.tv_star_timer);
        tv_end_timer = (TextView) findViewById(R.id.tv_end_timer);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        deviceListBeans = new ArrayList<DeviceListBean>();
        venderListAdapter = new VenderListAdapter(ConditionsActivity.this, deviceListBeans, this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setAdapter(venderListAdapter);
        endTimer = dateUtil.getNowDate();
        starTimer = dateUtil.getDate(6);
        venderList();
    }

    @Override
    public void onItemClick(View view, int position) {
        venderListAdapter.updateAdapter(deviceListBeans, position);

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public boolean compare_date(String starTimer, String endTimer) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(starTimer);
            Date dt2 = df.parse(endTimer);
            if ((dt2.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000) >= 6) {
                return true;
            } else {
                Tools.showToast(this, "结束时间必须大于开始时间7天以上");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
