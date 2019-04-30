package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.OrderListState;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

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
    private SpringView springView;
    private int pageNumber = 1;
    boolean isPage = true;
    private LinearLayout ll_wu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNumber = 1;
        orderlist();

    }

    private void orderlist() {
        Map<String, String> map = new HashMap<>();
        map.put("isFinish", isFinish + "");
        if (orderListState != null) {
            map.put("statusEnum", orderListState + "");
        }
        map.put("page", pageNumber + "");
        map.put("size", "10");
        mQueue.add(ParamTools.packParam(Const.otcOrderlist, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        ll_wu = (LinearLayout) findViewById(R.id.ll_wu);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        otcOrderListAdapter = new OtcOrderListAdapter(OrderListActivity.this, list, this);
        recycleView.setAdapter(otcOrderListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        springView = (SpringView) findViewById(R.id.springView);
        rb_top_jin = (RadioButton) findViewById(R.id.rb_top_jin);
        rb_top_wancheng = (RadioButton) findViewById(R.id.rb_top_wancheng);
        rg_top = (RadioGroup) findViewById(R.id.rg_top);
        rd_group = (RadioGroup) findViewById(R.id.rd_group);
        cursor = (LinearLayout) findViewById(R.id.cursor);
        rd_wancheng = (RadioButton) findViewById(R.id.rd_wancheng);
        rd_quxiao = (RadioButton) findViewById(R.id.rd_quxiao);
        rg_top_2 = (RadioGroup) findViewById(R.id.rg_top_2);
        rg_top.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_top_jin) {
                    isFinish = false;
                } else if (checkedId == R.id.rb_top_wancheng) {
                    isFinish = true;

                }
                pageNumber = 1;
                initEvent();

            }
        });
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_all) {
                    orderListState = null;
                    params.leftMargin = 0;
                    cursor.setLayoutParams(params);
                } else if (checkedId == R.id.rd_wei) {
                    orderListState = OrderListState.A_PROCESS;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams(params);
                } else if (checkedId == R.id.rd_yi) {
                    orderListState = OrderListState.B_BEAPPEAL;
                    params.leftMargin = (int) cursorWidth * 2;
                    cursor.setLayoutParams(params);
                } else if (checkedId == R.id.rd_shen) {
                    orderListState = OrderListState.C_APPEAL;
                    params.leftMargin = (int) cursorWidth * 3;
                    cursor.setLayoutParams(params);
                }
                pageNumber = 1;
                orderlist();
            }
        });
        rg_top_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rd_wancheng) {
                    orderListState = OrderListState.D_SUCCESS;
                    params.leftMargin = 0;
                    cursor.setLayoutParams(params);
                } else if (checkedId == R.id.rd_quxiao) {
                    orderListState = OrderListState.E_FAIL;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams(params);
                }
                pageNumber = 1;
                orderlist();
            }
        });
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                orderlist();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isPage) {
                    orderlist();
                } else {
                    Tools.showToast(OrderListActivity.this, "没有更多数据了");
                    springView.onFinishFreshAndLoad();
                }

            }
        });
        initEvent();
    }

    public void initEvent() {
        params = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        if (!isFinish) {
            cursorWidth = params.width = Tools.getScreenWidth(OrderListActivity.this) / 4;
            cursor.setLayoutParams(params);
            rd_group.setVisibility(View.VISIBLE);
            rg_top_2.setVisibility(View.GONE);
            rd_group.check(R.id.rd_all);
            orderListState = null;

        } else {
            cursorWidth = params.width = Tools.getScreenWidth(OrderListActivity.this) / 2;
            cursor.setLayoutParams(params);
            rd_group.setVisibility(View.GONE);
            rg_top_2.setVisibility(View.VISIBLE);
            rg_top_2.check(R.id.rd_wancheng);
            orderListState = OrderListState.D_SUCCESS;
        }
        params.leftMargin = 0;
        cursor.setLayoutParams(params);
        pageNumber = 1;
        orderlist();

    }

    /**
     * 确认收款
     */
    public void confirm(String orderId, String payPasswrod) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        String paypass = MD5Util.getMD5String(payPasswrod);
        map.put("payPassword", paypass);
        mQueue.add(ParamTools.packParam(Const.confirm, this, this, this, map));
    }

    /**
     * 申诉订单
     */
    public void appeal(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        mQueue.add(ParamTools.packParam(Const.appeal, this, this, this, map));
        loading();
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.otcOrderlist)) {
            springView.onFinishFreshAndLoad();
            OtcOrderListEntity otcOrderListEntity = JSON.parseObject(data, OtcOrderListEntity.class);
            if (pageNumber == 1) {
                list = otcOrderListEntity.data.content;
                if (list.size() < 1) {
                    springView.setVisibility(View.GONE);
                    ll_wu.setVisibility(View.VISIBLE);
                } else {
                    ll_wu.setVisibility(View.GONE);
                    springView.setVisibility(View.VISIBLE);
                }
            } else {
                list.addAll(otcOrderListEntity.data.content);
            }

            otcOrderListAdapter.updateAdapter(list, otcOrderListEntity.serverTime);
            if (pageNumber < otcOrderListEntity.data.totalPages) {
                isPage = true;
            } else {
                isPage = false;
            }
        } else if (url.contains(Const.confirm)) {
            orderlist();
        } else if (url.contains(Const.appeal)) {
            orderlist();
        }else if (url.contains(Const.setPayPassword)) {
            Tools.showToast(this, "设置成功");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        }

    }

    /**
     * 设置支付密码
     */
    private void setPayPassword(String payPassword) {
        Map<String, String> map = new HashMap<>();
        map.put("payPassword", MD5Util.getMD5String(payPassword));
        mQueue.add(ParamTools.packParam(Const.setPayPassword, this, this, this, map));
        loading();
    }
    @Override
    public void ConfirmReceipt(String order_uuid) {
        //确认付款
        deleteOrderDialog("收款", order_uuid);
    }

    @Override
    public void Complaint(String order_uuid) {
        //申诉订单
        deleteOrderDialog("申诉订单", order_uuid);
    }

    private void deleteOrderDialog(final String text, final String order_uuid) {
        CancelTheDealDialog.newInstance().setTitle("温馨提示").setContext("您确定要" + text + "?").setQtext("取消").setOktext("确定")
                .setOnClickListener(new CancelTheDealDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        if (text.equals("收款")) {
                            inoutPsw(order_uuid);


                        } else if (text.equals("申诉订单")) {
                            appeal(order_uuid);
                        }
                    }
                }).show(this);
    }

    //打开输入密码的对话框
    public void inoutPsw(final String order_uuid) {
        final boolean hasPayPassword = mSavePreferencesData.getBooleanData("hasPayPassword");
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if (hasPayPassword) {
                        confirm(order_uuid, psw);
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
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }
}
