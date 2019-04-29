package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activityTwo.TeamRerunsActivity;
import com.cypal.ming.cypal.adapter.BaseClickListener;
import com.cypal.ming.cypal.adapter.BindingMoreTypeAdapter;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.adapter.WrapperLinearLayoutManager;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.dialogfrment.ConfirmPaymentDialog;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;
import com.cypal.ming.cypal.vm.BankItemEventHandler;
import com.cypal.ming.cypal.vm.OderDetailsItemVM;
import com.cypal.ming.cypal.vm.OderDetailsVM;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.shinichi.library.ImagePreview;

import static com.cypal.ming.cypal.config.Const.start;

/**
 * 充值详情
 */
public class TopUpDetailsActivity extends BaseActivity implements CategoryAdapter.OnClickListener, BaseClickListener, BankItemEventHandler {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private List<BindingAdapterItemType> list;
    private String orderId;
    private BindingMoreTypeAdapter bindingMoreTypeAdapter;
    private LinearLayout ll_huihua;
    private TextView tv_quxiao;
    private TextView tv_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_details);
        orderId = getIntent().getStringExtra("orderId");
        initView();
    }

    private void order() {

        if (!TextUtils.isEmpty(orderId)) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderId);
            mQueue.add(ParamTools.packParam(Const.order, this, this, map, Request.Method.GET, this));
            loading();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        order();
    }

    private void cancel() {
        if (!TextUtils.isEmpty(orderId)) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderId);
            mQueue.add(ParamTools.packParam(Const.cancel, this, this, this, map));
            loading();
        }
    }

    /**
     * 确认付款
     */
    private void rwconfirm(String paymentVoucher, String pwd) {
        if (!TextUtils.isEmpty(orderId)) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("paymentVoucher", paymentVoucher);
            map.put("remark", "dasd");
            String paypass = MD5Util.getMD5String(pwd);
            map.put("payPassword", paypass);
            mQueue.add(ParamTools.packParam(Const.rwconfirm, this, this, this, map));
            loading();
        }
    }

    /**
     * 申诉订单
     */
    public void save() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        mQueue.add(ParamTools.packParam(Const.service, this, this, this, map));
        loading();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        bindingMoreTypeAdapter = new BindingMoreTypeAdapter(list, this);
        recycleView.setLayoutManager(new WrapperLinearLayoutManager(this));
        recycleView.setAdapter(bindingMoreTypeAdapter);


        ll_huihua = (LinearLayout) findViewById(R.id.ll_huihua);
        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        ll_huihua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderId = getIntent().getStringExtra("orderId");
                String url = Const.BASE_URL + "/h5/chat?rwOrderId=" + orderId + "&token=" + mSavePreferencesData.getStringData("token");
                Intent intent = new Intent(TopUpDetailsActivity.this, WebviewActivity.class);
                intent.putExtra("link_url", url);
                intent.putExtra("link_name", "会话");
                startActivity(intent);
            }
        });

        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTheDealDialog.newInstance().setOnClickListener(new CancelTheDealDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        cancel();
                    }
                }).show(TopUpDetailsActivity.this);
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_ok.getText().toString().equals("申诉详情")) {
                } else if (tv_ok.getText().toString().equals("确认付款")) {
                    ConfirmPaymentDialog.newInstance("").setOnClickListener(new ConfirmPaymentDialog.OnClickListener() {
                        @Override
                        public void successful(String paymentVoucher) {
                            inoutPsw(paymentVoucher);
                        }
                    }).show(TopUpDetailsActivity.this);

                } else if (tv_ok.getText().toString().equals("申诉")) {
//                    Intent intent = new Intent( TopUpDetailsActivity.this, ComplaintOrderActivity.class );
//                    intent.putExtra( "orderId", orderId );
//                    startActivity( intent );
                    deleteOrderDialog();

                }
            }
        });
    }

    //打开输入密码的对话框
    public void inoutPsw(final String paymentVoucher) {
        final boolean hasPayPassword = mSavePreferencesData.getBooleanData("hasPayPassword");
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if (hasPayPassword) {
                        rwconfirm(paymentVoucher, psw);
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
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = this.getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
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
    protected void returnData(String data, String url) {
        if (url.contains(Const.order)) {
            try {
                list.clear();
                JSONObject json = new JSONObject(data);
                String datebean = json.optString("data");
                OderDetailsVM oderDetailsVM = JSON.parseObject(datebean, OderDetailsVM.class);
                initBootm(oderDetailsVM);
                list.add(oderDetailsVM);
                List<OderDetailsItemVM> itemVMS = JSON.parseArray(oderDetailsVM.getPayAccountJson(), OderDetailsItemVM.class);
                for (OderDetailsItemVM oderDetailsItemVM : itemVMS) {
                    if (oderDetailsItemVM.getAccountType().equals("BANKCARD")) {
                        oderDetailsItemVM.setViewType(R.layout.layout_details_banl);
                    } else {
                        oderDetailsItemVM.setViewType(R.layout.layout_details_pay);
                    }
                    list.add(oderDetailsItemVM);
                }


                bindingMoreTypeAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Tools.showToast(this, "数据格式不对");
            }
        } else if (url.contains(Const.cancel)) {
            order();
        } else if (url.contains(Const.rwconfirm)) {
            order();
        } else if (url.contains(Const.service)) {
            order();
        } else if (url.contains(Const.setPayPassword)) {
            Tools.showToast(this, "设置成功");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        }


    }

    @Override
    public void ConfirmReceipt(String value, String type) {
        Intent intent = new Intent(TopUpDetailsActivity.this, BankList.class);
        intent.putExtra("value", value);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    /**
     * 保存二维码
     */
    public void onSaveBitmap(String qrcodeSrc) {
        Tools.onSaveBitmap(CodeUtils.createImage(qrcodeSrc, 150, 150, null), TopUpDetailsActivity.this);
        Tools.showToast(this, "二维码已保存到系统图库");
    }

    /**
     * 查看凭证
     */
    public void onStartImg(String paymentVoucher) {
        ImagePreview
                .getInstance()
                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                .setContext(TopUpDetailsActivity.this)

                // 设置从第几张开始看（索引从0开始）
                // .setIndex(0)

                //=================================================================================================
                // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                // 1：第一步生成的imageInfo List
                //.setImageInfoList(imageInfoList)

                // 2：直接传url List
                //.setImageList(List<String> imageList)

                // 3：只有一张图片的情况，可以直接传入这张图片的url
                .setImage(paymentVoucher)
                //=================================================================================================

                // 开启预览
                .start();
    }

    /**
     * 初始化底部按钮
     */
    private void initBootm(OderDetailsVM oderDetailsVM) {
        String state = oderDetailsVM.getStatusEnum();
        ll_huihua.setVisibility(View.GONE);
        tv_ok.setVisibility(View.GONE);
        tv_quxiao.setVisibility(View.GONE);
        if (state.equals(TopUpState.TRADING.toString())) {
            ll_huihua.setVisibility(View.VISIBLE);
            tv_ok.setVisibility(View.VISIBLE);
            tv_quxiao.setVisibility(View.VISIBLE);
        } else if (state.equals(TopUpState.CANCEL.toString())) {
            ll_huihua.setVisibility(View.VISIBLE);
        } else if (state.equals(TopUpState.SERVICE.toString())) {
            ll_huihua.setVisibility(View.VISIBLE);
//            tv_ok.setVisibility( View.VISIBLE );
//            tv_ok.setText( "申诉详情" );
        } else if (state.equals(TopUpState.SUCCESS.toString())) {
            ll_huihua.setVisibility(View.VISIBLE);
        } else {
            ll_huihua.setVisibility(View.VISIBLE);
            tv_ok.setVisibility(View.VISIBLE);
            tv_ok.setText("申诉");
        }
    }

    private void deleteOrderDialog() {
        CancelTheDealDialog.newInstance().setTitle("温馨提示").setContext("确定要申请客服介入").setQtext("取消").setOktext("确定")
                .setOnClickListener(new CancelTheDealDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        save();
                    }
                }).show(this);
    }

    @Override
    public void copyName(OderDetailsItemVM viewModel) {
        ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(viewModel.getRealName());
        Tools.showToast(this, "复制成功");
    }

    @Override
    public void copyCard(OderDetailsItemVM viewModel) {
        ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(viewModel.getAccountData());
        Tools.showToast(this, "复制成功");

    }
}
