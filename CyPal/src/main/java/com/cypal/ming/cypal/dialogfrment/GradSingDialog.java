package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.GradSingDialogBinding;
import com.cypal.ming.cypal.databinding.TradingDialogBinding;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 手动抢单
 */

public class GradSingDialog extends CenterDialog implements Response.Listener<String>, Response.ErrorListener {

    private static final String AMOUNT = "amount";
    private GradSingDialogBinding binding;
    private String amount;
    private Context mContext;
    public RequestQueue mQueue; // 请求列队

    public GradSingDialog() {
    }

    public static GradSingDialog newInstance(String amount) {
        GradSingDialog dialog = new GradSingDialog();
        Bundle bundle = new Bundle();
        bundle.putString( AMOUNT, amount );
        dialog.setArguments( bundle );
        return dialog;
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
        super.onDismiss( dialog );
    }

    @Override
    public int getLayoutId() {
        return R.layout.grad_sing_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (GradSingDialogBinding) dataBinding;

        Bundle bundle = getArguments();
        if (bundle != null) {
            amount = bundle.getString( AMOUNT );
        }
        binding.tvAmount.setText( "￥" + amount );
        binding.tvBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }

    @Override
    public void show(Object object) {
        mContext = (Context) object;
        mQueue = Volley.newRequestQueue( mContext );
        super.show( object );
    }


    @Override
    public void onResponse(String response, String url) {
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "code" );
            String msg = json.optString( "msg" );
            String data = json.optString( "data" );
            if (stauts == 1) {
                dismiss();
            } else if (stauts == -2) {
                dismiss();
                //跳转至认证会员
                Tools.jump( (Activity) mContext, MemberActivity.class, false );
            } else {
                dismiss();
                Tools.showToast( mContext, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dismiss();
            Tools.showToast( mContext, "数据格式不对" );
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
