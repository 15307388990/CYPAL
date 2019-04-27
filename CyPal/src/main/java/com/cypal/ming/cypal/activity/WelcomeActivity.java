package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.utils.Tools;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class WelcomeActivity extends BaseActivity {
    private LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        view = (LinearLayout) findViewById(R.id.welcome_layout);
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);// 动画透明度
        anim.setDuration(500);
        view.startAnimation(anim);
        anim.setAnimationListener(new WelcomeAnimation());
//        Intent intent = new Intent();
//        String auth_token = mSavePreferencesData.getStringData("token");
//        if (auth_token != null && !auth_token.equals("")) {
//            intent.setClass(WelcomeActivity.this, TabActivity.class);
//        } else {
//            intent.setClass(WelcomeActivity.this, LoginActivity.class);
//        }
//        startActivity(intent);
//        WelcomeActivity.this.finish();
    }


    private class WelcomeAnimation implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            initPermission();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }
    /**
     * 获取手机信息权限
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_PHONE_STATE).
                onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Intent intent = new Intent();
                        String auth_token = mSavePreferencesData.getStringData("token");
                        if (auth_token != null && !auth_token.equals("")) {
                            intent.setClass(WelcomeActivity.this, TabActivity.class);
                        } else {
                            intent.setClass(WelcomeActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Tools.showToast(WelcomeActivity.this, "无法获取手机状态信息，应用无法正常使用");
                    }
                }).
                start();

    }

    @Override
    public void onResponse(String response, String url) {

    }

}
