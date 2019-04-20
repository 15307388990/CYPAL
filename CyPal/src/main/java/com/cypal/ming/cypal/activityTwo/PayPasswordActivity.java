package com.cypal.ming.cypal.activityTwo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.utils.Tools;

/**
 * @author luoming
 * created at 2019/4/19 9:49 AM
 * 支付密码
 */
public class PayPasswordActivity extends BaseActivity {
    private LinearLayout ll_view_back;
    private LinearLayout ll_modify;
    private LinearLayout ll_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_pay_password);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        ll_modify = (LinearLayout) findViewById(R.id.ll_modify);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(PayPasswordActivity.this,ModifyPayPasswordActivity.class,false);
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(PayPasswordActivity.this,BackPayPasswordActivity.class,false);
            }
        });
    }


}
