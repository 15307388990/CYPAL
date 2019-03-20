package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.SellDetailListAdapter;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.bean.OrderModel;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.AutoVerticalScrollTextView;
import com.cypal.ming.cypal.widgets.view.FullyLinearLayoutManager;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class MainFragment extends BaseFragment implements OnClickListener, SellDetailListAdapter.OnClickListener {

    private SpringView springView;
    private SellDetailListAdapter sellDetailListAdapter;
    private RecyclerView recycleView;
    private AutoVerticalScrollTextView auto_textview;
    private TextView tv_successratetext;
    private TextView tv_balance;

    public MainFragment(Activity context) {
        super( context );
    }

    private TextView tv_number;
    private ArrayList<OrderModel> orderModels;
    private int pageNumber = 1;
    private boolean isxia = true;
    private int number = 0;
    private List<IndexEntity.DataBean.NoticeListBean> noticeListBeanList;
    private boolean isRunning = true;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                auto_textview.next();
                number++;
                auto_textview.setText( noticeListBeanList.get( number % noticeListBeanList.size() ).getTitle() );
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_main, container, false );
        initView( view );
        return view;
    }


    private void initView(View view) {
        recycleView = (RecyclerView) view.findViewById( R.id.recycleView );
        springView = (SpringView) view.findViewById( R.id.springView );
        tv_number = (TextView) view.findViewById( R.id.tv_number );
        auto_textview = (AutoVerticalScrollTextView) view.findViewById( R.id.auto_textview );
        orderModels = new ArrayList<OrderModel>();
        for (int i = 0; i < 6; i++) {
            OrderModel orderModel = new OrderModel();
            orderModels.add( orderModel );

        }
        sellDetailListAdapter = new SellDetailListAdapter( mcontext, orderModels, this );
        View headView = LayoutInflater.from( mcontext ).inflate( R.layout.home_main_head, null );
        tv_successratetext = (TextView) headView.findViewById( R.id.tv_successratetext );
        tv_balance= (TextView) headView.findViewById( R.id.tv_balance );
        sellDetailListAdapter.setHeaderView( headView );
        recycleView.setAdapter( sellDetailListAdapter );
        recycleView.setLayoutManager( new LinearLayoutManager( mcontext ) );
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


    public void orderList() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.mallSetInfo, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
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
            sellDetailListAdapter.updateAdapter( orderModels );
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

    /**
     * 数据返回
     */
    @Override
    public void returnData(String data, String url) {
        IndexEntity indexEntity = JSON.parseObject( data, IndexEntity.class );
        noticeListBeanList = indexEntity.getData().getNoticeList();
        if (noticeListBeanList.size() > 0) {
            auto_textview.setText( noticeListBeanList.get( 0 ).getTitle() );
            new Thread() {
                @Override
                public void run() {
                    while (isRunning) {
                        SystemClock.sleep( 3000 );
                        handler.sendEmptyMessage( 199 );
                    }
                }
            }.start();

        }
        auto_textview.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        tv_successratetext.setText( indexEntity.getData().getSuccessRateText() );
        tv_balance.setText( "￥"+indexEntity.getData().getBalance() );
    }
}
