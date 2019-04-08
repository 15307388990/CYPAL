package com.cypal.ming.cypal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.adapter.FriendsListAdapter;
import com.cypal.ming.cypal.adapter.TopUpRecordListAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.FriendsListEntity;
import com.cypal.ming.cypal.bean.TopUpListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.utils.TopUpState;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 好友列表
 */
public class FriendsListActivity extends BaseActivity {


    private LinearLayout ll_view_back;
    private RecyclerView recycleView;
    private FriendsListAdapter topUpListAdapter;
    private List<FriendsListEntity.DataBean.TeamBean> list;
    private LinearLayout.LayoutParams params;
    private int cursorWidth;
    private LinearLayout cursor;
    private String levelEnum = "A";
    private TopUpState topUpState;
    private SpringView springView;
    private int pageNumber = 1;
    boolean isPage = true;
    private TextView right_view_text;
    private RadioButton rd_a;
    private RadioButton rd_b;
    private RadioGroup rg_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        initView();
    }

    private void myTeam() {
        Map<String, String> map = new HashMap<>();
        map.put("levelEnum", levelEnum + "");
        map.put("page", pageNumber + "");
        map.put("size", "10");
        mQueue.add(ParamTools.packParam(Const.myTeam, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        rd_a = (RadioButton) findViewById(R.id.rd_a);
        rd_b = (RadioButton) findViewById(R.id.rd_b);
        rg_top = (RadioGroup) findViewById(R.id.rg_top);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        topUpListAdapter = new FriendsListAdapter(FriendsListActivity.this, list);
        recycleView.setAdapter(topUpListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        springView = (SpringView) findViewById(R.id.springView);
        cursor = (LinearLayout) findViewById(R.id.cursor);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                myTeam();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                if (isPage) {
                    myTeam();
                } else {
                    Tools.showToast(FriendsListActivity.this, "没有更多数据了");
                    springView.onFinishFreshAndLoad();
                }

            }
        });
        initEvent();
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        rg_top.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rd_a) {
                    levelEnum = "A";
                    params.leftMargin = 0;
                    cursor.setLayoutParams(params);
                } else {
                    levelEnum = "B";
                    params.leftMargin = (int) cursorWidth;
                    cursor.setLayoutParams(params);
                }
                myTeam();
            }
        });
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(FriendsListActivity.this, TransferActivity.class, false);
            }
        });

    }

    public void initEvent() {
        params = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        cursorWidth = params.width = Tools.getScreenWidth(FriendsListActivity.this) / 2;
        cursor.setLayoutParams(params);
        topUpState = TopUpState.SUCCESS;
        params.leftMargin = 0;
        cursor.setLayoutParams(params);
        pageNumber = 1;
        myTeam();

    }

    @Override
    protected void returnData(String data, String url) {
        springView.onFinishFreshAndLoad();
        FriendsListEntity friendsListEntity = JSON.parseObject(data, FriendsListEntity.class);
        rd_a.setText("A级（" + friendsListEntity.data.Acount + "位）");
        rd_b.setText("B级（" + friendsListEntity.data.Bcount + "位）");

        if (friendsListEntity.data.team.size() == 10) {
            isPage = true;
        } else {
            isPage = false;
        }
        if (pageNumber == 1) {
            list = friendsListEntity.data.team;
        } else {
            list.addAll(friendsListEntity.data.team);
        }

        topUpListAdapter.updateAdapter(list);

    }

}
