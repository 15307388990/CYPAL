package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialog.AuthCodeDialog;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.MyCountTimer;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.internal.platform.Platform;

/**
 * 找回密码
 */
public class RetrievePasswordActivity extends BaseActivity implements View.OnClickListener {


    // private AuthCodeDialog authCodeDialog;
    private CountDownTimer countTimer;
    private ImageView img_back;
    private LinearLayout ll_view_back;
    private ImageView right_view_text;
    private TextView top_view_text;
    private RadioButton rd_phone;
    private RadioButton rd_email;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private EditText et_iphone;
    private EditText et_code;
    private TextView tv_code;
    private EditText et_new;
    private EditText et_new2;
    private Button btn_next;
    private RelativeLayout.LayoutParams params;
    private int cursorWidth;
    private int currentSelectTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_retrueve_password );
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.top_background ) );
        ButterKnife.bind( this );
        initView();
        initOnclik();
        initEvent();
    }

    private void initView() {
        img_back = (ImageView) findViewById( R.id.img_back );
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        right_view_text = (ImageView) findViewById( R.id.right_view_text );
        top_view_text = (TextView) findViewById( R.id.top_view_text );
        rd_phone = (RadioButton) findViewById( R.id.rd_phone );
        rd_email = (RadioButton) findViewById( R.id.rd_email );
        rd_group = (RadioGroup) findViewById( R.id.rd_group );
        cursor = (LinearLayout) findViewById( R.id.cursor );
        et_iphone = (EditText) findViewById( R.id.et_iphone );
        et_code = (EditText) findViewById( R.id.et_code );
        tv_code = (TextView) findViewById( R.id.tv_code );
        et_new = (EditText) findViewById( R.id.et_new );
        et_new2 = (EditText) findViewById( R.id.et_new2 );
        btn_next = (Button) findViewById( R.id.btn_next );
        countTimer = new MyCountTimer( this, tv_code, "获取验证码", R.color.tab_text_color_select, R.color.darkgray );
        rd_phone.setChecked( true );
        currentSelectTab = 0;
    }

    public void initEvent() {
        params = (RelativeLayout.LayoutParams) cursor.getLayoutParams();
        cursorWidth = params.width = Tools.getScreenWidth( RetrievePasswordActivity.this ) / 2;
        cursor.setLayoutParams( params );
        rd_group.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_phone) {
                    currentSelectTab = 0;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_email) {
                    currentSelectTab = 1;
                    params.leftMargin = cursorWidth;
                    cursor.setLayoutParams( params );
                }
            }
        } );
    }

    private void initOnclik() {
        img_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        btn_next.setOnClickListener( this);
        tv_code.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhoneMsg();
            }
        } );
    }

    //手动输入的手机号
    private void sendPhoneMsg() {
        //防止重复点击操作
        if (!tv_code.getText().toString().equals( "发送验证码" )) {
            return;
        }
        if (et_iphone.getText().toString().length() == 0) {
            Toast.makeText( this, "手机号不能为空", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (!Tools.isMobileNum( et_iphone.getText().toString() )) {
            Toast.makeText( this, "手机号格式有误", Toast.LENGTH_SHORT ).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put( "account", et_iphone.getText().toString() );
        if (currentSelectTab == 0) {
            map.put( "registerType", "PHONE" );
        } else {
            map.put( "registerType", "EMAIL" );
        }
        mQueue.add( ParamTools.packParam( Const.forgetPassword, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();
    }

    /* 修改密码 */
    public void Repassword() {
        Map<String, String> map = new HashMap<>();
        map.put( "account", et_iphone.getText().toString().trim() );
        map.put( "verifyCode", et_code.getText().toString().trim() );
        String password = MD5Util.getMD5String( et_new.getText().toString().trim() );
        String password2 = MD5Util.getMD5String( et_new2.getText().toString().trim() );
        map.put( "password", password );
        map.put( "two_password", password2 );
        mQueue.add( ParamTools.packParam( Const.repassword, this, this, map ) );
        loading();
    }

    @Override
    protected void returnData(String data, String url) {
        super.returnData( data, url );
        if (url.contains( Const.forgetPassword )) {

//            if (authCodeDialog != null && authCodeDialog.isShowing()) {
//                authCodeDialog.dismiss();
//            }
            countTimer.start();// 开启定时器
            tv_code.setVisibility( View.VISIBLE );
        } else if (url.contains( Const.repassword )) {
            Tools.showToast( this, "密码修改成功" );
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String iphone = et_iphone.getText().toString().trim();
        if (TextUtils.isEmpty( iphone )) {
            Toast.makeText( this, "请输入手机号", Toast.LENGTH_SHORT ).show();
            return;
        }

        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty( code )) {
            Toast.makeText( this, "请输入验证码", Toast.LENGTH_SHORT ).show();
            return;
        }

        String new1 = et_new.getText().toString().trim();
        if (TextUtils.isEmpty( new1 )) {
            Toast.makeText( this, "请输入6～18位英文数字组合密码", Toast.LENGTH_SHORT ).show();
            return;
        }

        String new2 = et_new2.getText().toString().trim();
        if (TextUtils.isEmpty( new2 )) {
            Toast.makeText( this, "确认密码", Toast.LENGTH_SHORT ).show();
            return;
        }
        Repassword();
        // TODO validate success, do something


    }
}
