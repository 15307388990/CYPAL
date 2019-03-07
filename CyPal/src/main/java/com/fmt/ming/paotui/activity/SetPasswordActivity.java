package com.fmt.ming.paotui.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.bean.StoreBean;
import com.fmt.ming.paotui.utils.MD5Util;
import com.fmt.ming.paotui.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetPasswordActivity extends BaseActivity {
	/**
	 * 设置新密码
	 */
	@ViewInject(R.id.et_password_one)
	private EditText et_password_one; // 新密码
	@ViewInject(R.id.et_password_two)
	private EditText et_password_two; // 确认密码
	@ViewInject(R.id.login)
	private Button login; // 确认
	private String name, pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpassword);
		ViewUtils.inject(this);
		initTitle();
		ll_view_back.setVisibility(View.GONE);
		title.setText("设置新密码");
		et_password_two.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et_password_two.getText().length() >= 1) {
					login.setBackgroundResource(R.drawable.login_btn_n);
					login.setTextColor(getResources().getColor(R.color.actionbar_title_color));
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/** 单击事件 */
	@OnClick({ R.id.login, R.id.right_view_text })
	public void clickMethod(View v) {
		if (v.getId() == R.id.login) {
			if (checkParams()) {
				loading();
				toLogin(name, pwd);
			}
		}
	}

	/* 执行登录操作 */
	public void toLogin(String name, String pwd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("admin_account", name);
		String password = MD5Util.getMD5String(pwd);
		map.put("admin_pass", password);
		loading();
	}

	/** 检测参数合法性 */
	public boolean checkParams() {
		name = et_password_one.getText().toString();
		pwd = et_password_two.getText().toString();
		if (!name.equals(pwd) || name.length() < 1 || pwd.length() < 1) {
			Tools.showToast(this, "两次密码必须相同!");
			return false;
		}
		return true;
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
				mSavePreferencesData.putStringData("user", name);
				mSavePreferencesData.putStringData("pwd", pwd);
				mSavePreferencesData.putStringData("json", response);
				storeBean = JSON.parseObject(response, StoreBean.class);
				// posReg();
				// 友盟统计
				MobclickAgent.onProfileSignIn(Tools.getDeviceBrand(), name);
				Tools.jump(this, TabActivity.class, true);
			} else {
				Tools.showToast(this, msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Tools.showToast(this, "发生错误,请重试!");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_HOME) {
			return true;
		}
		if (keyCode == event.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
