package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 类名称：SucceedWithdrawAccessActivity 类描述：提现成功界面 创建人：罗富贵 创建时间：2016年5月11日
 * 
 * @version
 * 
 */
public class SucceedWithdrawAccessActivity extends BaseActivity {
	@ViewInject(R.id.btn_return)
	private Button btn_return;// 返回首页

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_succeed_withdraw);
		ViewUtils.inject(this);
		initTitle();
		title.setText("提现到余额");
	}

	/** 单击事件 */
	@OnClick({ R.id.btn_return })
	public void clickMethod(View v) {
		this.finish();
	}

}
