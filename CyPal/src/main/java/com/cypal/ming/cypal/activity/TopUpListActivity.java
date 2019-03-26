package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.adapter.TopUpRecordListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.TopUpListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值记录列表
 */
public class TopUpListActivity extends BaseActivity implements CategoryAdapter.OnClickListener {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private TopUpRecordListAdapter topUpListAdapter;
    private List<TopUpListEntity.DataBean.ContentBean> list;
    private LinearLayout.LayoutParams params;
    private int cursorWidth;
    private int currentSelectTab;
    private RadioButton rb_top_jin;
    private RadioButton rb_top_wancheng;
    private RadioGroup rg_top;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private boolean isFinish = true;
    private TopUpState topUpState;
    private RadioButton rd_wancheng;
    private RadioButton rd_quxiao;
    private RadioGroup rg_top_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_top_up_list );
        initView();
    }

    private void orderlist() {
        Map<String, String> map = new HashMap<>();
        map.put( "isFinish", isFinish + "" );
        if (topUpState != null) {
            map.put( "statusEnum", topUpState + "" );
        }
        mQueue.add( ParamTools.packParam( Const.orderlist, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        recycleView = (RecyclerView) findViewById( R.id.recycleView );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        list = new ArrayList<>();
        topUpListAdapter = new TopUpRecordListAdapter( TopUpListActivity.this, list );
        recycleView.setAdapter( topUpListAdapter );
        recycleView.setLayoutManager( new LinearLayoutManager( this ) );

        rb_top_jin = (RadioButton) findViewById( R.id.rb_top_jin );
        rb_top_wancheng = (RadioButton) findViewById( R.id.rb_top_wancheng );
        rg_top = (RadioGroup) findViewById( R.id.rg_top );
        rd_group = (RadioGroup) findViewById( R.id.rd_group );
        cursor = (LinearLayout) findViewById( R.id.cursor );
        rd_wancheng = (RadioButton) findViewById( R.id.rd_wancheng );
        rd_quxiao = (RadioButton) findViewById( R.id.rd_quxiao );
        rg_top_2 = (RadioGroup) findViewById( R.id.rg_top_2 );
        rg_top.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_top_jin) {
                    isFinish = true;
                } else if (checkedId == R.id.rb_top_wancheng) {
                    isFinish = false;

                }
                initEvent();

            }
        } );
        rd_group.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_all) {
                    topUpState = null;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_wei) {
                    topUpState = TopUpState.TRADING;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_yi) {
                    topUpState = TopUpState.CONFIRM;
                    params.leftMargin = (int) cursorWidth * 2;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_shen) {
                    topUpState = TopUpState.SERVICE;
                    params.leftMargin = (int) cursorWidth * 3;
                    cursor.setLayoutParams( params );
                }
                orderlist();
            }
        } );
        rg_top_2.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rd_wancheng) {
                    topUpState = TopUpState.SUCCESS;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_quxiao) {
                    topUpState = TopUpState.CANCEL;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams( params );
                }
                orderlist();
            }
        } );
        initEvent();
    }

    public void initEvent() {
        params = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        if (isFinish) {
            cursorWidth = params.width = Tools.getScreenWidth( TopUpListActivity.this ) / 4;
            cursor.setLayoutParams( params );
            rd_group.setVisibility( View.VISIBLE );
            rg_top_2.setVisibility( View.GONE );
            rd_group.check( R.id.rd_all );
            topUpState = null;

        } else {
            cursorWidth = params.width = Tools.getScreenWidth( TopUpListActivity.this ) / 2;
            cursor.setLayoutParams( params );
            rd_group.setVisibility( View.GONE );
            rg_top_2.setVisibility( View.VISIBLE );
            rg_top_2.check( R.id.rd_wancheng );
            topUpState = TopUpState.SUCCESS;
        }
        params.leftMargin = 0;
        cursor.setLayoutParams( params );
        orderlist();

    }

    @Override
    protected void returnData(String data, String url) {
        TopUpListEntity topUpListEntity = JSON.parseObject( data, TopUpListEntity.class );
        list = topUpListEntity.data.content;
        topUpListAdapter.updateAdapter( list );

    }

    @Override
    public void ConfirmReceipt(String value, String type) {
        Intent intent = new Intent( TopUpListActivity.this, BankList.class );
        intent.putExtra( "value", value );
        intent.putExtra( "type", type );
        startActivity( intent );

    }
}
