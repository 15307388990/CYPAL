package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.AccountListActivity;
import com.cypal.ming.cypal.activity.CommissionAcitity;
import com.cypal.ming.cypal.activity.InvitationFriendsActivity;
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.activity.OrderListActivity;
import com.cypal.ming.cypal.activity.PersonalActivity;
import com.cypal.ming.cypal.activity.TopUpListActivity;
import com.cypal.ming.cypal.activity.WbhbListActivity;
import com.cypal.ming.cypal.activityTwo.TeamRerunsActivity;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.InfoEntity;
import com.cypal.ming.cypal.bean.SignInEntity;
import com.cypal.ming.cypal.bean.VersionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.SignInDialog;
import com.cypal.ming.cypal.dialogfrment.VersionUpgradeDialog;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.CircleImageView;
import com.cypal.ming.cypal.vm.TeamRerunsHeadVM;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的
 */
@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment {

    private CircleImageView myicon;//头像
    private TextView right_view_text;
    private LinearLayout ll_view_back;//签到
    private LinearLayout ll_view_back2;//已签到
    private ImageView iv_renzhen;
    private TextView tv_nickname;
    private LinearLayout ll_yaoqing;
    private View v_yaoqing;
    private TextView tv_text;
    private TextView tv_creditscore;
    private LinearLayout ll_shou;
    private LinearLayout ll_commission;
    private LinearLayout ll_jiedan;
    private LinearLayout ll_top_up;
    private TextView tv_version;
    private LinearLayout ll_version;
    private LinearLayout ll_tuandui;
    private ImageView iv_round_red;//红色圆点
    private LinearLayout ll_wbhb;//微博红包

    public MineFragment(Activity context) {
        super(context);
    }

    public MineFragment() {
    }

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        setIn();
        super.onResume();

    }

    public void setIn() {
        Map<String, String> map = new HashMap<>();
        map.put("contactType", "myInfo");
        mQueue.add(ParamTools.packParam(Const.setIn, this, this, map, Request.Method.GET, mcontext));
        loading();

    }

    public void signIn() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.signIn, mcontext, this, this, map));
        loading();

    }

    @Override
    public void returnData(String data, String url) {
        if (url.contains(Const.setIn)) {
            mSavePreferencesData.putStringData("infojson", data);
            initData(data);
        } else if (url.contains(Const.signIn)) {
            SignInEntity signInEntity = JSON.parseObject(data, SignInEntity.class);
            SignInDialog signInDialog = SignInDialog.newInstance(signInEntity.data.signReward);
            signInDialog.show(mcontext);
            ll_view_back.setVisibility(View.GONE);
            ll_view_back2.setVisibility(View.GONE);
        } else if (url.contains(Const.check)) {
            VersionEntity versionEntity = JSON.parseObject(data, VersionEntity.class);
            VersionUpgradeDialog.newInstance(versionEntity.data).show(mcontext);
        }
    }

    //检测版本
    private void AppVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("osEnum", "android");
        map.put("versionId", Tools.packageCode(mcontext) + "");
        mQueue.add(ParamTools.packParam(Const.check, mcontext, this, this, map));

    }

    private void initView(View view) {
        right_view_text = (TextView) view.findViewById(R.id.right_view_text);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, PersonalActivity.class, false);
            }
        });
        ll_view_back2 = (LinearLayout) view.findViewById(R.id.ll_view_back2);
        ll_view_back = (LinearLayout) view.findViewById(R.id.ll_view_back);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        iv_renzhen = (ImageView) view.findViewById(R.id.iv_renzhen);
        tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
        myicon = (CircleImageView) view.findViewById(R.id.myicon);
        ll_yaoqing = (LinearLayout) view.findViewById(R.id.ll_yaoqing);
        v_yaoqing = (View) view.findViewById(R.id.v_yaoqing);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        ll_version = (LinearLayout) view.findViewById(R.id.ll_version);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_creditscore = (TextView) view.findViewById(R.id.tv_creditscore);
        ll_tuandui = (LinearLayout) view.findViewById(R.id.ll_tuandui);
        ll_wbhb = (LinearLayout) view.findViewById(R.id.ll_wbhb);
        iv_round_red = (ImageView) view.findViewById(R.id.iv_round_red);
        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, MemberActivity.class, false);
            }
        });
        ll_shou = (LinearLayout) view.findViewById(R.id.ll_shou);
        ll_shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, AccountListActivity.class, false);
            }
        });
        ll_commission = (LinearLayout) view.findViewById(R.id.ll_commission);
        ll_commission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, CommissionAcitity.class, false);
            }
        });
        //缓存数据
        if (!TextUtils.isEmpty(mSavePreferencesData.getStringData("infojson"))) {
            initData(mSavePreferencesData.getStringData("infojson"));
        }
        ll_jiedan = (LinearLayout) view.findViewById(R.id.ll_jiedan);
        ll_jiedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, OrderListActivity.class, false);
            }
        });
        ll_top_up = (LinearLayout) view.findViewById(R.id.ll_top_up);
        ll_top_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, TopUpListActivity.class, false);
            }
        });
        tv_version.setText("v" + Tools.getVersionName(mcontext));

        ll_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppVersion();
            }
        });
        ll_yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, InvitationFriendsActivity.class, false);
            }
        });


        ll_tuandui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //团队收益
                Tools.jump(mcontext, TeamRerunsActivity.class, false);
            }
        });
        ll_wbhb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(mcontext, WbhbListActivity.class, false);

            }
        });

    }

    private void initData(String data) {

        InfoEntity infoEntity = JSON.parseObject(data, InfoEntity.class);
        InfoEntity.DataBean.MyInformationBeanBean myInformationBeanBean = infoEntity.data.myInformationBean;
        if (TextUtils.isEmpty(myInformationBeanBean.avatar)) {
            myicon.setImageResource(R.drawable.head);
        } else {
            imageLoader.displayImage(myInformationBeanBean.avatar, myicon,
                    options);

        }
        tv_nickname.setText(myInformationBeanBean.nickName);
        //是否认证
        if (myInformationBeanBean.certification) {
            iv_renzhen.setImageResource(R.drawable.label_members);
            tv_creditscore.setVisibility(View.VISIBLE);
            tv_text.setVisibility(View.GONE);
        } else {
            iv_renzhen.setImageResource(R.drawable.label_no);
            tv_creditscore.setVisibility(View.VISIBLE);
            tv_text.setVisibility(View.VISIBLE);
        }
        //是否签到
        if (("SIGNIN").equals(myInformationBeanBean.signStatus)) {
            ll_view_back.setVisibility(View.GONE);
            ll_view_back2.setVisibility(View.GONE);
        } else {
            ll_view_back.setVisibility(View.VISIBLE);
            ll_view_back2.setVisibility(View.GONE);
        }
        //是否显示邀请好友
        if (myInformationBeanBean.showInviteFriends) {
            ll_yaoqing.setVisibility(View.VISIBLE);
            v_yaoqing.setVisibility(View.VISIBLE);
        } else {
            ll_yaoqing.setVisibility(View.GONE);
            v_yaoqing.setVisibility(View.GONE);
        }
        //是否显示团队收益
        if (myInformationBeanBean.showTeamRecharge) {
            ll_tuandui.setVisibility(View.VISIBLE);
        } else {
            ll_tuandui.setVisibility(View.GONE);
        }
        tv_creditscore.setText("信用分：" + myInformationBeanBean.creditScore);
        //是否显示版本更新的红圆点
        if (Tools.isRound) {
            iv_round_red.setVisibility(View.VISIBLE);
        } else {
            iv_round_red.setVisibility(View.GONE);
        }
    }

}
