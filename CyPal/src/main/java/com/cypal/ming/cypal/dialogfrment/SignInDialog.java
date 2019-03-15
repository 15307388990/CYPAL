package com.cypal.ming.cypal.dialogfrment;

import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.databinding.SignInDialogBinding;
import com.cypal.ming.cypal.databinding.TradingDialogBinding;
import com.cypal.ming.cypal.utils.Tools;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 交易
 */
public class SignInDialog extends CenterDialog {

    private static final String URL = "url";
    private SignInDialogBinding binding;

    public SignInDialog() {
    }

    public static SignInDialog newInstance(String url) {
        SignInDialog dialog = new SignInDialog();
        Bundle bundle = new Bundle();
        bundle.putString( URL, url );
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
        return R.layout.sign_in_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (SignInDialogBinding) dataBinding;
        Bundle bundle = getArguments();
        if (bundle != null) {
            String buildUrl = bundle.getString( URL );
        }
        binding.tvOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }


}
