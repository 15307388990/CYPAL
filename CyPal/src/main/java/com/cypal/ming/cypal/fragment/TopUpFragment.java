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
import com.cypal.ming.cypal.adapter.TopUpListAdapter;
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
public class TopUpFragment extends BaseFragment implements OnClickListener, TopUpListAdapter.OnClickListener {

    private RecyclerView recycleView;
    private SpringView springView;
    private TopUpListAdapter topUpListAdapter;

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
        View view = inflater.inflate( R.layout.activity_top_up, container, false );
        initView( view );
        return view;
    }

    private void initView(View view) {

        recycleView = (RecyclerView) view.findViewById( R.id.recycleView );
        springView = (SpringView) view.findViewById( R.id.springView );
        rl_layout = (RelativeLayout) view.findViewById( R.id.rl_layout );
        orderModels = new ArrayList<OrderModel>();
        for (int i = 0; i < 6; i++) {
            OrderModel orderModel = new OrderModel();
            orderModels.add( orderModel );

        }
        topUpListAdapter = new TopUpListAdapter( mcontext, orderModels, this );
        recycleView.setLayoutManager( new LinearLayoutManager( mcontext ) );
        recycleView.setAdapter( topUpListAdapter );
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


    @Override
    public void onResume() {
        super.onResume();
    }

    public void orderList() {
//        Map<String, String> map = new HashMap<>();
//        map.put( "token", mSavePreferencesData.getStringData( "token" ) );
//        mQueue.add( ParamTools.packParam( Const.orderlist, this, this, map ) );
//        loading();

    }


    private void showSellList(ArrayList<OrderModel> list) {
        if (orderModels.size() > 0 || list.size() > 0) {
            springView.setVisibility( View.VISIBLE );
            if (pageNumber != 1) {
                orderModels.addAll( list );
            } else {
                orderModels = list;
            }
            topUpListAdapter.updateAdapter( orderModels );
        } else {
            springView.setVisibility( View.GONE );
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    @Override
    public void ConfirmReceipt(String order_uuid) {

    }

    @Override
    public void Complaint(String order_uuid) {

    }
}
