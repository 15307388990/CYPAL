package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.cypal.ming.cypal.bean.StoreBean;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialog.CustomDialogActivity;
import com.cypal.ming.cypal.service.VersionService;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        initView();
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.top_background ) );
        Tools.webacts.add( this );
        ButterKnife.bind( this );
        tv_f_password.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( LoginActivity.this, RetrievePasswordActivity.class, false );
            }
        } );
        tv_registered.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( LoginActivity.this, RegisteredActivity.class, false );
            }
        } );
        Intent intent = new Intent();
        String auth_token = mSavePreferencesData.getStringData( "token" );
        if (auth_token != null && !auth_token.equals( "" )) {
            intent.setClass( LoginActivity.this, TabActivity.class );
            startActivity( intent );
        }
        // jiebianAppVersion();
//        etLoginPassword.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (etLoginPassword.getText().length() >= 1) {
//                    login.setBackgroundResource(R.drawable.login_btn_n);
//                    login.setTextColor(getResources().getColor(R.color.actionbar_title_color));
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
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

    @OnClick(R.id.login)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (checkParams()) {
                    toLogin( name, pwd );
                }
                break;
        }
    }

    /* 执行登录操作 */
    public void toLogin(String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put( "mobile", name );
        map.put( "password", pwd );
        map.put( "israpp", "1" );
        mQueue.add( ParamTools.packParam( Const.venderLogin, this, this, map ) );
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

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "status" );
            String msg = json.optString( "msg" );
            if (stauts == 200) {
                String token = json.optString( "token" );
                mSavePreferencesData.putStringData( "token", token );
                mSavePreferencesData.putStringData( "name", name );
                mSavePreferencesData.putStringData( "pwd", pwd );

                String date = json.optString( "data" );
                mSavePreferencesData.putStringData( "json", date );
                storeBean = JSON.parseObject( date, StoreBean.class );
                // 友盟统计
                MobclickAgent.onProfileSignIn( Tools.getDeviceBrand(), name );
                finish();
                Tools.jump( this, TabActivity.class, false );

            } else {
                Tools.showToast( this, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast( this, "发生错误,请重试!" );
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

        login.setOnClickListener( this );
        tv_registered = (TextView) findViewById( R.id.tv_registered );
        tv_registered.setOnClickListener( this );
        tv_f_password = (TextView) findViewById( R.id.tv_f_password );
        tv_f_password.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                break;
        }
    }

    private void submit() {
        // validate
        String account = et_login_account.getText().toString().trim();
        if (TextUtils.isEmpty( account )) {
            Toast.makeText( this, "手机号或邮箱", Toast.LENGTH_SHORT ).show();
            return;
        }

        String password = et_login_password.getText().toString().trim();
        if (TextUtils.isEmpty( password )) {
            Toast.makeText( this, "请输入密码", Toast.LENGTH_SHORT ).show();
            return;
        }

        // TODO validate success, do something


    }
}
