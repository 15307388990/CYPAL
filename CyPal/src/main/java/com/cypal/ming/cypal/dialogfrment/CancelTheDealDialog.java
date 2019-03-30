package com.cypal.ming.cypal.dialogfrment;

import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.databinding.CancelTheDealDialogBinding;
import com.cypal.ming.cypal.databinding.SignInDialogBinding;
import com.cypal.ming.cypal.utils.Tools;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 取消交易提示框
 */
public class CancelTheDealDialog extends CenterDialog {

    private static final String URL = "url";
    private CancelTheDealDialogBinding binding;
    private OnClickListener onClickListener;

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void successful();

    }

    public CancelTheDealDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;

    }


    public static CancelTheDealDialog newInstance(String url) {
        CancelTheDealDialog dialog = new CancelTheDealDialog();
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
        return R.layout.cancel_the_deal_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (CancelTheDealDialogBinding) dataBinding;
        Bundle bundle = getArguments();
        if (bundle != null) {
            String buildUrl = bundle.getString( URL );
        }
        binding.tvOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.successful();
            }
        } );
        binding.tvCancle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }


}
