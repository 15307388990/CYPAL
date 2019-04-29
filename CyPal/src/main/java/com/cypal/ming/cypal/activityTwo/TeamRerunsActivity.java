package com.cypal.ming.cypal.activityTwo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.BankList;
import com.cypal.ming.cypal.activity.WebviewActivity;
import com.cypal.ming.cypal.adapter.BaseClickListener;
import com.cypal.ming.cypal.adapter.BindingMoreTypeAdapter;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.adapter.WrapperLinearLayoutManager;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.dialogfrment.ConfirmPaymentDialog;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;
import com.cypal.ming.cypal.vm.BankItemEventHandler;
import com.cypal.ming.cypal.vm.OderDetailsItemVM;
import com.cypal.ming.cypal.vm.OderDetailsVM;
import com.cypal.ming.cypal.vm.TeamRerunsHeadVM;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoming
 * created at 2019/4/21 9:57 AM
 * 团队收益
 */
public class TeamRerunsActivity extends BaseActivity implements CategoryAdapter.OnClickListener, BaseClickListener, BankItemEventHandler {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private List<BindingAdapterItemType> list;
    private BindingMoreTypeAdapter bindingMoreTypeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_returns);
        initView();
    }

    /**
     * 团队收益银行卡
     */
    private void teamBankCard() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.teamRecharge, this, this, map, Request.Method.GET, this));
        loading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        teamBankCard();
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
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.teamRecharge)) {
            try {
                list.clear();
                JSONObject json = new JSONObject(data);
                String datebean = json.optString("data");
                TeamRerunsHeadVM teamRerunsHeadVM = new TeamRerunsHeadVM();
                if (!TextUtils.isEmpty(datebean) && !TextUtils.equals(datebean, "[]") && !TextUtils.equals(datebean, "[null]")) {
                    List<OderDetailsItemVM> itemVMS = JSON.parseArray(datebean, OderDetailsItemVM.class);
                    teamRerunsHeadVM.setWuVisibility(View.GONE);
                    list.add(teamRerunsHeadVM);

                    for (OderDetailsItemVM oderDetailsItemVM : itemVMS) {
                        if (oderDetailsItemVM.getAccountType().equals("BANKCARD")) {
                            oderDetailsItemVM.setViewType(R.layout.layout_details_banl);
                        } else {
                            oderDetailsItemVM.setViewType(R.layout.layout_details_pay);
                        }
                        list.add(oderDetailsItemVM);
                    }

                } else {
                    teamRerunsHeadVM.setWuVisibility(View.VISIBLE);
                    list.add(teamRerunsHeadVM);
                }

                bindingMoreTypeAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Tools.showToast(this, "数据格式不对");
            }
        }


    }

    @Override
    public void ConfirmReceipt(String value, String type) {
        Intent intent = new Intent(TeamRerunsActivity.this, BankList.class);
        intent.putExtra("value", value);
        intent.putExtra("type", type);
        startActivity(intent);

    }


    @Override
    public void copyName(OderDetailsItemVM viewModel) {
        ClipboardManager cmb = (ClipboardManager) TeamRerunsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(viewModel.getRealName());
        Tools.showToast(this, "复制成功");
    }

    @Override
    public void copyCard(OderDetailsItemVM viewModel) {
        ClipboardManager cmb = (ClipboardManager) TeamRerunsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(viewModel.getAccountData());
        Tools.showToast(this, "复制成功");

    }
}
