package com.cypal.ming.cypal.base;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.bean.StoreBean;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.widgets.dialogs.LoadingDialog;

/**
 * @author 罗富贵 Activity 基类
 */
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseFragment extends Fragment implements Listener<String>, ErrorListener {
    public SavePreferencesData mSavePreferencesData;
    private LoadingDialog mloading;
    public RequestQueue mQueue; // 请求列队
    public View rl_title_bar;
    public View ll_view_back; // 返回
    public ImageView rightView; // 更多
    public TextView title; // 标题
    public Activity mcontext;
    public StoreBean storeBean;

    public BaseFragment(Activity context) {
        this.mcontext = context;
        mSavePreferencesData = new SavePreferencesData(context);
        mQueue = Volley.newRequestQueue(context);
        initAdmin();
    }

    public BaseFragment() {
    }

    public void initAdmin() {
        storeBean = new StoreBean();
        String mallSet = mSavePreferencesData.getStringData("json");
        if (mallSet != null) {
            try {
                storeBean = JSON.parseObject(mallSet, StoreBean.class);
            } catch (Exception e) {
                mSavePreferencesData.putStringData("json", "");
            }
        }
    }


    /**
     * 弹出等待框
     */
    public void loading() {
        if (mloading == null) {
            mloading = new LoadingDialog(mcontext, "请稍候...");
            mloading.setCanceledOnTouchOutside(false);
        }
        if (mloading.isShowing()) {
            mloading.dismiss();
        }
        // mloading.show();// 由于客户不喜欢弹框样式,顾先隐藏
    }

    /**
     * 隐藏等待框
     */
    public void dismissLoading() {
        if (mloading == null || !mloading.isShowing()) {
            return;
        }
        mloading.dismiss();
    }

    /**
     * 初始化标题栏
     */
    public void initTitle(View view) {
        rl_title_bar = view.findViewById(R.id.rl_title_bar);
        ll_view_back = view.findViewById(R.id.ll_view_back);
        ll_view_back.setVisibility(View.GONE);
        title = (TextView) view.findViewById(R.id.top_view_text);
        rightView = (ImageView) view.findViewById(R.id.right_view_text);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        dismissLoading();
        Tools.showToast(mcontext, "网络连接异常");
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
                returnData(response, url);
            } else if (stauts == -200) {
                mSavePreferencesData.putStringData("token", "");
                Tools.jump(mcontext, LoginActivity.class, true);
            } else {
                Tools.showToast(mcontext, msg);
                returnMsg(response, url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(mcontext, "数据格式不对");
        }
    }

    public void returnData(String data, String url) {
    }

    protected void returnMsg(String data, String url) {
    }

}
