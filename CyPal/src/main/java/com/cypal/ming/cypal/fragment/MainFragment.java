package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.*;
import com.cypal.ming.cypal.adapter.SellDetailListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.BaseEntity;
import com.cypal.ming.cypal.bean.ContentEntity;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.bean.VersionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.AccountDialog;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.dialogfrment.VersionUpgradeDialog;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.MessageEnum;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.AutoVerticalScrollTextView;
import com.cypal.ming.cypal.vm.IWsManager;
import com.cypal.ming.cypal.ws.WsManager;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

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
    private LinearLayout ll_wu;
    private LinearLayout ll_order;
    private RelativeLayout ll_view_back;//消息中心
    private String pay;//收款账号 字符串
    private TextView top_view_text;

    public MainFragment(Activity context) {
        super(context);
    }

    public MainFragment() {
    }

    private TextView tv_number;
    private List<IndexEntity.DataBean.UndoOrderBean.ContentBean> orderModels;
    private int pageNumber = 1;
    private boolean isxia = true;
    private int number = 0;
    private List<IndexEntity.DataBean.NoticeListBean> noticeListBeanList;
    private boolean isRunning = true;
    private Thread thread;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                if (noticeListBeanList != null) {
                    if (!noticeListBeanList.isEmpty()) {
                        auto_textview.next();
                        number++;
                        auto_textview.setText(noticeListBeanList.get(number % noticeListBeanList.size()).title);
                    }
                }
            } else if (msg.what == 2) {
                Tools.isOnline = false;
                setText();

            } else if (msg.what == 3) {
                Tools.isOnline = true;
                setText();
            } else if (msg.what == 1) {
                String text = (String) msg.obj;
                if (!TextUtils.isEmpty(text)) {
                    try {
                        ManagerEntity managerEntity = JSON.parseObject(text, ManagerEntity.class);
                        Log.d("WsManager", "接收开始判断类型");
                        if (!managerEntity.messageEnum.equals(MessageEnum.OTCHAND.toString())) {
                            Log.d("WsManager", "通知");
                            iv_round_red.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Tools.showToast(mcontext, "出现异常，请重新登录");
                    }
                }
            }


        }
    };
    private String method;
    private boolean isStar;
    private VersionUpgradeDialog versionUpgradeDialog;
    private ImageView iv_round_red;


    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 销毁线程方法
     */
    private void destroyThread() {
        try {
            if (null != thread && Thread.State.RUNNABLE == thread.getState()) {
                try {
                    Thread.sleep(500);
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

    /**
     * 设置首页文字
     */
    private void setText() {
        if (Tools.isOnline) {
            top_view_text.setText("首页");
        } else {
            top_view_text.setText("首页（离线）");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        //  WsManager.getInstance().setmListener(this);
        initView(view);
        return view;
    }


    private void initView(View view) {
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        springView = (SpringView) view.findViewById(R.id.springView);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        ll_view_back = (RelativeLayout) view.findViewById(R.id.ll_view_back);
        iv_round_red = (ImageView) view.findViewById(R.id.iv_round_red);
        top_view_text = (TextView) view.findViewById(R.id.top_view_text);
        auto_textview = (AutoVerticalScrollTextView) view.findViewById(R.id.auto_textview);
        orderModels = new ArrayList<IndexEntity.DataBean.UndoOrderBean.ContentBean>();
        sellDetailListAdapter = new SellDetailListAdapter(mcontext, orderModels, this);
        View headView = LayoutInflater.from(mcontext).inflate(R.layout.home_main_head, null);
        iv_wexin = (ImageView) headView.findViewById(R.id.iv_wexin);
        iv_alipay = (ImageView) headView.findViewById(R.id.iv_alipay);
        iv_banl = (ImageView) headView.findViewById(R.id.iv_banl);
        iv_yun = (ImageView) headView.findViewById(R.id.iv_yun);
        ll_auto = (LinearLayout) headView.findViewById(R.id.ll_auto);
        ll_hand = (LinearLayout) headView.findViewById(R.id.ll_hand);
        ll_layout = (LinearLayout) headView.findViewById(R.id.ll_layout);
        ll_order = (LinearLayout) headView.findViewById(R.id.ll_order);
        tv_timer = (TextView) headView.findViewById(R.id.tv_timer);
        tv_quxiao = (TextView) headView.findViewById(R.id.tv_quxiao);
        ll_timer = (LinearLayout) headView.findViewById(R.id.ll_timer);
        ll_wu = (LinearLayout) headView.findViewById(R.id.ll_wu);
        tv_successratetext = (TextView) headView.findViewById(R.id.tv_successratetext);
        tv_balance = (TextView) headView.findViewById(R.id.tv_balance);
        tv_todaySuccess = (TextView) headView.findViewById(R.id.tv_todaySuccess);
        ll_account = (LinearLayout) headView.findViewById(R.id.ll_account);
        tv_qiang = (TextView) headView.findViewById(R.id.tv_qiang);
        tv_todaySuccessMoney = (TextView) headView.findViewById(R.id.tv_todaySuccessMoney);
        tv_todayCommision = (TextView) headView.findViewById(R.id.tv_todayCommision);
        sellDetailListAdapter.setHeaderView(headView);
        recycleView.setAdapter(sellDetailListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(mcontext));
        springView.setHeader(new DefaultHeader(mcontext));
        springView.setFooter(new DefaultFooter(mcontext));
        setText();

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                orderList();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
//                pageNumber++;
//                if (isxia) {
//                    orderList();
//                } else {
//                    Tools.showToast( getActivity(), "没有更多数据了" );
//                    springView.onFinishFreshAndLoad();
//                }

            }
        });
        /**
         * 消息中心
         */
        ll_view_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_round_red.setVisibility(View.GONE);
                Tools.jump(mcontext, MeassActivity.class, false);

            }
        });
        auto_textview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tools.showToast( mcontext, strings[number % strings.length] );
                if (noticeListBeanList.size() > 0) {
                    String url = Const.BASE_URL + "/h5/notice/" + noticeListBeanList.get(number % noticeListBeanList.size()).id;
                    Intent intent = new Intent(mcontext, WebviewActivity.class);
                    intent.putExtra("link_url", url);
                    intent.putExtra("link_name", "消息详情");
                    startActivity(intent);
                }

            }
        });
        ll_auto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                method = "AUTO";
                inoutPsw(null);
            }
        });


        ll_hand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                method = "HAND";
                if (isStar) {
                    Tools.jump(mcontext, GrabSingleActivity.class, false);
                } else {
                    inoutPsw(null);
                }

            }
        });


        tv_quxiao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消接单
                stop();
            }
        });


        ll_account.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pay) && !"[]".equals(pay)) {
                    AccountDialog.newInstance().setOnClickListener(new AccountDialog.OnClickListener() {
                        @Override
                        public void successful() {
                            //设置成功刷新数据
                            orderList();
                        }
                    }).show(mcontext);
                } else {
                    Tools.jump(mcontext, AccountListActivity.class, false);
                }
            }
        });
        if (!TextUtils.isEmpty(mSavePreferencesData.getStringData("indexjson"))) {
            IndexEntity indexEntity = JSON.parseObject(mSavePreferencesData.getStringData("indexjson"), IndexEntity.class);
            initData(indexEntity);
        }
        /**
         * 跳转至接单记录
         */
        ll_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, OrderListActivity.class, false);
            }
        });
    }


    public void orderList() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.mallSetInfo, this, this, map, Request.Method.GET, mcontext));
    }

    /**
     * 开始接单
     */
    public void start(String payPasswrod) {
        Map<String, String> map = new HashMap<>();
        map.put("method", method);
        String paypass = MD5Util.getMD5String(payPasswrod);
        map.put("payPassword", paypass);
        mQueue.add(ParamTools.packParam(Const.start, mcontext, this, this, map));
        loading();
    }

    /**
     * 取消接单
     */
    public void stop() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.stop, mcontext, this, this, map));
        loading();
    }


    /**
     * 确认收款
     */
    public void confirm(String orderId, String payPasswrod) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        String paypass = MD5Util.getMD5String(payPasswrod);
        map.put("payPassword", paypass);
        mQueue.add(ParamTools.packParam(Const.confirm, mcontext, this, this, map));
    }

    /**
     * 申诉订单
     */
    public void appeal(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        mQueue.add(ParamTools.packParam(Const.appeal, mcontext, this, this, map));
        loading();
    }

    @Override
    public void onResume() {
        orderList();
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
            }

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    @Override
    public void ConfirmReceipt(String order_uuid, String amount) {
        deleteOrderDialog("收款" + amount, order_uuid);
    }

    @Override
    public void Complaint(String order_uuid) {
        deleteOrderDialog("申诉订单", order_uuid);
    }

    private void deleteOrderDialog(final String text, final String order_uuid) {
        CancelTheDealDialog.newInstance().setTitle("温馨提示").setContext("您确定要" + text + "?").setQtext("取消").setOktext("确定")
                .setOnClickListener(new CancelTheDealDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        if (text.equals("申诉订单")) {
                            appeal(order_uuid);
                        } else {
                            inoutPsw(order_uuid);
                        }
                    }
                }).show(mcontext);
    }

    /**
     * 数据返回
     */
    @Override
    public void returnData(String data, String url) {
        if (url.contains(Const.start)) {
            if (method.equals("HAND") && !isStar) {
                Tools.jump(mcontext, GrabSingleActivity.class, false);
            } else {
                //接单成功刷新首页
                orderList();
            }

        } else if (url.contains(Const.stop)) {
            orderList();
        } else if (url.contains(Const.mallSetInfo)) {
            springView.onFinishFreshAndLoad();
            mSavePreferencesData.putStringData("indexjson", data);
            IndexEntity indexEntity = JSON.parseObject(data, IndexEntity.class);
            VersionUpdate(indexEntity);
            initData(indexEntity);
        } else if (url.contains(Const.confirm)) {
            orderList();
        } else if (url.contains(Const.appeal)) {
            orderList();
        } else if (url.contains(Const.setPayPassword)) {
            Tools.showToast(mcontext, "设置成功");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        }

    }


    /**
     * 设置支付密码
     */
    private void setPayPassword(String payPassword) {
        Map<String, String> map = new HashMap<>();
        map.put("payPassword", MD5Util.getMD5String(payPassword));
        mQueue.add(ParamTools.packParam(Const.setPayPassword, mcontext, this, this, map));
        loading();
    }


    //打开输入密码的对话框
    public void inoutPsw(final String order_uuid) {
        final boolean hasPayPassword = mSavePreferencesData.getBooleanData("hasPayPassword");
        SelectPopupWindow menuWindow = new SelectPopupWindow(mcontext, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if (hasPayPassword) {
                        if (TextUtils.isEmpty(order_uuid)) {
                            start(psw);
                        } else {
                            confirm(order_uuid, psw);
                        }


                    } else {
                        setPayPassword(psw);
                    }
                }
            }
        });
        if (!hasPayPassword) {
            menuWindow.setTitle("设置支付密码");
        }
        Rect rect = new Rect();
        mcontext.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = mcontext.getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(mcontext.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }

    @Override
    public void onDestroy() {
        if (versionUpgradeDialog != null) {
            if (versionUpgradeDialog.getIshow()) {
                versionUpgradeDialog.dismissAllowingStateLoss();
            }
        }
        if (thread != null) {
            thread = null;
        }
        super.onDestroy();

    }

    @Override
    protected void returnMsg(String data, String url) {
        if (url.contains(Const.start)) {
            //接单接口返回-2，跳转到认证会员页
            BaseEntity entity = JSON.parseObject(data, BaseEntity.class);
            if (entity.code == -2) {
                Tools.jump(mcontext, MemberActivity.class, false);
            }
        }
    }

    private void initData(IndexEntity indexEntity) {

        noticeListBeanList = indexEntity.data.noticeList;
        tv_successratetext.setText(indexEntity.data.successRateText);
        tv_balance.setText("￥" + indexEntity.data.balance);
        tv_todayCommision.setText(indexEntity.data.indexTodayOrderAnalysisResp.todayCommision + "");
        tv_todaySuccess.setText(indexEntity.data.indexTodayOrderAnalysisResp.todaySuccess + "");
        tv_todaySuccessMoney.setText(indexEntity.data.indexTodayOrderAnalysisResp.todaySuccessMoney + "");
        pay = indexEntity.data.usedPayAccount;
        iv_wexin.setVisibility(View.GONE);
        iv_alipay.setVisibility(View.GONE);
        iv_yun.setVisibility(View.GONE);
        iv_banl.setVisibility(View.GONE);
        if (pay.contains("WXPAY")) {
            iv_wexin.setVisibility(View.VISIBLE);
        }
        if (pay.contains("ALIPAY")) {
            iv_alipay.setVisibility(View.VISIBLE);
        }
        if (pay.contains("CLOUDPAY")) {
            iv_yun.setVisibility(View.VISIBLE);
        }
        if (pay.contains("BANKCARD")) {
            iv_banl.setVisibility(View.VISIBLE);
        }
        IndexEntity.DataBean.OtcBean otcBean = indexEntity.data.otc;
        isStar = otcBean.start;
        if (otcBean.start) {
            //开始接单
            if (otcBean.otcType.equals("AUTO")) {
                //自动接单
                ll_layout.setVisibility(View.GONE);
                ll_timer.setVisibility(View.VISIBLE);
                long time = indexEntity.serverTime - Tools.getLongformat(otcBean.startTime);
                String timer = Tools.getDateString(time);
                tv_timer.setText("已接单 " + timer);
            } else {
                //手动接单
                ll_layout.setVisibility(View.VISIBLE);
                ll_timer.setVisibility(View.GONE);
                tv_qiang.setText("继续抢单");
            }

        } else {
            //未开始接单
            ll_layout.setVisibility(View.VISIBLE);
            ll_timer.setVisibility(View.GONE);
            tv_qiang.setText("手动抢单");
        }
        orderModels = indexEntity.data.undoOrder.content;
        sellDetailListAdapter.updateAdapter(orderModels, indexEntity.serverTime);
        if (orderModels.size() > 0) {
            ll_wu.setVisibility(View.GONE);
        } else {
            ll_wu.setVisibility(View.VISIBLE);
        }

        //公告开启
        if (thread == null) {
            if (noticeListBeanList != null) {
                if (!noticeListBeanList.isEmpty()) {
                    auto_textview.setText(noticeListBeanList.get(number % noticeListBeanList.size()).title);
                    thread = new Thread() {
                        @Override
                        public void run() {
                            while (isRunning) {
                                SystemClock.sleep(5000);
                                handler.sendEmptyMessage(199);
                            }
                        }
                    };
                    if (!thread.isAlive()) {
                        thread.start();
                    }
                }
            }
        }
        //支付密码
        mSavePreferencesData.putBooleanData("hasPayPassword", indexEntity.data.hasPayPassword);
    }

    /**
     * 版本更新
     */
    private void VersionUpdate(IndexEntity indexEntity) {
        if (indexEntity.data.version != null) {
            Tools.isRound = true;
            if (indexEntity.data.version.updateType != -1) {
                VersionEntity versionBean = new VersionEntity();
                if (versionUpgradeDialog != null) {
                    //如果弹框不为空
                    if (versionUpgradeDialog.getIshow()) {
                        versionUpgradeDialog.dismissAllowingStateLoss();
                    }
                    versionUpgradeDialog = null;
                }
                versionUpgradeDialog = VersionUpgradeDialog.newInstance(versionBean.getData(indexEntity.data.version));
                if (!versionUpgradeDialog.getIshow()) {
                    long quxiaotime = System.currentTimeMillis();
                    if (mSavePreferencesData.getLongData("quxiaotime", 0) == 0 || indexEntity.data.version.updateType == 1) {
                        versionUpgradeDialog.show(mcontext);
                    } else {
                        if (mSavePreferencesData.getLongData("quxiaotime", 0) - quxiaotime > 24 * 60 * 1000) {
                            versionUpgradeDialog.show(mcontext);
                        }
                    }
                }
            }
        }
    }


    /**
     * 断开连接
     */
    public void onDisconnected() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(2);
            }
        }.start();

    }

    /**
     * 连接成功
     */
    public void onConnected() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 有消息
     */
    public void onTextMessage(final String text) {
        new Thread() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = text;
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }
}
