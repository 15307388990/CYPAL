package com.cypal.ming.cypal.activity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.ManagerAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.ContentEntity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelNoCloseDialog;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.utils.EncryptUtil;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.MessageEnum;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手动抢单
 */
public class GrabSingleActivity extends BaseActivity implements ManagerAdapter.OnClickListener {


    private RelativeLayout.LayoutParams params;
    private int cursorWidth;
    private int currentSelectTab;
    private LinearLayout ll_view_back;
    private RadioButton rd_phone;
    private RadioButton rd_email;
    private RadioButton rd_yun;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private TextView top_view_text;
    private RecyclerView recycleView;
    private List<ContentEntity> list;
    private ManagerAdapter managerAdapter;
    private String orderId;
    private String amount;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GrabSingleActivity.this.isFinishing()) {
                if (msg.what == 1) {
                    String text = (String) msg.obj;
                    if (!TextUtils.isEmpty(text)) {
                        try {
                            ManagerEntity managerEntity = JSON.parseObject(text, ManagerEntity.class);
                            if (managerEntity.messageEnum.equals(MessageEnum.OTCHAND.toString())) {
                                String content = EncryptUtil.decryptAES(managerEntity.content, "bc7cff29995970aaee5ca6de775d69f1c8468303d0e9820dddf8ae13eec2f5efd3e752f0b7ed313afb383f7fca003fa2");
                                ContentEntity contentEntity = JSON.parseObject(content, ContentEntity.class);
                                list.add(contentEntity);
                                initYou(contentEntity);
                                UpdateAdapter();
                                Play();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Tools.showToast(GrabSingleActivity.this, "出现异常，请重新登录");
                        }
                    }
                } else if (msg.what == 2) {
                    Tools.isOnline=false;
                    setText();

                } else if (msg.what == 3) {
                    Tools.isOnline=true;
                    setText();
                }

            }
        }
    };
    private TextView tv_you1;
    private TextView tv_you2;
    private TextView tv_you3;
    String paytype = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_single);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_background));
        initView();
        initEvent();
    }
    /**
     * 设置首页文字
     */
    private void setText() {
        if (Tools.isOnline) {
            top_view_text.setText("手动抢单");
        } else {
            top_view_text.setText("手动抢单（离线）");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 抢单
     */
    public void take() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        String timeStamp= System.currentTimeMillis()+"";
        map.put("timeStamp", timeStamp);
        StringBuilder sign = new StringBuilder();
        sign.append(mSavePreferencesData.getStringData("token"));
        sign.append(timeStamp);
        sign.append(orderId);
        map.put("sign",MD5Util.getMD5String(sign.toString()) );
        mQueue.add(ParamTools.packParam(Const.take, this, this, this, map));
        loading();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        rd_phone = (RadioButton) findViewById(R.id.rd_phone);
        rd_email = (RadioButton) findViewById(R.id.rd_email);
        rd_yun = (RadioButton) findViewById(R.id.rd_yun);
        rd_group = (RadioGroup) findViewById(R.id.rd_group);
        cursor = (LinearLayout) findViewById(R.id.cursor);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<ContentEntity>();
        managerAdapter = new ManagerAdapter(this, list, this);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setAdapter(managerAdapter);

        top_view_text = (TextView) findViewById(R.id.top_view_text);
        tv_you1 = (TextView) findViewById(R.id.tv_you1);
        tv_you2 = (TextView) findViewById(R.id.tv_you2);
        tv_you3 = (TextView) findViewById(R.id.tv_you3);
    }

    public void initEvent() {
        params = (RelativeLayout.LayoutParams) cursor.getLayoutParams();
        cursorWidth = params.width = Tools.getScreenWidth(GrabSingleActivity.this) / 3;
        cursor.setLayoutParams(params);
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_phone) {
                    currentSelectTab = 0;
                    params.leftMargin = 0;
                    tv_you1.setVisibility(View.INVISIBLE);
                    cursor.setLayoutParams(params);

                } else if (checkedId == R.id.rd_email) {
                    currentSelectTab = 1;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams(params);
                    tv_you2.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.rd_yun) {
                    currentSelectTab = 2;
                    params.leftMargin = (int) cursorWidth + cursorWidth;
                    cursor.setLayoutParams(params);
                    tv_you3.setVisibility(View.INVISIBLE);
                }
                UpdateAdapter();
            }
        });
    }


    @Override
    public void returnData(String data, String url) {
        if (url.contains(Const.take)) {
            //抢单成功
            // GradSingDialog.newInstance(amount).show(GrabSingleActivity.this);
            CancelNoCloseDialog.newInstance().setTitle("恭喜您抢单成功").
                    setIsQuBtn(false).
                    setContext("¥" + amount).setOktext("继续").show(GrabSingleActivity.this);
            //显示已抢光
            for (int i = 0; i < list.size(); i++) {
                if (orderId.equals(list.get(i).orderId + "")) {
                    list.get(i).isQian = true;
                    list.get(i).Text = "已抢光";
                }

            }
            UpdateAdapter();

        }

    }

    @Override
    protected void returnMsg(String data, String url) {
        if (url.contains(Const.take)) {
            for (int i = 0; i < list.size(); i++) {
                if (orderId.equals(list.get(i).orderId + "")) {
                    list.get(i).isQian = true;
                    list.get(i).Text = "订单飞走了";
                }

            }
        }
        UpdateAdapter();
    }

    @Override
    public void onTextMessage(final String text) {
        super.onTextMessage(text);
        new Thread() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = text;
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();

    }

    /**
     * 断开连接
     */
    @Override
    public void onDisconnected() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(2);
            }
        }.start();

    }

    /**
     * 连接成功
     */
    @Override
    public void onConnected() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 控制有单显示
     */
    private void initYou(ContentEntity contentEntity) {
        //推送过来订单不是当前类型
        if (!TextUtils.equals(contentEntity.payType, paytype)) {
            if (contentEntity.payType.equals("WXPAY")) {
                tv_you1.setVisibility(View.VISIBLE);
            } else if (contentEntity.payType.contains("ALIPAY")) {
                tv_you2.setVisibility(View.VISIBLE);
            } else {
                tv_you3.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * //过滤刷新列表
     */
    private void UpdateAdapter() {
        switch (currentSelectTab) {
            case 0:
                paytype = "WXPAY";
                break;
            case 1:
                paytype = "ALIPAY";
                break;
            case 2:
                paytype = "CLOUDPAY";
                break;
        }

        managerAdapter.updateAdapter(getList(paytype));
    }

    private List<ContentEntity> getList(String payType) {
        List<ContentEntity> contentEntities = new ArrayList<>();
        for (int i = list.size()-1; i >= 0; i--) {
            if (list.get(i).payType.contains(payType)) {
                contentEntities.add(list.get(i));
            }

        }
        return contentEntities;
    }


    @Override
    public void Qiang(String orderId, String amount) {
        this.orderId = orderId;
        this.amount = amount;
        take();
    }

    private void Play() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
        rt.play();

    }
}
