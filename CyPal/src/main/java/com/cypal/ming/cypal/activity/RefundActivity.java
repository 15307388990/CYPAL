package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.MemberEntity;
import com.cypal.ming.cypal.bean.RefoundBailEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.AccountDialog;
import com.cypal.ming.cypal.dialogfrment.MarginDialog;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.HashMap;
import java.util.Map;

import static com.cypal.ming.cypal.config.Const.submitBailMoney;

/**
 * 退还保证金
 */
public class RefundActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private ImageView iv_wexin;
    private ImageView iv_alipay;
    private ImageView iv_banl;
    private ImageView iv_yun;
    private LinearLayout ll_account;
    private Button btn_next;
    private TextView tv_tui;
    private TextView tv_choose;
    private LinearLayout ll_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_refund );
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        refoundBailMoney();

    }

    public void refoundBailMoney() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.refoundBailMoney, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();
    }

    public void sureRefoundBailMoney() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.sureRefoundBailMoney, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        iv_wexin = (ImageView) findViewById( R.id.iv_wexin );
        iv_alipay = (ImageView) findViewById( R.id.iv_alipay );
        iv_banl = (ImageView) findViewById( R.id.iv_banl );
        iv_yun = (ImageView) findViewById( R.id.iv_yun );
        ll_account = (LinearLayout) findViewById( R.id.ll_account );
        btn_next = (Button) findViewById( R.id.btn_next );
        btn_next.setOnClickListener( this );
        tv_tui = (TextView) findViewById( R.id.tv_tui );
        tv_choose = (TextView) findViewById( R.id.tv_choose );
        ll_choose = (LinearLayout) findViewById( R.id.ll_choose );
        ll_choose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountDialog accountDialog = AccountDialog.newInstance();
                accountDialog.setOnClickListener( new AccountDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        //设置成功刷新数据
                        refoundBailMoney();
                    }
                } );
                accountDialog.show( RefundActivity.this );
            }
        } );
    }


    @Override
    protected void returnData(String data, String url) {
        if (url.contains( Const.refoundBailMoney )) {
            RefoundBailEntity refoundBailEntity = JSON.parseObject( data, RefoundBailEntity.class );
            if (!TextUtils.isEmpty( refoundBailEntity.data.usedPayAccountList )) {
                ll_account.setVisibility( View.VISIBLE );
                tv_choose.setVisibility( View.GONE );

                String pay = refoundBailEntity.data.usedPayAccountList;
                iv_wexin.setVisibility( View.GONE );
                iv_alipay.setVisibility( View.GONE );
                iv_yun.setVisibility( View.GONE );
                iv_banl.setVisibility( View.GONE );
                if (pay.contains( "WXPAY" )) {
                    iv_wexin.setVisibility( View.VISIBLE );
                }
                if (pay.contains( "ALIPAY" )) {
                    iv_alipay.setVisibility( View.VISIBLE );
                }
                if (pay.contains( "CLOUDPAY" )) {
                    iv_yun.setVisibility( View.VISIBLE );
                }
                if (pay.contains( "BANKCARD" )) {
                    iv_banl.setVisibility( View.VISIBLE );
                }
            } else {
                ll_account.setVisibility( View.GONE );
                tv_choose.setVisibility( View.VISIBLE );
            }
        } else if (url.contains( Const.sureRefoundBailMoney )) {
            Intent intent = new Intent( RefundActivity.this, SucceedWithdrawAccessActivity.class );
            intent.putExtra( "title", "退还保证金" );
            intent.putExtra( "context", "提交成功" );
            intent.putExtra( "text", "我们将尽快为你退还保证金" );
            startActivity( intent );
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                sureRefoundBailMoney();
                break;
        }
    }
}
