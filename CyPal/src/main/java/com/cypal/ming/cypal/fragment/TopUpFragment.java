package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.activity.OrderDetailsActivity;
import com.cypal.ming.cypal.activity.TakeActivity;
import com.cypal.ming.cypal.adapter.SellDetailListAdapter;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.OrderModel;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * @Author luoming
 * @Date 2019/3/9 8:56 PM
 * 充值
 */

@SuppressLint("ValidFragment")
public class TopUpFragment extends BaseFragment implements OnClickListener, SellDetailListAdapter.OnClickListener {

    private RadioButton rb_new;//新订单
    private RadioButton rb_distribution;//配送中
    private RadioButton rb_complete;//已完成
    private RecyclerView recycleView;
    private SpringView springView;
    private SellDetailListAdapter sellDetailListAdapter;
    private int rstatus = 0;//状态{0:新订单, 1:配送中,2:完成}

    public TopUpFragment(Activity context) {
        super( context );
    }

    private RelativeLayout rl_layout;
    private TextView tv_number;
    private ArrayList<OrderModel> orderModels;
    private int pageNumber = 1;
    private boolean isxia = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_main, container, false );
        initView( view );
        return view;
    }

    private void initView(View view) {

        orderList();
        recycleView = (RecyclerView) view.findViewById( R.id.recycleView );
        springView = (SpringView) view.findViewById( R.id.springView );
        rl_layout = (RelativeLayout) view.findViewById( R.id.rl_layout );
        tv_number = (TextView) view.findViewById( R.id.tv_number );
        orderModels = new ArrayList<OrderModel>();
        sellDetailListAdapter = new SellDetailListAdapter( mcontext, orderModels, this );
        recycleView.setLayoutManager( new LinearLayoutManager( mcontext ) );
        recycleView.setAdapter( sellDetailListAdapter );
        rb_new.setChecked( true );
        springView.setHeader( new DefaultHeader( mcontext ) );
        springView.setFooter( new DefaultFooter( mcontext ) );
        springView.setListener( new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                orderList();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isxia) {
                    orderList();
                } else {
                    Tools.showToast( getActivity(), "没有更多数据了" );
                    springView.onFinishFreshAndLoad();
                }

            }
        } );
    }



    /* 获取设备列表 */

    public void orderList() {
        Map<String, String> map = new HashMap<>();
        map.put( "token", mSavePreferencesData.getStringData( "token" ) );
        map.put( "israpp", "1" );
        map.put( "rstatus", rstatus + "" );
        map.put( "page", pageNumber + "" );
        mQueue.add( ParamTools.packParam( Const.orderlist, this, this, map ) );
        loading();
    }

    @Override
    public void onResume() {
        orderList();
        super.onResume();
    }

    /**
     * @param order_uuid
     * @param action     操作类型{0:拒单,1:接单,2:取件,3:完成
     */
    public void update(String order_uuid, int action) {
        Map<String, String> map = new HashMap<>();
        map.put( "token", mSavePreferencesData.getStringData( "token" ) );
        map.put( "israpp", "1" );
        map.put( "order_uuid", order_uuid );
        map.put( "action", action + "" );//操作类型{0:拒单,1:接单,2:取件,3:完成
        mQueue.add( ParamTools.packParam( Const.update, this, this, map ) );
        loading();
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "status" );
            String msg = json.optString( "msg" );
            if (stauts == 0) {
                if (url.contains( Const.orderlist )) {
                    JSONObject jsonObject = json.optJSONObject( "data" );
                    String result = jsonObject.optString( "data" );
                    ArrayList<OrderModel> list = (ArrayList<OrderModel>) JSON.parseArray( result, OrderModel.class );
                    if (rstatus == 0) {
                        if (list.size() > 0) {
                            rl_layout.setVisibility( View.VISIBLE );
                            tv_number.setText( list.size() + "" );
                        } else {
                            rl_layout.setVisibility( View.GONE );
                        }
                    }
                    if (list.size() < 10) {
                        isxia = false;
                    } else {
                        isxia = true;
                    }
                    springView.onFinishFreshAndLoad();
                    showSellList( list );
                } else if (url.contains( Const.update )) {
                    //更新成功 刷新数据
                    orderList();
                }
            } else if (stauts == 403) {
                Tools.showToast( mcontext, "登录过期请重新登录" );
                mSavePreferencesData.putStringData( "token", "" );
                mSavePreferencesData.putStringData( "json", "" );
                Tools.jump( mcontext, LoginActivity.class, true );
            } else {
                Tools.showToast( mcontext, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast( mcontext, "发生错误,请重试!" );
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
            }

        }
    }

    private void showSellList(ArrayList<OrderModel> list) {
        if (orderModels.size() > 0 || list.size() > 0) {
            springView.setVisibility( View.VISIBLE );
            if (pageNumber != 1) {
                orderModels.addAll( list );
            } else {
                orderModels = list;
            }
            sellDetailListAdapter.updateAdapter( orderModels, rstatus );
        } else {
            springView.setVisibility( View.GONE );
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    private void deleteOrderDialog(final String text, final String order_uuid) {
        AlertDialog.Builder builder = new AlertDialog.Builder( mcontext );
        builder.setMessage( "您确定要" + text + "?" );
        builder.setTitle( text );
        builder.setPositiveButton( "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );

        builder.setNegativeButton( "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (text.equals( "接单" )) {
                    update( order_uuid, 1 );

                } else if (text.equals( "拒单" )) {
                    update( order_uuid, 0 );
                }

            }
        } );
        builder.create().show();
    }

    @Override
    public void ConfirmReceipt(String order_uuid) {

    }

    @Override
    public void Complaint(String order_uuid) {

    }
}
