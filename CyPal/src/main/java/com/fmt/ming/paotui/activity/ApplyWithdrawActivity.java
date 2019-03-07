package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.CarshAccountBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：ApplyWithdrawActivity 类描述： 我要提现 首页 创建人：罗富贵 创建时间：2016年5月11日
 */
public class ApplyWithdrawActivity extends BaseActivity {

    @ViewInject(R.id.tv_kahao)
    private TextView tv_kahao;// 卡号 或支付宝账号
    @ViewInject(R.id.tv_kahao_name)
    private TextView tv_kahao_name;// 卡号 或支付宝账号
    @ViewInject(R.id.ll_kaohao)
    private LinearLayout ll_kaohao;
    @ViewInject(R.id.tv_can_withdraw_price)
    private TextView tv_can_withdraw_price;// 可提现金额
    @ViewInject(R.id.et_price)
    private EditText et_price;// 提现金额
    @ViewInject(R.id.et_beizhu)
    private EditText et_beizhu;// 提现备注
    @ViewInject(R.id.tv_all)
    private TextView tv_all;// 全部提现
    @ViewInject(R.id.iv_arrw)
    private TextView iv_arrw;
    @ViewInject(R.id.btn_tixian)
    private Button btn_tixian;// 提现
    private int sum;
    private PopupWindow popWindow;
    private int account_id = 0;// 提现账户
    private boolean isXin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applywithdraw);
        ViewUtils.inject(this);
        initTitle();
        title.setText("我要提现");
        initValues();
        tv_can_withdraw_price.setText("(可提现金额：" + getIntent().getStringExtra("sum") + ")");

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (isXin) {
            accountList();
        }

    }

    private void initValues() {
        et_price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (et_price.getText().length() >= 1 && Double.valueOf(et_price.getText().toString()) > 0) {
                    btn_tixian.setBackgroundResource(R.drawable.bt);
                    btn_tixian.setTextColor(getResources().getColor(android.R.color.white));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        obtainDataWithdrawList();
        loading();

    }

    /**
     * 单击事件
     */
    @OnClick({R.id.tv_all, R.id.ll_kaohao, R.id.btn_tixian})
    public void clickMethod(View v) {
        if (v.getId() == R.id.tv_all) {
            et_price.setText(Tools.getFenYuan(sum));
        } else if (v.getId() == R.id.btn_tixian) {
            if (et_price.getText().length() >= 1) {
                double price = Double.parseDouble(et_price.getText().toString());
                if (price > 0) {
                    // 提现金额小于可提现金额
                    if (price * 100 <= sum) {
                        if (account_id == 0) {
                            Tools.showToast(getApplicationContext(), "未选择提现账户");
                            return;
                        }

                        Intent intent = new Intent(ApplyWithdrawActivity.this, VerifyPaymentPasswordActivity.class);
                        startActivityForResult(intent, 1);

                    } else {
                        Toast.makeText(getApplicationContext(), "账户余额不足", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Tools.showToast(getApplicationContext(), "提现金额必须要大于0");
                }
            }
        } else if (v.getId() == R.id.ll_kaohao) {
            Intent intent = new Intent(getApplicationContext(), CashAccountListActivity.class);
            intent.putExtra("type", 1);
            startActivityForResult(intent, 2);
        }
    }

    /**
     * 账户列表
     */
    private void accountList() {
        Map<String, String> map = new HashMap<String, String>();
        // auth_token 登录令牌
        // agency_id 机构id
        map.put("auth_token", mSavePreferencesData.getStringData("auth_token"));
        mQueue.add(ParamTools.packParam(Const.accountList, this, this, map));
        loading();
    }

    private void applyWithdraw(String pay_pass_token) {
        Map<String, String> map = new HashMap<String, String>();
        // agency_id 机构id
        // withdraw_price 提现金额
        // withdraw_leave_message 留言
        // account_id 提现账号id
        // auth_token 必输项
        // pay_pass_token 支付密码验证成功后的token
        map.put("auth_token", partnerBean.getAuth_token());
        map.put("pay_pass", pay_pass_token);
        map.put("price", Tools.getYuanFen(et_price.getText().toString()) + "");
        // map.put("withdraw.withdraw_name", aFinancebean.getWithdrawal_name());
        map.put("account_id", account_id + "");
        map.put("info_message", et_beizhu.getText().toString());
        mQueue.add(ParamTools.packParam(Const.applyWithdraw, this, this, map));
        loading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                applyWithdraw(data.getStringExtra("pay_pass"));
            } else if (requestCode == 2) {
                int withdraw_type = data.getIntExtra("withdraw_type", 0);
                account_id = data.getIntExtra("account_id", 0);
                isXin = false;
                if (withdraw_type == 1) {
                    tv_kahao.setText(data.getStringExtra("number"));
                    tv_kahao_name.setText("(支付宝)");
                } else if (withdraw_type == 2) {
                    tv_kahao.setText(data.getStringExtra("number"));
                    tv_kahao_name.setText("(银行卡)");
                }
            }

        }
    }

    /* 获取可提现金额数据 */
    public void obtainDataWithdrawList() {
        Map<String, String> map = new HashMap<String, String>();
        // agency_id int 机构id
        // auth_token String 登陆令牌
        // admin_id int 管理员id
        map.put("auth_token", partnerBean.getAuth_token());
        map.put("withdraw_stauts", "0");
        map.put("pageNum", 1 + "");
        map.put("pageSize", 1000 + "");
        mQueue.add(ParamTools.packParam(Const.withdrawList, this, this, map));
        loading();
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                if (url.contains(Const.applyWithdraw)) {
                    Tools.jump(this, SucceedWithdrawAccessActivity.class, false);
                    this.finish();
                } else if (url.contains(Const.accountList)) {
                    String accountlist = json.getString("result");
                    List<CarshAccountBean> CarshAccountBeans = JSON.parseArray(accountlist, CarshAccountBean.class);
                    if (CarshAccountBeans.size() > 0) {
                        tv_kahao.setText(CarshAccountBeans.get(0).getAccount_card());
                        account_id = CarshAccountBeans.get(0).getId();
                        if (CarshAccountBeans.get(0).getAccount_type() == 1) {
                            tv_kahao_name.setText("(支付宝)");
                        } else {
                            tv_kahao_name.setText("(银行卡)");
                        }
                    } else {
                        tv_kahao.setText("暂无账户");
                        tv_kahao_name.setText("");
                    }
                } else if (url.contains(Const.withdrawList)) {
                    JSONObject jsonObject = json.optJSONObject("result");
                    sum = jsonObject.optInt("can_withdraw_price");
                    tv_can_withdraw_price.setText("(可提现金额：" + Tools.getFenYuan(sum) + ")");
                    accountList();
                }

            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "网络异常");
        }
    }
}
