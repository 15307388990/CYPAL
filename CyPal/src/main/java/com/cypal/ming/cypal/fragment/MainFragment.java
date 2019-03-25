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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.AccountListActivity;
import com.cypal.ming.cypal.activity.GrabSingleActivity;
import com.cypal.ming.cypal.activity.SetPayPasswordOneActivity;
import com.cypal.ming.cypal.adapter.SellDetailListAdapter;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.bean.OrderModel;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.AutoVerticalScrollTextView;
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
    private TextView tv_todaySuccess;
    private TextView tv_todaySuccessMoney;
    private TextView tv_todayCommision;
    private ImageView iv_wexin;
    private ImageView iv_alipay;
    private ImageView iv_banl;
    private ImageView iv_yun;
    private LinearLayout ll_auto;
    private LinearLayout ll_hand;
    private LinearLayout ll_layout;
    private TextView tv_timer;
    private TextView tv_quxiao;
    private LinearLayout ll_timer;
    private TextView tv_qiang;
    private LinearLayout ll_account;

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
    private Thread thread;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                auto_textview.next();
                number++;
                auto_textview.setText( noticeListBeanList.get( number % noticeListBeanList.size() ).getTitle() );
            }
        }
    };
    private String method;
    private boolean isStar;

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
    }

    /**
     * 销毁线程方法
     */
    private void destroyThread() {
        try {
            if (null != thread && Thread.State.RUNNABLE == thread.getState()) {
                try {
                    Thread.sleep( 500 );
                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            thread = null;
        }
    }

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
        iv_wexin = (ImageView) headView.findViewById( R.id.iv_wexin );
        iv_alipay = (ImageView) headView.findViewById( R.id.iv_alipay );
        iv_banl = (ImageView) headView.findViewById( R.id.iv_banl );
        iv_yun = (ImageView) headView.findViewById( R.id.iv_yun );
        ll_auto = (LinearLayout) headView.findViewById( R.id.ll_auto );
        ll_hand = (LinearLayout) headView.findViewById( R.id.ll_hand );
        ll_layout = (LinearLayout) headView.findViewById( R.id.ll_layout );
        tv_timer = (TextView) headView.findViewById( R.id.tv_timer );
        tv_quxiao = (TextView) headView.findViewById( R.id.tv_quxiao );
        ll_timer = (LinearLayout) headView.findViewById( R.id.ll_timer );
        tv_successratetext = (TextView) headView.findViewById( R.id.tv_successratetext );
        tv_balance = (TextView) headView.findViewById( R.id.tv_balance );
        tv_todaySuccess = (TextView) headView.findViewById( R.id.tv_todaySuccess );
        ll_account = (LinearLayout) headView.findViewById( R.id.ll_account );
        tv_qiang = (TextView) headView.findViewById( R.id.tv_qiang );
        tv_todaySuccessMoney = (TextView) headView.findViewById( R.id.tv_todaySuccessMoney );
        tv_todayCommision = (TextView) headView.findViewById( R.id.tv_todayCommision );
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
        auto_textview.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        ll_auto.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                method = "AUTO";
                start();
            }
        } );
        ll_hand.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                method = "HAND";
                if (isStar) {
                    Tools.jump( mcontext, GrabSingleActivity.class, false );
                } else {
                    start();
                }

            }
        } );
        thread = new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    SystemClock.sleep( 5000 );
                    handler.sendEmptyMessage( 199 );
                }
            }
        };


        tv_quxiao.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消接单
                stop();
            }
        } );


        ll_account.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, AccountListActivity.class, false );
            }
        } );
    }


    public void orderList() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.mallSetInfo, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
    }

    /**
     * 开始接单
     */
    public void start() {
        Map<String, String> map = new HashMap<>();
        map.put( "method", method );
        mQueue.add( ParamTools.packParam( Const.start, mcontext, this, this, map ) );
        loading();
    }

    /**
     * 取消接单
     */
    public void stop() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.stop, mcontext, this, this, map ) );
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
    public void returnData(final String data, String url) {
        if (url.contains( Const.start )) {
            if (method.equals( "HAND" ) && !isStar) {
                Tools.jump( mcontext, GrabSingleActivity.class, false );
            }
            //接单成功刷新首页
            orderList();
        } else if (url.contains( Const.stop )) {
            orderList();
        } else {
            springView.onFinishFreshAndLoad();
            initData( data );
        }

    }

    private void initData(String data) {
        IndexEntity indexEntity = JSON.parseObject( data, IndexEntity.class );
        noticeListBeanList = indexEntity.getData().getNoticeList();
        if (!noticeListBeanList.isEmpty()) {
            if (!thread.isAlive()) {
                thread.start();
            }

        }

        tv_successratetext.setText( indexEntity.getData().getSuccessRateText() );
        tv_balance.setText( "￥" + indexEntity.getData().getBalance() );
        tv_todayCommision.setText( indexEntity.getData().getIndexTodayOrderAnalysisResp().getTodayCommision() + "" );
        tv_todaySuccess.setText( indexEntity.getData().getIndexTodayOrderAnalysisResp().getTodaySuccess() + "" );
        tv_todaySuccessMoney.setText( indexEntity.getData().getIndexTodayOrderAnalysisResp().getTodaySuccessMoney() + "" );
        String pay = indexEntity.getData().getUsedPayAccount();
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
        IndexEntity.DataBean.OtcBean otcBean = indexEntity.getData().getOtc();
        isStar = otcBean.isStart();
        if (otcBean.isStart()) {
            //开始接单
            if (otcBean.getOtcType().equals( "AUTO" )) {
                //自动接单
                ll_layout.setVisibility( View.GONE );
                ll_timer.setVisibility( View.VISIBLE );
                long time = indexEntity.getServerTime() - Tools.getLongformat( otcBean.getStartTime() );
                String timer = Tools.getDateString( time );
                tv_timer.setText( "已接单 " + timer );
            } else {
                //手动接单
                ll_layout.setVisibility( View.VISIBLE );
                ll_timer.setVisibility( View.GONE );
                tv_qiang.setText( "继续抢单" );
            }

        } else {
            //未开始接单
            ll_layout.setVisibility( View.VISIBLE );
            ll_timer.setVisibility( View.GONE );
            tv_qiang.setText( "手动抢单" );
        }
    }
}
