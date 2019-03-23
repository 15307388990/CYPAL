package com.cypal.ming.cypal.activity;

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
 * 退还保证金
 */
public class RefundActivity extends BaseActivity {


    private LinearLayout ll_view_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_refund );
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


    private void initView() {
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
    }


    @Override
    protected void returnData(String data, String url) {
        if (url.contains( certification )) {
            MemberEntity memberEntity = JSON.parseObject( data, MemberEntity.class );
        } else if (url.contains( submitBailMoney )) {
            MarginDialog marginDialog;
            marginDialog = MarginDialog.newInstance( "余额中扣除" );
            marginDialog.show( RefundActivity.this );
        }


    }

}
