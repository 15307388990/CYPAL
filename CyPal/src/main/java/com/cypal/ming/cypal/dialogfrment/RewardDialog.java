package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.DescmisionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.RewardDialogBinding;
import com.cypal.ming.cypal.databinding.SignInDialogBinding;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 奖励规则界面
 */
public class RewardDialog extends CenterDialog implements Response.Listener<String>, Response.ErrorListener {

    private static final String URL = "url";
    private RewardDialogBinding binding;
    private Context mContext;
    public SavePreferencesData mSavePreferencesData;
    public RequestQueue mQueue; // 请求列队

    public RewardDialog() {
    }

    public static RewardDialog newInstance(String url) {
        RewardDialog dialog = new RewardDialog();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void show(Object object) {
        mContext = (Context) object;
        mSavePreferencesData = new SavePreferencesData(mContext);
        mQueue = Volley.newRequestQueue(mContext);
        description();
        super.show(object);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.reward_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth(getActivity()) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (RewardDialogBinding) dataBinding;
        Bundle bundle = getArguments();
        if (bundle != null) {
            String buildUrl = bundle.getString(URL);
        }
        binding.ivColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void description() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.description, this, this, map, Request.Method.GET, mContext));
    }


    @Override
    public void onResponse(String response, String url) {
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("code");
            String msg = json.optString("msg");
            String data = json.optString("data");
            if (stauts == 1) {
                if (url.contains(Const.description)) {
                    DescmisionEntity descmisionEntity = JSON.parseObject(response, DescmisionEntity.class);
                    if (!TextUtils.isEmpty(descmisionEntity.data.WXPAY)) {
                        binding.tvWx.setText("1:微信接单可获得交易金额" + descmisionEntity.data.WXPAY + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.ALIPAY)) {
                        binding.tvAli.setText("2:支付宝接单可获得交易金额" + descmisionEntity.data.ALIPAY + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.CLOUDPAY)) {
                        binding.tvClou.setText("3:云闪付接单可获得交易金额" + descmisionEntity.data.CLOUDPAY + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.SECONDS)) {
                        binding.tvPdd.setText("4:拼多多接单可获得交易金额" + descmisionEntity.data.PDD + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.CNP)) {
                        binding.tvCnp.setText("5:吹牛皮接单可获得交易金额" + descmisionEntity.data.CNP + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.WBHB)) {
                        binding.tvWbhb.setText("6:微博红包接单可获得交易金额" + descmisionEntity.data.CNP + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.SECONDS)) {
                        binding.tvSeconnod.setText("7:A级好友接单可获得交易金额" + descmisionEntity.data.SECONDS + "的佣金");
                    }
                    if (!TextUtils.isEmpty(descmisionEntity.data.FIRST)) {
                        binding.tvFirst.setText("8:B级好友接单可获得交易金额的" + descmisionEntity.data.FIRST + "的佣金");
                    }
                }

            } else if (stauts == -2) {
                dismiss();
                //跳转至认证会员
                Tools.jump((Activity) mContext, MemberActivity.class, false);
            } else {
                dismiss();
                Tools.showToast(mContext, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dismiss();
            Tools.showToast(mContext, "数据格式不对");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
