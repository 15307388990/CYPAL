package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        view = (LinearLayout) findViewById(R.id.welcome_layout);
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);// 动画透明度
        anim.setDuration(2000);
        view.startAnimation(anim);
        anim.setAnimationListener(new WelcomeAnimation());
    }

    private Boolean isFirstEnter = false;

    private class WelcomeAnimation implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isFirstEnter = mSavePreferencesData.getBooleanData("isFirstEnter");
            Intent intent = new Intent();
            String auth_token = mSavePreferencesData.getStringData("auth_token");
            if (auth_token != null && !auth_token.equals("")) {
                intent.setClass(WelcomeActivity.this, TabActivity.class);
            } else {
                intent.setClass(WelcomeActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            WelcomeActivity.this.finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }


    @Override
    public void onResponse(String response, String url) {

    }

}
