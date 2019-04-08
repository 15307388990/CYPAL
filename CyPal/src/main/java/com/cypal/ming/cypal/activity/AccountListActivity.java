package com.cypal.ming.cypal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.CategoryAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.CategoryEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户列表
 */
public class AccountListActivity extends BaseActivity implements CategoryAdapter.OnClickListener {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryEntity.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_account_list );
        initView();
        getBankBranchsByCityCode();
    }

    private void getBankBranchsByCityCode() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.category, this, this, map, Request.Method.GET,this ) );
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        recycleView = (RecyclerView) findViewById( R.id.recycleView );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        list = new ArrayList<>();
        categoryAdapter = new CategoryAdapter( this, list, this );
        recycleView.setAdapter( categoryAdapter );
        recycleView.setLayoutManager( new LinearLayoutManager( this ) );

    }

    @Override
    protected void returnData(String data, String url) {
        super.returnData( data, url );
        CategoryEntity categoryEntity = JSON.parseObject( data, CategoryEntity.class );
        categoryAdapter.updateAdapter( categoryEntity.data );

    }

    @Override
    public void ConfirmReceipt(String value, String type) {
        Intent intent = new Intent( AccountListActivity.this, BankList.class );
        intent.putExtra( "value", value );
        intent.putExtra( "type", type );
        startActivity( intent );

    }
}
