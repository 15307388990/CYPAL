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
import com.cypal.ming.cypal.databinding.MarginDialogBinding;
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
 * 交易
 */

public class MarginDialog extends CenterDialog {

    private static final String TEXT = "text";
    private MarginDialogBinding binding;

    public MarginDialog() {
    }

    public static MarginDialog newInstance(String text) {
        MarginDialog dialog = new MarginDialog();
        Bundle bundle = new Bundle();
        bundle.putString( TEXT, text );
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
        return R.layout.margin_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (MarginDialogBinding) dataBinding;

        Bundle bundle = getArguments();
        if (bundle != null) {
            String text = bundle.getString( TEXT );
            binding.tvText.setText( text );
        }
        binding.tvOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }

    @Override
    public void show(Object object) {
        super.show( object );
    }


}
