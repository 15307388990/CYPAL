package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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
import com.cypal.ming.cypal.bean.ContentEntity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.GradSingDialogBinding;
import com.cypal.ming.cypal.dialogfrment.GradSingDialog;
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
    private List<ContentEntity> list;
    private ManagerAdapter managerAdapter;
    private String orderId;
    private String amount;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            if (!TextUtils.isEmpty( text )) {
                ManagerEntity managerEntity = JSON.parseObject( text, ManagerEntity.class );
                ContentEntity contentEntity = JSON.parseObject( managerEntity.content, ContentEntity.class );
                list.add( contentEntity );
                UpdateAdapter();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_grab_single );
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.top_background ) );
        WsManager.getInstance().init( mSavePreferencesData.getStringData( "token" ), GrabSingleActivity.this );
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
        list = new ArrayList<ContentEntity>();
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
                    params.leftMargin = (int) cursorWidth + cursorWidth;
                    cursor.setLayoutParams( params );
                }
                UpdateAdapter();
            }
        } );
    }


    @Override
    public void returnData(String data, String url) {
        if (url.contains( Const.take )) {
            GradSingDialog.newInstance( amount ).show( GrabSingleActivity.this );
        }

    }

    @Override
    protected void returnMsg(String data, String url) {
        if (url.contains( Const.take )) {
            for (int i = 0; i < list.size(); i++) {
                if (orderId.equals( list.get( i ).orderId )) {
                    list.get( i ).isQian = true;
                }

            }
        }
        UpdateAdapter();
    }

    @Override
    public void onTextMessage(final String text) {
        new Thread() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = text;
                message.what = 1;
                handler.sendMessage( message );
            }
        }.start();

    }

    /**
     * //过滤刷新列表
     */
    private void UpdateAdapter() {
        String paytype = null;

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

    private List<ContentEntity> getList(String payType) {
        List<ContentEntity> contentEntities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get( i ).payType.equals( payType )) {
                contentEntities.add( list.get( i ) );
            }

        }
        return contentEntities;
    }


    @Override
    public void Qiang(String orderId, String amount) {
        this.orderId = orderId;
        this.amount = amount;
        take();
    }
}