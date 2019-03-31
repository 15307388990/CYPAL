package com.cypal.ming.cypal.dialogfrment;

import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.databinding.RewardDialogBinding;
import com.cypal.ming.cypal.databinding.SignInDialogBinding;
import com.cypal.ming.cypal.utils.Tools;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 奖励规则界面
 */
public class RewardDialog extends CenterDialog {

    private static final String URL = "url";
    private RewardDialogBinding binding;

    public RewardDialog() {
    }

    public static RewardDialog newInstance(String url) {
        RewardDialog dialog = new RewardDialog();
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
        return R.layout.reward_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (RewardDialogBinding) dataBinding;
        Bundle bundle = getArguments();
        if (bundle != null) {
            String buildUrl = bundle.getString( URL );
        }
        binding.ivColse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }


}
