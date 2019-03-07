package com.fmt.ming.paotui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.SortAdapter;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.bean.CityModel;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.dialog.SideBar;
import com.fmt.ming.paotui.utils.CharacterParser;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCity extends BaseActivity implements OnItemClickListener {
    private EditText searchView = null;
    private ListView city_list = null;
    private SortAdapter adapter = null;
    private List<CityModel> city_data = null;
    private CharacterParser characterParser = null;
    public static SelectCity selectCity = null;
    private SideBar sideBar;
    private TextView dialog;
    private List<CityModel> city_data2 = null;
    private String belongs_bank;// 所属银行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        ViewUtils.inject(this);
        initTitle();
        title.setText("城市选择");
        belongs_bank = getIntent().getStringExtra("belongs_bank");
        city_list = (ListView) findViewById(R.id.city_list);
        searchView = (EditText) findViewById(R.id.et_search);
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                // filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(searchView.getText().toString());

            }
        });
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    city_list.setSelection(position);
                }

            }
        });

        characterParser = CharacterParser.getInstance();
        city_data = new ArrayList<CityModel>();
        getAllBankCity();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("SelectCity", "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                setResult(RESULT_OK, data);// 定义返回的参数parama
                finish();
            }
        }
    }

    //数据
    private void getAllBankCity() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.getAllBankCity, this, this, map));
        loading();
    }
    /**
     * 根据输入框中的�?�来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CityModel> filterDateList = new ArrayList<CityModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = city_data;
        } else {
            filterDateList.clear();
            for (CityModel sortModel : city_data) {
                String name = sortModel.getCity_name();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        // Collections.sort(filterDateList, pinyinComparator);
        city_data2 = filterDateList;
        adapter.updateListView(filterDateList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        String city_code = city_data2.get(position).getCity_code();
        Intent intent = new Intent(SelectCity.this, BankList.class);
        intent.putExtra("belongs_bank", belongs_bank);
        intent.putExtra("city_code", city_code);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int resultCode = json.getInt("status");

            if (resultCode == 0) {
                if (url.contains(Const.getAllBankCity)) {
                    List<CityModel> cityModels = JSON.parseArray(json.optString("result"), CityModel.class);
                    if (cityModels != null && cityModels.size() > 0) {
                        city_data = sortCityDatas(cityModels);
                        adapter = new SortAdapter(this, city_data);
                        city_data2 = city_data;
                        city_list.setAdapter(adapter);
                        sideBar.setVisibility(View.VISIBLE);
                        city_list.setOnItemClickListener(SelectCity.this);
                    }
                }
            } else if (resultCode == -4004) {
                mSavePreferencesData.putStringData("json", "");
                Tools.jump(this, LoginActivity.class, true);
                Tools.showToast(this, "登录过期请重新登录?");
            } else {
                Tools.showToast(getApplicationContext(), "接口错误");
            }
        } catch (JSONException e) {
            // TODO: handle exception
            Tools.showToast(getApplicationContext(), "解析数据错误");
        }

    }
    private List<CityModel> sortCityDatas(List<CityModel> cityModels) {

        Collections.sort(cityModels, new Comparator<CityModel>() {
            @Override
            public int compare(CityModel c1, CityModel c2) {

                if (c1.getSort().charAt(0) > c2.getSort().charAt(0)) {
                    return 1;
                }
                if (c1.getSort().charAt(0) == c2.getSort().charAt(0)) {
                    return 0;
                }
                return -1;
            }
        });
        return cityModels;
    }
}
