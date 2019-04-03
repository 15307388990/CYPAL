package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改昵称
 */
public class NickNameActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private EditText et_nick;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void modifyNickName() {
        Map<String, String> map = new HashMap<>();
        map.put("nickName", et_nick.getText().toString().trim());
        mQueue.add(ParamTools.packParam(Const.modifyNickName, this, this, this, map));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_nick = (EditText) findViewById(R.id.et_nick);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        String nickname = getIntent().getStringExtra("nickname");
        if (!TextUtils.isEmpty(nickname)) {
            et_nick.setText(nickname);
        }
    }


    @Override
    protected void returnData(String data, String url) {
        Tools.showToast(NickNameActivity.this, "修改成功");
        Intent intent = new Intent();
        intent.putExtra("nickname", et_nick.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String nick = et_nick.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {
            Tools.showToast(this, "请输入您的昵称");
            return;
        }
        modifyNickName();


    }
}
