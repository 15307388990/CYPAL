package com.fmt.ming.paotui.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmt.ming.paotui.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class LoadingDialog extends Dialog {
    private TextView tv;
    private String title;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ImageView iv_img;

    public LoadingDialog(Context context, String title) {
        super(context, R.style.loadingDialogStyle);
        this.title = title;
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        tv = (TextView) this.findViewById(R.id.tv);
        tv.setText(title);
        iv_img = (ImageView) this.findViewById(R.id.iv_img);
        AnimationDrawable mAnimation = new AnimationDrawable();
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_01), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_02), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_03), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_04), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_05), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_06), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_07), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_08), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_09), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_10), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_11), 100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_12), 100);
        mAnimation.setOneShot(false);
        iv_img.setBackground(mAnimation);
        if (mAnimation != null && !mAnimation.isRunning()) {
            mAnimation.start();
        }

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.dimAmount = 0.6f;
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
