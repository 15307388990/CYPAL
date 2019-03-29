package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.utils.Tools;

import okhttp3.internal.platform.Platform;


/**
 * @Author luoming
 * @Date 2019/3/13 10:09 PM
 */
public abstract class CenterDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog( getActivity(), R.style.DialogStyle );
        if (dialog.getWindow() != null) {
            dialog.getWindow().setDimAmount( 0.65f );
        }
        ViewDataBinding binding = getLayoutBind();
        View view = binding.getRoot();
        dialog.setContentView( view );
        initView( binding );
        initWindowParams( dialog );
        return dialog;
    }

    public int getWindowWidth() {
        return (Tools.getScreenWidth( getActivity() ) * 5 / 6);
    }

    public int getScreenHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show( manager, tag );
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    // 设置Dialog属性
    protected void initWindowParams(Dialog dialog) {
        Window win = dialog.getWindow();
        if (win != null) {
//            win.setWindowAnimations(R.style.rise);  //添加动画
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = getWindowWidth();
            lp.height = getScreenHeight();
            lp.gravity = getGravity();
            win.setAttributes( lp );
        }
    }


    public ViewDataBinding getLayoutBind() {
        return DataBindingUtil.inflate( getActivity().getLayoutInflater(), getLayoutId(), null
                , false );
    }

    public abstract int getLayoutId();

    public abstract void initView(ViewDataBinding dataBinding);


    public void show(Object object) {
        show( object, this.toString() );
    }

    public void show(Object object, String tag) {
        if (object instanceof Activity) {
            android.app.FragmentManager manager = ((Activity) object).getFragmentManager();
            if (isAdded()) {
                dismiss();
            }
            super.show( manager, tag );
        } else if (object instanceof Fragment) {
            android.app.FragmentManager fragmentManager = ((Fragment) object).getActivity().getFragmentManager();
            if (isAdded()) {
                dismiss();
            }
            super.show( fragmentManager, tag );

        }
    }
}
