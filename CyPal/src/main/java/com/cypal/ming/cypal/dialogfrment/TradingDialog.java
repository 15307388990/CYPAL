package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.CertificationActivity;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.activity.TopUpDetailsActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.TradingDialogBinding;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 交易
 */

public class TradingDialog extends CenterDialog implements Response.Listener<String>, Response.ErrorListener {

    private static final String MINLIMIT = "minLimit";
    private static final String RWPID = "rwpId";
    private TradingDialogBinding binding;
    private String rwpId;
    private int minLimit = 0;
    private Context mContext;
    public RequestQueue mQueue; // 请求列队

    public TradingDialog() {
    }

    public static TradingDialog newInstance(String rwpId, int minLimit) {
        TradingDialog dialog = new TradingDialog();
        Bundle bundle = new Bundle();
        bundle.putString( RWPID, rwpId );
        bundle.putInt( MINLIMIT, minLimit );
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
        return R.layout.trading_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (TradingDialogBinding) dataBinding;

        Bundle bundle = getArguments();
        if (bundle != null) {
            rwpId = bundle.getString( RWPID );
            minLimit = bundle.getInt( MINLIMIT, 0 );
        }
        binding.tvCancle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
        binding.tvOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = binding.tvEdit.getText().toString().trim();
                if (Integer.valueOf( amount ) > minLimit) {
                    reCharge( amount );
                } else {
                    Tools.showToast( mContext, "充值金额不能小于最低充值金额" );
                }
            }
        } );
    }

    @Override
    public void show(Object object) {
        mContext = (Context) object;
        mQueue = Volley.newRequestQueue( mContext );
        super.show( object );
    }

    /* 充值 */
    public void reCharge(String amount) {
        Map<String, String> map = new HashMap<>();
        map.put( "rwpId", rwpId );
        map.put( "amount", amount );
        mQueue.add( ParamTools.packParam( Const.recharge, mContext, this, this, map ) );
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
                String orderId = json.getJSONObject( "data" ).getString( "orderId" );
                Intent intent = new Intent( mContext, TopUpDetailsActivity.class );
                intent.putExtra( "orderId", orderId );
                startActivity( intent );

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
