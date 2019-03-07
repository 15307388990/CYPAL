package com.fmt.ming.paotui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.utils.Tools;
import com.fmt.ming.paotui.widgets.dialogs.QuitCheckPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonCenterActivity extends BaseActivity implements QuitCheckPopupWindow.onConfirmListener {

    @Bind(R.id.img_back)
    ImageView imgBack;

    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Bind(R.id.tv_quit)
    TextView tvQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        initTitle();
        imgBack.setVisibility(View.VISIBLE);
        title.setText("售货明细");
        rightView.setVisibility(View.GONE);
        title.setText("个人中心");
        tvVersion.setText("版本 v" + getVersionName());
    }

    /**
     * 获取当前程序的版本号
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    @Override
    public void onResponse(String response, String url) {

    }

    @OnClick({R.id.img_back, R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_quit:
                QuitCheckPopupWindow quitCheckPopupWindow = new QuitCheckPopupWindow(this, this);
                quitCheckPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }
    }

    @Override
    public void onConfirm() {
        if (storeBean != null) {
            storeBean = null;
            mSavePreferencesData.putStringData("json", null);
        }
        finish();
        Tools.jump(this, LoginActivity.class, false);
    }
}
