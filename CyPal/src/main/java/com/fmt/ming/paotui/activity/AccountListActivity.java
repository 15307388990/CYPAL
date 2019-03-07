package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fmt.ming.paotui.config.Const.accountList;

/**
 * 账户列表
 */
public class AccountListActivity extends BaseActivity {


    private ListView lv_list;
    private TextView ll_list_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        initView();
        initTitle();
        title.setText("选择银行卡");
        rightView.setImageDrawable(getResources().getDrawable(R.drawable.add_banl));
        rightView.setVisibility(View.VISIBLE);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.jump(AccountListActivity.this, AddAccount2Activity.class, false);
            }
        });
        accountList();
    }


    //获取提现账号列表
    public void accountList() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(accountList, this, this, map));
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
                if (url.contains(accountList)) {
                    JSONArray jsonArray = json.getJSONArray("accountlist");
//                    "account_name":"string,持卡人真实姓名",
//                            "account_card":"string,持卡人卡号/支付宝账号",
//                            "account_bank":"string,开户行名字 为银行卡时必填",
//                            "account_bank_address":"string,开户行地址"
                }
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }
    }

    private void initView() {
        lv_list = (ListView) findViewById(R.id.lv_list);
        ll_list_none = (TextView) findViewById(R.id.ll_list_none);
    }
}
