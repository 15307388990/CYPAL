package com.fmt.ming.paotui.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.dialog.CustomDialogActivity;
import com.fmt.ming.paotui.fragment.MainFragment;
import com.fmt.ming.paotui.fragment.MineFragment;
import com.fmt.ming.paotui.service.LocationService;
import com.fmt.ming.paotui.service.VersionService;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * 首页
 */
public class TabActivity extends BaseActivity {
    public Fragment mMianFragment, mMinFragment;
    private RadioButton tab_rb_home;
    private RadioButton tab_rb_mine;
    private FrameLayout fragment_container;
    private RadioGroup tab_rg_menu;
    private int serviceVersion; //服务器版本
    private int localVersion;//本地版本
    private String mall_name;//版本名称
    Runnable runnable;
    private long mExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.separator_color));
        Tools.webacts.add(this);
        initView();
        initDate();
        // mallSetInfo();
        //jiebianAppVersion();
        ButterKnife.bind(this);
        Intent intent = new Intent(TabActivity.this, LocationService.class);
        startService(intent);
        //注册别名
        JPushInterface.setAlias(this, 1, "fmt_" + storeBean.getId());
        setStyleCustom();
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
//        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(TabActivity.this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.tv_time);
//        builder.layoutIconDrawable = R.drawable.ic_launcher;
//        builder. = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.mm);
//        JPushInterface.setPushNotificationBuilder(2, builder);
    }

    /* 获取店铺详情 */
    private void mallSetInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
        mQueue.add(ParamTools.packParam(Const.mallSetInfo, this, this, map));
    }


    public void initDate() {
        tab_rg_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TOD
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                hideAllFragment(transaction);
                switch (checkedId) {
                    case R.id.tab_rb_home:
                        if (mMianFragment == null) {
                            mMianFragment = new MainFragment(TabActivity.this);
                            transaction.add(R.id.fragment_container, mMianFragment);
                        } else {
                            transaction.show(mMianFragment);
                        }
                        break;
                    case R.id.tab_rb_mine:
                        if (mMinFragment != null) {
                            transaction.remove(mMinFragment);
                        }
                        mMinFragment = new MineFragment(TabActivity.this);
                        transaction.add(R.id.fragment_container, mMinFragment);

                        break;


                    default:
                        break;
                }
                transaction.commit();

            }
        });
        tab_rg_menu.check(R.id.tab_rb_home);
    }

    // 隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (mMianFragment != null) {
            transaction.hide(mMianFragment);
        }
        if (mMinFragment != null) {
            transaction.hide(mMinFragment);
        }
    }


    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                if (url.contains(Const.mallSetInfo)) {
                    mSavePreferencesData.putStringData("json", json.optString("result"));
                    initAdmin();
                }
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //检测版本
    private void jiebianAppVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("device_type", "0");//设备类型 0为Android 1为ios
        map.put("auth_token", partnerBean.getAuth_token());
        map.put("app_type", "2");//1商户版2街边go3俊鹏
//        mQueue.add(ParamTools.packParam(Const.jiebianAppVersion, this, this, map));
//        loading();
        HttpParams httpParams = new HttpParams();
        httpParams.put("device_type", "0");//设备类型 0为Android 1为ios
        httpParams.put("auth_token", partnerBean.getAuth_token());
        httpParams.put("app_type", "2");//1商户版2街边go3俊鹏
        VersionParams.Builder builder = new VersionParams.Builder().setRequestMethod(HttpRequestMethod.POST).setRequestParams(httpParams)
                .setRequestUrl(Const.BASE_URL + Const.jiebianAppVersion)
                .setCustomDownloadActivityClass(CustomDialogActivity.class)
                .setService(VersionService.class);
        AllenChecker.startVersionCheck(this, builder.build());

    }

    private void initView() {
        tab_rb_home = (RadioButton) findViewById(R.id.tab_rb_home);
        tab_rb_mine = (RadioButton) findViewById(R.id.tab_rb_mine);
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        tab_rg_menu = (RadioGroup) findViewById(R.id.tab_rg_menu);

        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, "打电话", R.drawable.permission_ic_phone));
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "拍照", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "相册", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "定位", R.drawable.permission_ic_location));
        HiPermission.create(TabActivity.this).permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        // showToast("用户关闭权限申请");
                        Tools.showToast(TabActivity.this, "可能导致有些功能不能正常使用");
                    }

                    @Override
                    public void onFinish() {
                        //showToast("所有权限申请完成");
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        // Log.i(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        //Log.i(TAG, "onGuarantee");
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // if ((System.currentTimeMillis() - mExitTime) > 2000)
            // {Toast.makeText(this,
            // getResources().getString(R.string.exit).toString(),
            // Toast.LENGTH_SHORT).show();
            // mExitTime = System.currentTimeMillis();}

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intent.addCategory(Intent.CATEGORY_HOME);
                this.startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMinFragment.onActivityResult(requestCode, resultCode, data);
    }

}
