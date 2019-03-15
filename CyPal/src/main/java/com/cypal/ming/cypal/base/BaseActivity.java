package com.cypal.ming.cypal.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
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
import com.cypal.ming.cypal.bean.PartnerBean;
import com.cypal.ming.cypal.bean.StoreBean;
import com.cypal.ming.cypal.widgets.dialogs.LoadingDialog;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * @author 罗富贵 Activity 基类
 */
public abstract class BaseActivity<T> extends Activity implements Listener<String>, ErrorListener {
    public View rl_title_bar;
    public View ll_view_back; // 返回
    public TextView title; // 标题
    public ImageView rightView; // 更多
    private LoadingDialog loading;
    public RequestQueue mQueue; // 请求列队
    PowerManager pm;
    WakeLock mWakeLock;
    public SavePreferencesData mSavePreferencesData;
    public StoreBean storeBean;
    protected DecimalFormat mDf;
    protected PartnerBean partnerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.top_background ) );
        mQueue = Volley.newRequestQueue( this );
        mDf = new DecimalFormat( "0.00" );

        /*
         * 设置设备常亮
         */
        pm = (PowerManager) getSystemService( Context.POWER_SERVICE );
        mWakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag" );
        mSavePreferencesData = new SavePreferencesData( this );
        initAdmin();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
        MobclickAgent.onResume( this );
        initAdmin();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
        MobclickAgent.onPause( this );
    }

    /**
     * 初始化标题栏
     */
    public void initTitle() {
        rl_title_bar = findViewById( R.id.rl_title_bar );

        ll_view_back = findViewById( R.id.ll_view_back );
        title = (TextView) findViewById( R.id.top_view_text );
        rightView = (ImageView) findViewById( R.id.right_view_text );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.stop();
                finish();
            }
        } );
    }

    public void initAdmin() {
        storeBean = new StoreBean();
        String mallSet = mSavePreferencesData.getStringData( "json" );
        if (mallSet != null) {
            synchronized (BaseActivity.this) {
                try {
                    storeBean = JSON.parseObject( mallSet, StoreBean.class );
                } catch (Exception e) {
                    mSavePreferencesData.putStringData( "json", "" );
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
            loading = new LoadingDialog( this, "请稍候..." );
            loading.setCanceledOnTouchOutside( false );
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
        super.onActivityResult( requestCode, resultCode, data );
        /** 被挤下线 */
        if (requestCode == 10000) {
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dismissLoading();
        Tools.showToast( getApplicationContext(), "网络连接异常" );
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "code" );
            String msg = json.optString( "msg" );
            String data = json.optString( "data" );
            if (stauts == 1) {
                this.returnData( data, url );
            } else {
                Tools.showToast( this, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast( this, "数据格式不对" );
        }
    }

    protected void returnData(String data, String url) {
    }


}
