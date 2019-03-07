package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.FinanceWithdrawBean;
import com.fmt.ming.paotui.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 打款详情
 */
public class MoneyDetailsActivity extends BaseActivity {


    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_staus)
    TextView tv_staus;
    @Bind(R.id.tv_way)
    TextView tvWay;
    @Bind(R.id.tv_card)
    TextView tvCard;
    @Bind(R.id.tv_timer)
    TextView tvTimer;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_note)
    TextView tvNote;
    private FinanceWithdrawBean moneyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_details);
        ButterKnife.bind(this);
        initTitle();
        title.setText("详情");
        initDate();
    }

    private void initDate() {
        moneyModel = (FinanceWithdrawBean) getIntent().getSerializableExtra("moneyModel");
        tvMoney.setText("+" + Tools.getFenYuan(moneyModel.getWithdraw_price()));
        if (moneyModel.getWithdraw_stauts() == 1)//1； 提现中 2；提现成功 -1提现失败"
        {
            tv_staus.setText("待打款");
        } else if (moneyModel.getWithdraw_stauts() == 2) {
            tv_staus.setText("已打款");
        } else {
            tv_staus.setText("打款失败");
        }
        tvWay.setText(moneyModel.getWithdraw_type_info());
        tvCard.setText(Tools.pCardNumber(moneyModel.getWithdraw_access()));
        if (moneyModel.getWithdraw_number() == null || moneyModel.getWithdraw_number().equals("")) {


            tvTimer.setText("/");
            tvNumber.setText("/");
            tvNote.setText("/");
        } else {
            tvTimer.setText(Tools.getDateformat2(Long.parseLong(moneyModel
                    .getWithdraw_date())));
            tvNumber.setText(moneyModel.getWithdraw_number());
            tvNote.setText(moneyModel.getWithdraw_info());
        }

    }


    @Override
    public void onResponse(String response, String url) {
        // admin_agency: 341,
        // status: 0,
        // auth_token: "d8deb043-30a0-4a95-8d7d-2d653c56fe7f",
        // admin_id: 347,
        // agency_name: "罗富贵小吃店",
        // msg: "登录成功"
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }


}
