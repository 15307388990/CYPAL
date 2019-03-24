package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
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
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.activity.PersonalActivity;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.InfoEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.SignInDialog;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.CircleImageView;
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

    public MineFragment(Activity context) {
        super( context );
    }

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_mine, container, false );
        initView( view );
        return view;
    }


    @Override
    public void onResume() {
        setIn();
        super.onResume();

    }

    public void setIn() {
        Map<String, String> map = new HashMap<>();
        map.put( "contactType", "myInfo" );
        mQueue.add( ParamTools.packParam( Const.setIn, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
        loading();

    }

    public void signIn() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.signIn, mcontext, this, this, map ) );
        loading();

    }

    @Override
    public void returnData(String data, String url) {
        if (url.contains( Const.setIn )) {
            InfoEntity infoEntity = JSON.parseObject( data, InfoEntity.class );
            InfoEntity.DataBean.MyInformationBeanBean myInformationBeanBean = infoEntity.data.myInformationBean;
            if (myInformationBeanBean.avatar == null) {
                imageLoader.displayImage( Const.USER_DEFAULT_ICON, myicon, options );
            } else {
                imageLoader.displayImage( myInformationBeanBean.avatar, myicon,
                        options );

            }
            tv_nickname.setText( myInformationBeanBean.nickName );
            //是否认证
            if (myInformationBeanBean.certification) {
                iv_renzhen.setImageResource( R.drawable.label_members );
                tv_creditscore.setVisibility( View.VISIBLE );
                tv_text.setVisibility( View.GONE );
            } else {
                iv_renzhen.setImageResource( R.drawable.label_no );
                tv_creditscore.setVisibility( View.GONE );
                tv_text.setVisibility( View.VISIBLE );
            }
            //是否签到
            if (("SIGNIN").equals( myInformationBeanBean.signStatus )) {
                ll_view_back.setVisibility( View.GONE );
                ll_view_back2.setVisibility( View.VISIBLE );
            } else {
                ll_view_back.setVisibility( View.VISIBLE );
                ll_view_back2.setVisibility( View.GONE );
            }
            //是否显示邀请好友
            if (myInformationBeanBean.showInviteFriends) {
                ll_yaoqing.setVisibility( View.VISIBLE );
                v_yaoqing.setVisibility( View.VISIBLE );
            } else {
                ll_yaoqing.setVisibility( View.GONE );
                v_yaoqing.setVisibility( View.GONE );
            }
            tv_creditscore.setText( "信用分：" + myInformationBeanBean.creditScore );
        } else if (url.contains( Const.signIn )) {
            SignInDialog signInDialog = SignInDialog.newInstance( "" );
            signInDialog.show( mcontext );
            ll_view_back.setVisibility( View.GONE );
            ll_view_back2.setVisibility( View.VISIBLE );
        }
    }

    private void initView(View view) {
        right_view_text = (TextView) view.findViewById( R.id.right_view_text );
        right_view_text.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, PersonalActivity.class, false );
            }
        } );
        ll_view_back2 = (LinearLayout) view.findViewById( R.id.ll_view_back2 );
        ll_view_back = (LinearLayout) view.findViewById( R.id.ll_view_back );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        } );
        iv_renzhen = (ImageView) view.findViewById( R.id.iv_renzhen );
        tv_nickname = (TextView) view.findViewById( R.id.tv_nickname );
        myicon = (CircleImageView) view.findViewById( R.id.myicon );
        ll_yaoqing = (LinearLayout) view.findViewById( R.id.ll_yaoqing );
        v_yaoqing = (View) view.findViewById( R.id.v_yaoqing );
        tv_text = (TextView) view.findViewById( R.id.tv_text );
        tv_creditscore = (TextView) view.findViewById( R.id.tv_creditscore );
        tv_text.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, MemberActivity.class, false );
            }
        } );
        ll_shou = (LinearLayout) view.findViewById( R.id.ll_shou );
        ll_shou.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, AccountListActivity.class, false );
            }
        } );
        ll_commission = (LinearLayout) view.findViewById( R.id.ll_commission );
        ll_commission.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, CommissionAcitity.class, false );
            }
        } );
    }
}
