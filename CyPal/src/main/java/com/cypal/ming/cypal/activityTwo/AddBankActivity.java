package com.cypal.ming.cypal.activityTwo;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.BankListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoming
 * created at 2019/4/19 9:49 AM
 * 添加银行卡
 */
public class AddBankActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private EditText et_name;
    private Spinner spacer;
    private EditText et_card;
    private Button btn_next;
    private String bankString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_add_bank);
        initView();
        teamBankCard();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_name = (EditText) findViewById(R.id.et_name);
        spacer = (Spinner) findViewById(R.id.spacer);
        et_card = (EditText) findViewById(R.id.et_card);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        ll_view_back.setOnClickListener(this);
    }

    /* 银行卡列表 */
    public void teamBankCard() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.teamBankCard, this, this, map, Request.Method.GET, this));
        loading();
    }

    /* 添加领导人银行卡 */
    public void saveBankCard(String payPassword) {
        String payString=MD5Util.getMD5String(payPassword);
        Map<String, String> map = new HashMap<>();
        map.put("realName", et_name.getText().toString().trim());
        map.put("accountData", et_card.getText().toString().trim());
        map.put("accountName", bankString);
        map.put("payPassword", payString);
        mQueue.add(ParamTools.packParam(Const.saveBankCard, this, this, this, map));
        loading();
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
        if (url.contains(Const.teamBankCard)) {
            final BankListEntity entity = JSON.parseObject(data, BankListEntity.class);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    AddBankActivity.this, android.R.layout.simple_spinner_item,
                    entity.data.bankLists);
            spacer.setAdapter(adapter);
            bankString = entity.data.bankLists.get(0);
            spacer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //选中触发
                    bankString = entity.data.bankLists.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (url.contains(Const.setPayPassword)) {
            Tools.showToast(this, "设置成功");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        } else if (url.contains(Const.saveBankCard)) {
            Tools.showToast(this, "添加成功");
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_view_back:
                finish();
                break;
            case R.id.btn_next:
                submit();
                break;
        }
    }


    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String card = et_card.getText().toString().trim();
        if (TextUtils.isEmpty(card)) {
            Toast.makeText(this, "银行卡不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        inoutPsw();
        // TODO validate success, do something


    }

    //打开输入密码的对话框
    public void inoutPsw() {
        final boolean hasPayPassword = mSavePreferencesData.getBooleanData("hasPayPassword");
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if (hasPayPassword) {
                        saveBankCard(psw);
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
