package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.ManagerAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.ws.WsManager;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手动抢单
 */
public class GrabSingleActivity extends BaseActivity implements WsManager.IWsManagerActivityView, ManagerAdapter.OnClickListener {


    private RelativeLayout.LayoutParams params;
    private int cursorWidth;
    private int currentSelectTab;
    private LinearLayout ll_view_back;
    private RadioButton rd_phone;
    private RadioButton rd_email;
    private RadioButton rd_yun;
    private RadioGroup rd_group;
    private LinearLayout cursor;
    private RecyclerView recycleView;
    private List<ManagerEntity> list;
    private ManagerAdapter managerAdapter;
    private String orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_grab_single );
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.top_background ) );
        WsManager.getInstance().init( mSavePreferencesData.getStringData( "webSocketToken" ), GrabSingleActivity.this );
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WsManager.getInstance().disconnect();
    }

    /**
     * 抢单
     */
    public void take() {
        Map<String, String> map = new HashMap<>();
        map.put( "orderId", orderId );
        mQueue.add( ParamTools.packParam( Const.take, this, this, this, map ) );
        loading();
    }

    private void initView() {
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        rd_phone = (RadioButton) findViewById( R.id.rd_phone );
        rd_email = (RadioButton) findViewById( R.id.rd_email );
        rd_yun = (RadioButton) findViewById( R.id.rd_yun );
        rd_group = (RadioGroup) findViewById( R.id.rd_group );
        cursor = (LinearLayout) findViewById( R.id.cursor );
        recycleView = (RecyclerView) findViewById( R.id.recycleView );

        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        list = new ArrayList<>();
        managerAdapter = new ManagerAdapter( this, list, this );
        recycleView.setLayoutManager( new GridLayoutManager( this, 3 ) );
        recycleView.setAdapter( managerAdapter );

    }

    public void initEvent() {
        params = (RelativeLayout.LayoutParams) cursor.getLayoutParams();
        cursorWidth = params.width = Tools.getScreenWidth( GrabSingleActivity.this ) / 3;
        cursor.setLayoutParams( params );
        rd_group.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rd_phone) {
                    currentSelectTab = 0;
                    params.leftMargin = 0;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_email) {
                    currentSelectTab = 1;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams( params );
                } else if (checkedId == R.id.rd_yun) {
                    currentSelectTab = 2;
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams( params );
                }
            }
        } );
    }


    @Override
    public void returnData(String data, String url) {

    }

    @Override
    public void onTextMessage(String text) {
        if (!TextUtils.isEmpty( text )) {
            ManagerEntity managerEntity = JSON.parseObject( text, ManagerEntity.class );
            list.add( managerEntity );
        }
        String paytype = null;
        //过滤刷新列表
        switch (currentSelectTab) {
            case 0:
                paytype = "WXPAY";
                break;
            case 1:
                paytype = "ALIPAY";
                break;
            case 2:
                paytype = "CLOUDPAY";
                break;
        }
        managerAdapter.updateAdapter( getList( paytype ) );
    }

    private List<ManagerEntity> getList(String payType) {
        List<ManagerEntity> managerEntityList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get( i ).content.payType.equals( payType )) {
                managerEntityList.add( list.get( i ) );
            }

        }
        return managerEntityList;
    }

    @Override
    public void Qiang(String orderId) {
        this.orderId = orderId;
        take();
    }
}
