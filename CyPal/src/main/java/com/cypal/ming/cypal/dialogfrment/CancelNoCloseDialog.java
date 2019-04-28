package com.cypal.ming.cypal.dialogfrment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.databinding.CancelTheDealDialogBinding;
import com.cypal.ming.cypal.utils.Tools;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 通用提示框
 */
public class CancelNoCloseDialog extends CenterDialog {

    private static final String URL = "url";
    private CancelTheDealDialogBinding binding;
    private OnClickListener onClickListener;
    private String title, context, qtext, oktext;
    private boolean isQuBtn = true;
    private int contextColor = R.color.CY_888888;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setDimAmount(0.65f);
        }
        ViewDataBinding binding = getLayoutBind();
        View view = binding.getRoot();
        dialog.setContentView(view);
        initView(binding);
        initWindowParams(dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }
    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void successful();

    }

    public CancelNoCloseDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;

    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public CancelNoCloseDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置内容字体颜色
     *
     * @param contextColor
     * @return
     */
    public CancelNoCloseDialog setContextColor(int contextColor) {
        this.contextColor = contextColor;
        return this;
    }

    /**
     * 是否显示取消按钮
     *
     * @return
     */
    public CancelNoCloseDialog setIsQuBtn(boolean isQuBtn) {
        this.isQuBtn = isQuBtn;
        return this;
    }

    /**
     * 设置内容
     *
     * @param context
     * @return
     */
    public CancelNoCloseDialog setContext(String context) {
        this.context = context;
        return this;
    }

    /**
     * 设置取消按钮文本
     *
     * @param qtext
     * @return
     */
    public CancelNoCloseDialog setQtext(String qtext) {
        this.qtext = qtext;
        return this;
    }

    /**
     * 设置确定按钮文本
     *
     * @param oktext
     * @return
     */
    public CancelNoCloseDialog setOktext(String oktext) {
        this.oktext = oktext;
        return this;
    }


    public static CancelNoCloseDialog newInstance() {
        CancelNoCloseDialog dialog = new CancelNoCloseDialog();
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
                if (onClickListener != null)
                    onClickListener.successful();
            }
        });
        if (!isQuBtn) {
            binding.tvCancle.setVisibility(View.GONE);
        }
        binding.tvContext.setTextColor(getResources().getColor(contextColor));
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
