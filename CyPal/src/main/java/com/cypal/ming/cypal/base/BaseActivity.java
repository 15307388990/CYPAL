package com.cypal.ming.cypal.base;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.GrabSingleActivity;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.activity.TabActivity;
import com.cypal.ming.cypal.bean.ContentEntity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.bean.PartnerBean;
import com.cypal.ming.cypal.bean.StoreBean;
import com.cypal.ming.cypal.utils.MessageEnum;
import com.cypal.ming.cypal.utils.NotifyUtil;
import com.cypal.ming.cypal.widgets.dialogs.LoadingDialog;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.ws.WsManager;
import com.githang.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * @author 罗富贵 Activity 基类
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements Listener<String>, ErrorListener, WsManager.IWsManagerActivityView {
    public View rl_title_bar;
    public View ll_view_back; // 返回
    public TextView title; // 标题
    public ImageView rightView; // 更多
    private LoadingDialog loading;
    public RequestQueue mQueue; // 请求列队
    public SavePreferencesData mSavePreferencesData;
    public StoreBean storeBean;
    protected DecimalFormat mDf;
    protected PartnerBean partnerBean;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String text = (String) msg.obj;
                if (!TextUtils.isEmpty(text)) {
                    try {
                        ManagerEntity managerEntity = JSON.parseObject(text, ManagerEntity.class);
                        Log.d("WsManager", "接收开始判断类型");
                        if (managerEntity.messageEnum.equals(MessageEnum.LOGINOUT.toString())) {
                            Log.d("WsManager", "强制离线");
                            goLogin(managerEntity);
                        } else if (!managerEntity.messageEnum.equals(MessageEnum.OTCHAND.toString()) && !managerEntity.messageEnum.equals(MessageEnum.IM.toString())) {
                            Log.d("WsManager", "通知");
                            goNotice(managerEntity);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Tools.showToast(BaseActivity.this, "出现异常，请重新登录");
                    }
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_background));
        mQueue = Volley.newRequestQueue(this);
        mDf = new DecimalFormat("0.00");

        /*
         * 设置设备常亮
         */
        // pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        // mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        mSavePreferencesData = new SavePreferencesData(this);
        WsManager.getInstance().setmListener(this);
        initAdmin();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initAdmin();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化标题栏
     */
    public void initTitle() {
        rl_title_bar = findViewById(R.id.rl_title_bar);

        ll_view_back = findViewById(R.id.ll_view_back);
        title = (TextView) findViewById(R.id.top_view_text);
        rightView = (ImageView) findViewById(R.id.right_view_text);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.stop();
                finish();
            }
        });
    }

    public void initAdmin() {
        storeBean = new StoreBean();
        String mallSet = mSavePreferencesData.getStringData("json");
        if (mallSet != null) {
            synchronized (BaseActivity.this) {
                try {
                    storeBean = JSON.parseObject(mallSet, StoreBean.class);
                } catch (Exception e) {
                    mSavePreferencesData.putStringData("json", "");
                }
            }
        }
    }

    ;

    /**
     * // * 设置状态栏背景状态 //
     */
    // private void setTranslucentStatus() {
    // if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
    // Window window = getWindow();
    // //
    // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // window.setStatusBarColor(getResources().getColor(R.color.blue));
    // // window.setNavigationBarColor(Color.TRANSPARENT);
    // }
    // }

    /**
     * 弹出等待框
     */
    public void loading() {
        if (loading == null) {
            loading = new LoadingDialog(this, "请稍候...");
            loading.setCanceledOnTouchOutside(false);
        }
        if (loading.isShowing()) {
            loading.dismiss();
        }
        // loading.show();// 由于客户不喜欢弹框样式,顾先隐藏
    }

    /**
     * 弹出等待框
     */
    public void loginloading() {
        if (loading == null) {
            loading = new LoadingDialog(this, "请稍候...");
            loading.setCanceledOnTouchOutside(false);
        }
        if (loading.isShowing()) {
            loading.dismiss();
        }
        loading.show();// 由于客户不喜欢弹框样式,顾先隐藏
    }

    /**
     * 隐藏等待框
     */
    public void dismissLoading() {
        if (loading == null || !loading.isShowing()) {
            return;
        }
        loading.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 被挤下线 */
        if (requestCode == 10000) {
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        dismissLoading();
        Tools.showToast(getApplicationContext(), "网络连接异常");
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("code");
            String msg = json.optString("msg");
            String data = json.optString("data");
            if (stauts == 1) {
                this.returnData(response, url);
            } else if (stauts == -200) {
                mSavePreferencesData.putStringData("token", "");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("msg", msg);
                startActivity(intent);
                finish();
                //Tools.jump( this, LoginActivity.class, true );
            } else {
                Tools.showToast(this, msg);
                this.returnMsg(response, url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "数据格式不对");
        }
    }

    protected void returnData(String data, String url) {
    }

    protected void returnMsg(String data, String url) {
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onTextMessage(final String text) {
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
     * 跳转至登录
     */
    private void goLogin(ManagerEntity managerEntity) {
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.putExtra("msg", managerEntity.content);
        startActivity(intent);
        finish();
    }

    /**
     * 弹通知
     */
    private void goNotice(ManagerEntity managerEntity) {
        Intent intent = new Intent(this, TabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.ic_launcher;
        String ticker = "您有一条新通知";
        String title = managerEntity.title;
        String content = managerEntity.content;
        NotifyUtil notify1 = new NotifyUtil(this, (int) System.currentTimeMillis());
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
    }
}
