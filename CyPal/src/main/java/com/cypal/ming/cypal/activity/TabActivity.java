package com.cypal.ming.cypal.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.VersionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialog.CustomDialogActivity;
import com.cypal.ming.cypal.dialogfrment.ConfirmPaymentDialog;
import com.cypal.ming.cypal.dialogfrment.VersionUpgradeDialog;
import com.cypal.ming.cypal.fragment.MainFragment;
import com.cypal.ming.cypal.fragment.MineFragment;
import com.cypal.ming.cypal.fragment.TopUpFragment;
import com.cypal.ming.cypal.service.VersionService;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * 首页
 */
public class TabActivity extends BaseActivity {
    private Fragment mMianFragment, topUpFragment, mMinFragment;
    private RadioButton tab_rb_home;
    private RadioButton tab_rb_mine;
    private FrameLayout fragment_container;
    private RadioGroup tab_rg_menu;
    private long mExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_background));
        Tools.webacts.add(this);
        initView();
        initDate();
      //  AppVersion();
        ButterKnife.bind(this);
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
                        if (mMianFragment != null) {
                            transaction.remove(mMianFragment);
                        }
                        mMianFragment = new MainFragment(TabActivity.this);
                        transaction.add(R.id.fragment_container, mMianFragment);
                        break;
                    case R.id.tab_rb_up:
                        if (topUpFragment != null) {
                            transaction.remove(topUpFragment);
                        }
                        topUpFragment = new TopUpFragment(TabActivity.this);
                        transaction.add(R.id.fragment_container, topUpFragment);

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
        if (topUpFragment != null) {
            transaction.hide(topUpFragment);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    //检测版本
    private void AppVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("osEnum", "android");
        map.put("versionId", Tools.packageCode(this) + "");
        mQueue.add(ParamTools.packParam(Const.check, this, this, this, map));

    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.check)) {
            VersionEntity versionEntity = JSON.parseObject(data, VersionEntity.class);
//            if (versionEntity.data.updateType != -1) {
//                VersionUpgradeDialog versionUpgradeDialog = VersionUpgradeDialog.newInstance(versionEntity.data);
//
//                versionUpgradeDialog.show(TabActivity.this);
//            }

        }
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
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "文件下载", R.drawable.permission_ic_storage));
        permissonItems.add( new PermissionItem( Manifest.permission.READ_EXTERNAL_STORAGE, "文件读取", R.drawable.permission_ic_location ) );
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
    }

}
