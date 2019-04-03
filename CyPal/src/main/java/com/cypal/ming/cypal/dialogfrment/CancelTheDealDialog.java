package com.cypal.ming.cypal.dialogfrment;

import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.databinding.CancelTheDealDialogBinding;
import com.cypal.ming.cypal.databinding.SignInDialogBinding;
import com.cypal.ming.cypal.utils.Tools;

import org.w3c.dom.Text;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 取消交易提示框
 */
public class CancelTheDealDialog extends CenterDialog {

    private static final String URL = "url";
    private CancelTheDealDialogBinding binding;
    private OnClickListener onClickListener;
    private String title, context, qtext, oktext;


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

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public CancelTheDealDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置内容
     *
     * @param context
     * @return
     */
    public CancelTheDealDialog setContext(String context) {
        this.context = context;
        return this;
    }

    /**
     * 设置取消按钮文本
     *
     * @param qtext
     * @return
     */
    public CancelTheDealDialog setQtext(String qtext) {
        this.qtext = qtext;
        return this;
    }

    /**
     * 设置确定按钮文本
     *
     * @param oktext
     * @return
     */
    public CancelTheDealDialog setOktext(String oktext) {
        this.oktext = oktext;
        return this;
    }


    public static CancelTheDealDialog newInstance() {
        CancelTheDealDialog dialog = new CancelTheDealDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
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
        super.onDismiss(dialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.cancel_the_deal_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth(getActivity()) * 0.75);
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (CancelTheDealDialogBinding) dataBinding;
        binding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.successful();
            }
        });
        binding.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (!TextUtils.isEmpty(title))
            binding.tvTitle.setText(title);
        if (!TextUtils.isEmpty(context))
            binding.tvContext.setText(context);
        if (!TextUtils.isEmpty(qtext))
            binding.tvCancle.setText(qtext);
        if (!TextUtils.isEmpty(oktext))
            binding.tvOk.setText(oktext);
    }


}
