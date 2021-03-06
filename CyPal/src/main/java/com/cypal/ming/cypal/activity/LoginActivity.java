package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.base.BaseView;
import com.cypal.ming.cypal.bean.LoginEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialog.CustomDialogActivity;
import com.cypal.ming.cypal.service.VersionService;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private String name, pwd;
    private EditText et_login_account;
    private LinearLayout layout_login_account;
    private TextView tv_account;
    private EditText et_login_password;
    private LinearLayout layout_login_password;
    private TextView tv_password;
    private Button login;
    private TextView iv_prompt;
    private TextView tv_registered;
    private TextView tv_f_password;
    private CheckBox tv_change;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        initView();
        Tools.webacts.add( this );
        ButterKnife.bind( this );
        // jiebianAppVersion();
    }

    private void loadVersion() {
        PackageManager pm = this.getPackageManager();// context为当前Activity上下
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo( this.getPackageName(), 0 );
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //检测版本
    private void jiebianAppVersion() {
        HttpParams httpParams = new HttpParams();
        httpParams.put( "israpp", "1" );
        VersionParams.Builder builder = new VersionParams.Builder().setRequestMethod( HttpRequestMethod.POST ).setRequestParams( httpParams )
                .setRequestUrl( Const.BASE_URL + Const.config )
                .setCustomDownloadActivityClass( CustomDialogActivity.class )
                .setService( VersionService.class );
        AllenChecker.startVersionCheck( this, builder.build() );

    }


    /* 执行登录操作 */
    public void toLogin(String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put( "account", name );
        pwd = MD5Util.getMD5String( pwd );
        map.put( "password", pwd );
        mQueue.add( ParamTools.packParam( Const.venderLogin, LoginActivity.this, this, this, map ) );
        loading();
    }


    /**
     * 检测参数合法性
     */
    public boolean checkParams() {
        name = et_login_account.getText().toString();
        pwd = et_login_password.getText().toString();
        if (name == null || name.length() == 0) {
            tv_account.setVisibility( View.VISIBLE );
        } else {
            tv_account.setVisibility( View.INVISIBLE );
        }
        if (pwd == null || pwd.length() == 0) {
            tv_password.setVisibility( View.VISIBLE );

        } else {
            tv_password.setVisibility( View.INVISIBLE );
        }
        if (name == null || name.length() == 0 || pwd == null || pwd.length() == 0) {

            return false;
        } else {
            return true;
        }


    }


    private void initView() {
        et_login_account = (EditText) findViewById( R.id.et_login_account );
        layout_login_account = (LinearLayout) findViewById( R.id.layout_login_account );
        tv_account = (TextView) findViewById( R.id.tv_account );
        et_login_password = (EditText) findViewById( R.id.et_login_password );
        layout_login_password = (LinearLayout) findViewById( R.id.layout_login_password );
        tv_password = (TextView) findViewById( R.id.tv_password );
        login = (Button) findViewById( R.id.login );
        iv_prompt = (TextView) findViewById( R.id.iv_prompt );
        tv_registered = (TextView) findViewById( R.id.tv_registered );
        tv_f_password = (TextView) findViewById( R.id.tv_f_password );


        tv_f_password.setOnClickListener( this );
        tv_registered.setOnClickListener( this );
        login.setOnClickListener( this );
        tv_change = (CheckBox) findViewById( R.id.tv_change );
        tv_change.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_login_password.setTransformationMethod( PasswordTransformationMethod.getInstance() );
                } else {
                    et_login_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );

                }

            }
        } );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (checkParams()) {
                    toLogin( name, pwd );
                }
                break;
            case R.id.tv_registered:
                Tools.jump( LoginActivity.this, RegisteredActivity.class, false );
                break;
            case R.id.tv_f_password:
                Tools.jump( LoginActivity.this, RetrievePasswordActivity.class, false );
                break;

        }
    }


    @Override
    public void returnData(String data, String url) {
        LoginEntity loginEntity = JSON.parseObject( data, LoginEntity.class );
        mSavePreferencesData.putStringData( "token", loginEntity.data.loginToken );
        //bug ly记录用户ID
        CrashReport.setUserId( et_login_account.getText().toString().trim() );
        Tools.jump( this, TabActivity.class, true );
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
                Toast.makeText( this, "再按一次退出程序", Toast.LENGTH_SHORT ).show();
                mExitTime = System.currentTimeMillis();

            } else {
                Intent intent = new Intent( Intent.ACTION_MAIN );
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );// 注意
                intent.addCategory( Intent.CATEGORY_HOME );
                this.startActivity( intent );
                finish();
            }
            return true;
        }

        return super.onKeyDown( keyCode, event );
    }

}
