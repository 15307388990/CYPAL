package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.MemberEntity;
import com.cypal.ming.cypal.dialogfrment.MarginDialog;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

import static com.cypal.ming.cypal.config.Const.certification;
import static com.cypal.ming.cypal.config.Const.submitBailMoney;

/**
 * 会员管理
 */
public class MemberActivity extends BaseActivity {


    private LinearLayout ll_view_back;
    private ImageView iv_img;
    private TextView tv_name;
    private TextView tv_code;
    private TextView tv_bao;
    private TextView tv_staus;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();
    private ImageView iv_renzhen;
    private TextView tv_tui;
    private TextView tv_text;
    private RelativeLayout rl_layout;
    private RelativeLayout rl_wei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_member );
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Certification();

    }

    public void Certification() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( certification, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();
    }

    public void submitBailMoney() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( submitBailMoney, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
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
        iv_img = (ImageView) findViewById( R.id.iv_img );
        tv_name = (TextView) findViewById( R.id.tv_name );
        tv_code = (TextView) findViewById( R.id.tv_code );
        tv_bao = (TextView) findViewById( R.id.tv_bao );
        tv_staus = (TextView) findViewById( R.id.tv_staus );
        iv_renzhen = (ImageView) findViewById( R.id.iv_renzhen );
        tv_tui = (TextView) findViewById( R.id.tv_tui );
        tv_text = (TextView) findViewById( R.id.tv_text );
        rl_layout = (RelativeLayout) findViewById( R.id.rl_layout );
        rl_wei = (RelativeLayout) findViewById( R.id.rl_wei );
        rl_wei.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( MemberActivity.this, CertificationActivity.class, false );
            }
        } );
        tv_text.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals( "缴纳保证金", tv_text.getText().toString() )) {
                    deleteOrderDialog();
                } else {
                    Tools.jump( MemberActivity.this, RefundActivity.class, false );
                }

            }
        } );
    }
    private void deleteOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要缴纳3000元人民币保证金吗？");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitBailMoney();
            }
        });
        builder.create().show();
    }


    @Override
    protected void returnData(String data, String url) {
        if (url.contains( certification )) {
            MemberEntity memberEntity = JSON.parseObject( data, MemberEntity.class );
            initDate( memberEntity );
        } else if (url.contains( submitBailMoney )) {
            MarginDialog marginDialog;
            marginDialog = MarginDialog.newInstance( "余额中扣除" );
            marginDialog.show( MemberActivity.this );
            //刷新请求
            Certification();
        }


    }

    private void initDate(MemberEntity memberEntity) {
        if (memberEntity == null) {
            Tools.showToast( MemberActivity.this, "数据解析错误" );
            return;
        }
        //身份信息
        tv_name.setText( memberEntity.data.certificationInfo.realName );
        tv_code.setText( memberEntity.data.certificationInfo.identitycardNumber );
        imageLoader.displayImage( memberEntity.data.certificationInfo.identitycardFront, iv_img,
                options );
        //审核状态
        if (memberEntity.data.certification_status.equals( "SUCCESS" )) {
            //完成
            iv_renzhen.setImageResource( R.drawable.icon_yirenzhen );
            rl_layout.setVisibility( View.VISIBLE );
            rl_wei.setVisibility( View.GONE );
        } else if (memberEntity.data.certification_status.equals( "PROCESS" )) {
            //待审核
            iv_renzhen.setImageResource( R.drawable.iocn_shenhezhong );
            rl_layout.setVisibility( View.VISIBLE );
            rl_wei.setVisibility( View.GONE );
        } else {
            //没有完成
            iv_renzhen.setImageResource( R.drawable.iocn_weitongguo );
            rl_wei.setVisibility( View.VISIBLE );
            rl_layout.setVisibility( View.GONE );
        }
        //保证金状态
        if (memberEntity.data.bailMoneyRecord_status.equals( "SUCCESS" )) {
            //完成
            tv_staus.setText( "已缴纳" );
            tv_staus.setBackgroundResource( R.drawable.yijiaona_bg );
            tv_tui.setVisibility( View.GONE );
            tv_text.setText( "申请退还保证金" );
        } else if (memberEntity.data.bailMoneyRecord_status.equals( "PROCESS" )) {
            //待审核
            tv_staus.setText( "退还中" );
            tv_staus.setBackgroundResource( R.drawable.shenhezhong_bg );
            tv_tui.setVisibility( View.VISIBLE );
            tv_text.setText( "" );

        } else {
            //没有完成
            tv_staus.setText( "未缴纳" );
            tv_staus.setBackgroundResource( R.drawable.weiyaoqing_bg );
            tv_tui.setVisibility( View.GONE );
            tv_text.setText( "缴纳保证金" );

        }

    }
}
