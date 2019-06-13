package com.cypal.ming.cypal.activityTwo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.SelectCityAapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.BankListEntity;
import com.cypal.ming.cypal.bean.BaseEntity;
import com.cypal.ming.cypal.bean.CityEntity;
import com.cypal.ming.cypal.bean.SelectCityEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * @author luoming
 * created at 2019/6/11 9:18 PM
 * 城市选择
 */
public class SelectCityActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private ClearEditText search_name;
    private IndexableLayout indexableLayout;
    private SelectCityAapter selectCityAapter;
    private List<CityEntity> city_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_select_city_activity);
        initView();
        getCityList();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        ll_view_back.setOnClickListener(this);
        search_name = (ClearEditText) findViewById(R.id.search_name);
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);

        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        selectCityAapter = new SelectCityAapter(this);
        indexableLayout.setAdapter(selectCityAapter);
        indexableLayout.setOverlayStyle_Center();
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        selectCityAapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityEntity entity) {
                Intent intent = new Intent();
                intent.putExtra("cityName", entity.getCityName());
                intent.putExtra("adcode", entity.getAdcode());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        search_name.addTextChangedListener(new TextWatcher() {

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
                filterData(search_name.getText().toString());

            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CityEntity> filterDateList = new ArrayList<CityEntity>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = city_data;
        } else {
            filterDateList.clear();
            if (city_data == null) {
                return;
            }
            for (CityEntity cityEntity : city_data) {
                String name = cityEntity.getCityName();
                if (name.contains(filterStr)) {
                    filterDateList.add(cityEntity);
                }
            }
        }

        // 根据a-z进行排序
        // Collections.sort(filterDateList, pinyinComparator);
        selectCityAapter.setDatas(filterDateList);
        selectCityAapter.notifyDataSetChanged();
    }

    /* 城市列表 */
    public void getCityList() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.city, this, this, map, Request.Method.GET, this));
        loading();
    }


    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.city)) {
            List<CityEntity> cityEntities = new ArrayList<>();
            CityEntity cityEntity = new CityEntity();
            cityEntity.setCityName("全部");
            cityEntities.add(cityEntity);

            SelectCityEntity entity = JSON.parseObject(data, SelectCityEntity.class);
            city_data = entity.getData();
            if (city_data.get(0).getCityName().equals("全部")) {
                indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter(selectCityAapter, "#", "#", cityEntities));
                city_data.remove(0);
            }

            selectCityAapter.setDatas(city_data);
            selectCityAapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_view_back:
                finish();
                break;
        }
    }


}
