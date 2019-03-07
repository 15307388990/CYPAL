package com.fmt.ming.paotui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.CashFreezeAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.SettleListBean;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 冻结金额记录
 */

public class CashFreezeActivity extends BaseActivity {

	@ViewInject(R.id.ll_wu)
	private LinearLayout ll_wu;
	@ViewInject(R.id.recycleView)
	private ListView mListView;

	private List<SettleListBean> financeSettles = new ArrayList<SettleListBean>();
	private int pagNumber = 1;// 页码
	private CashFreezeAdapter mCashFreezeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_freeze);
		ViewUtils.inject(this);
		Tools.acts.add(this);
		initTitle();
		title.setText("冻结金额记录");
		initView();
	}

	private void initView() {
		mCashFreezeAdapter = new CashFreezeAdapter(this, financeSettles);
		mListView.setAdapter(mCashFreezeAdapter);
		obtainData();
		loading();
	}

	/* 获取数据 */
	public void obtainData() {
		Map<String, String> map = new HashMap<String, String>();
		// agency_id int 机构id
		// auth_token String 登陆令牌
		// admin_id int 管理员id
		// pager.pageNumber int 页码
		// pager.pageSize int 每页显示数量
		map.put("auth_token", partnerBean.getAuth_token());
		map.put("settle_stauts", 0 + "");
		map.put("pageNumber", pagNumber + "");
		map.put("pageSize", "10000");
		mQueue.add(ParamTools.packParam(Const.freezeList, this, this, map));
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
				JSONObject jsonObject=json.optJSONObject("result");
				String settle_list = jsonObject.optString("settle_list");
				List<SettleListBean> finance = JSON.parseArray(settle_list, SettleListBean.class);
				if (pagNumber > 1) {
					financeSettles.addAll(finance);
				} else {
					financeSettles = finance;
				}
				if (financeSettles.size() > 0) {
					mListView.setVisibility(View.VISIBLE);
					ll_wu.setVisibility(View.GONE);
				} else {
					ll_wu.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
				}

				mCashFreezeAdapter.updateListView(financeSettles);
			} else {
				Tools.showToast(this, msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Tools.showToast(this, R.string.tips_unkown_error);
		}
	}

}
