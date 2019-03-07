package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.MD5Util;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * ● ● ● @version 设置支付密码 two ●
 **/
public class SetPayPasswordTwoActivity extends BaseActivity {
    @ViewInject(R.id.zero)
    private TextView zero;// 0
    @ViewInject(R.id.one)
    private TextView one;// 1
    @ViewInject(R.id.two)
    private TextView two;// 2
    @ViewInject(R.id.three)
    private TextView three;// 3
    @ViewInject(R.id.four)
    private TextView four;// 4
    @ViewInject(R.id.five)
    private TextView five;// 5
    @ViewInject(R.id.six)
    private TextView six;// 6
    @ViewInject(R.id.seven)
    private TextView seven;// 7
    @ViewInject(R.id.eight)
    private TextView eight;// 8
    @ViewInject(R.id.nine)
    private TextView nine;// 9
    @ViewInject(R.id.point)
    private TextView point;// .
    @ViewInject(R.id.delete)
    private ImageView delete;// delete

    @ViewInject(R.id.tv_1)
    private TextView tv_1;
    @ViewInject(R.id.tv_2)
    private TextView tv_2;
    @ViewInject(R.id.tv_3)
    private TextView tv_3;
    @ViewInject(R.id.tv_4)
    private TextView tv_4;
    @ViewInject(R.id.tv_5)
    private TextView tv_5;
    @ViewInject(R.id.tv_6)
    private TextView tv_6;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_authen)
    private LinearLayout ll_authen;

    private String addnumber = "";
    private String addnumberone;
    private int type;// 0为设置支付密码 1为修改支付密码
    private String sms_code;
    private String oldPayPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        ViewUtils.inject(this);
        Tools.acts.add(this);
        initTitle();

        //数据获取
        Intent intent = getIntent();
        addnumberone = intent.getStringExtra("addnumber");
        type = intent.getIntExtra("type", 0);
        sms_code = intent.getStringExtra("sms_code");
        oldPayPass = intent.getStringExtra("oldPayPass");

        //标题栏设置
        if (type == 0) {
            title.setText("设置支付密码");
        } else if (type == 1) {
            title.setText("修改支付密码");
            ll_view_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Tools.exit();
                }
            });
        }
        tv_title.setText("请再次确认支付密码");
    }

    /**
     * 单击事件
     */
    @OnClick({R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight,
            R.id.nine, R.id.point, R.id.delete})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.zero:
                add("0");
                break;
            case R.id.one:
                add("1");
                break;
            case R.id.two:
                add("2");
                break;
            case R.id.three:
                add("3");
                break;
            case R.id.four:
                add("4");
                break;
            case R.id.five:
                add("5");
                break;
            case R.id.six:
                add("6");
                break;
            case R.id.seven:
                add("7");
                break;
            case R.id.eight:
                add("8");
                break;
            case R.id.nine:
                add("9");
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    public void add(String addStr) {
        //如果满了6位数，不能再输入
        if (addnumber.length() == 6) {
            return;
        }
        //获取输入的数据进行处理
        addnumber = addnumber + addStr;
        initPassword();

        if (addnumber.length() == 6) {
            if (addnumber.equals(addnumberone)) {

                updatePayPass();    //0,1根据oldPayPass有没数值进行判断
            } else {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);// 加载动画资源文件
                ll_authen.startAnimation(shake); // 给组件播放动画效果
                Toast toast = Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(getApplicationContext());
                imageCodeProject.setImageResource(R.drawable.toast_wrong);
                toastView.addView(imageCodeProject, 0);
                toast.show();
            }
        }
    }

    public void delete() {
        if (addnumber.length() > 0) {
            addnumber = addnumber.substring(0, addnumber.length() - 1);
            initPassword();
        }
    }

    // 修改支付密码：有old为修改支付密码、没有为设置支付密码
    private void updatePayPass() {
        Map<String, String> map = new HashMap<>();
        if (oldPayPass != null && !oldPayPass.isEmpty()) {
            map.put("oldpass", oldPayPass);
        }
        map.put("newpass", MD5Util.getMD5String(addnumberone));
        map.put("confirm_pass", MD5Util.getMD5String(addnumber));
        map.put("auth_token", partnerBean.getAuth_token());
        map.put("sms_code", sms_code);
        mQueue.add(ParamTools.packParam(Const.updatePayPass, this, this, map));
        loading();
    }


    private void initPassword() {
        tv_6.setText("");
        tv_5.setText("");
        tv_4.setText("");
        tv_3.setText("");
        tv_2.setText("");
        tv_1.setText("");
        switch (addnumber.length()) {
            case 6:
                tv_6.setText("●");
            case 5:
                tv_5.setText("●");
            case 4:
                tv_4.setText("●");
            case 3:
                tv_3.setText("●");
            case 2:
                tv_2.setText("●");
            case 1:
                tv_1.setText("●");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int status = json.optInt("status");
            if (status == 0) {
                // 修改本地的支付密码
                partnerBean.setPass_pay(addnumber);
                partnerBean.setPay_password(1);
                String jsonString = JSON.toJSONString(partnerBean);
                mSavePreferencesData.putStringData("json", jsonString);  //bean对象是这个k,v数值
                Tools.showToast(this, type == 0 ? "设置成功" : "修改成功");
                finish();
            } else if (status < 0) {
                Tools.showToast(this, json.optString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, R.string.tips_unkown_error);
        }
    }
}
