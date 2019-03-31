package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;

/**
 * 类名称：SucceedWithdrawAccessActivity 类描述：提现成功界面 创建人：罗富贵 创建时间：2016年5月11日
 */
public class SucceedWithdrawAccessActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_context;
    private Button btn_return;
    private TextView tv_text;
    private TextView top_view_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_succeed_withdraw );
        initView();
        initTitle();
        String titletext = getIntent().getStringExtra( "title" );
        String context = getIntent().getStringExtra( "context" );
        String text = getIntent().getStringExtra( "tv_text" );
        if (!TextUtils.isEmpty( text )) {
            tv_text.setText( text );
            tv_text.setVisibility( View.VISIBLE );
        }
        top_view_text.setText( titletext );
        tv_context.setText( context );

    }


    private void initView() {
        img_back = (ImageView) findViewById( R.id.img_back );
        tv_context = (TextView) findViewById( R.id.tv_context );
        btn_return = (Button) findViewById( R.id.btn_return );
        tv_text = (TextView) findViewById( R.id.tv_text );
        btn_return.setOnClickListener( this );
        top_view_text = (TextView) findViewById( R.id.top_view_text );
        img_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                finish();

                break;
        }
    }
}
