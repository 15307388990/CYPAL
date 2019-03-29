package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.cypal.ming.cypal.adapter.OtcOrderListAdapter;
import com.cypal.ming.cypal.adapter.TopUpRecordListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.OtcOrderListEntity;
import com.cypal.ming.cypal.bean.TopUpListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.OrderListState;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接单记录列表
 */
public class OrderListActivity extends BaseActivity implements OtcOrderListAdapter.OnClickListener {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private OtcOrderListAdapter otcOrderListAdapter;
    private List<OtcOrderListEntity.DataBean.ContentBean> list;
    private LinearLayout.LayoutParams params;
    private int cursorWidth;
    private int currentSelectTab;
    private RadioButton rb_top_jin;
    private RadioButton rb_top_wancheng;
    private RadioGroup rg_top;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private boolean isFinish = false;
    private OrderListState orderListState;
    private RadioButton rd_wancheng;
    private RadioButton rd_quxiao;
    private RadioGroup rg_top_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_list );
        initView();
    }

    private void orderlist() {
        Map<String, String> map = new HashMap<>();
        map.put( "isFinish", isFinish + "" );
        if (orderListState != null) {
            map.put( "statusEnum", orderListState + "" );
        }
        mQueue.add( ParamTools.packParam( Const.otcOrderlist, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
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
        otcOrderListAdapter = new OtcOrderListAdapter( OrderListActivity.this, list, this );
        recycleView.setAdapter( otcOrderListAdapter );
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
                    isFinish = false;
                } else if (checkedId == R.id.rb_top_wancheng) {
                    isFinish = true;

                }
                initEvent();

            }
        } );
        rd_group.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_all) {
                    orderListState = null;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_wei) {
                    orderListState = OrderListState.A_PROCESS;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_yi) {
                    orderListState = OrderListState.B_BEAPPEAL;
                    params.leftMargin = (int) cursorWidth * 2;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_shen) {
                    orderListState = OrderListState.C_APPEAL;
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
                    orderListState = OrderListState.D_SUCCESS;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_quxiao) {
                    orderListState = OrderListState.E_FAIL;
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
        if (!isFinish) {
            cursorWidth = params.width = Tools.getScreenWidth( OrderListActivity.this ) / 4;
            cursor.setLayoutParams( params );
            rd_group.setVisibility( View.VISIBLE );
            rg_top_2.setVisibility( View.GONE );
            rd_group.check( R.id.rd_all );
            orderListState = null;

        } else {
            cursorWidth = params.width = Tools.getScreenWidth( OrderListActivity.this ) / 2;
            cursor.setLayoutParams( params );
            rd_group.setVisibility( View.GONE );
            rg_top_2.setVisibility( View.VISIBLE );
            rg_top_2.check( R.id.rd_wancheng );
            orderListState = OrderListState.D_SUCCESS;
        }
        params.leftMargin = 0;
        cursor.setLayoutParams( params );
        orderlist();

    }

    /**
     * 确认收款
     */
    public void confirm(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put( "orderId", orderId );
        mQueue.add( ParamTools.packParam( Const.confirm, this, this, this, map ) );
        loading();
    }

    /**
     * 申诉订单
     */
    public void appeal(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put( "orderId", orderId );
        mQueue.add( ParamTools.packParam( Const.service, this, this, this, map ) );
        loading();
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains( Const.otcOrderlist )) {
            OtcOrderListEntity otcOrderListEntity = JSON.parseObject( data, OtcOrderListEntity.class );
            list = otcOrderListEntity.data.content;
            otcOrderListAdapter.updateAdapter( list );
        } else if (url.contains( Const.confirm )) {
            orderlist();
        } else if (url.contains( Const.service )) {
            orderlist();
        }

    }

    @Override
    public void ConfirmReceipt(String order_uuid) {
        //确认付款
        deleteOrderDialog( "收款", order_uuid );
    }

    @Override
    public void Complaint(String order_uuid) {
        //申诉订单
        deleteOrderDialog( "申诉订单", order_uuid );
    }

    private void deleteOrderDialog(final String text, final String order_uuid) {
        AlertDialog.Builder builder = new AlertDialog.Builder( OrderListActivity.this );
        builder.setMessage( "您确定要" + text + "?" );
        builder.setTitle( "温馨提示" );
        builder.setPositiveButton( "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );

        builder.setNegativeButton( "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (text.equals( "收款" )) {
                    confirm( order_uuid );

                } else if (text.equals( "申诉订单" )) {
                    appeal( order_uuid );
                }

            }
        } );
        builder.create().show();
    }
}
