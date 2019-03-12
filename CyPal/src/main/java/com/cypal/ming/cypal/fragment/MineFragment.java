package com.cypal.ming.cypal.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.activity.MeassActivity;
import com.cypal.ming.cypal.activity.PersonalActivity;
import com.cypal.ming.cypal.activity.SetActivity;
import com.cypal.ming.cypal.base.BaseFragment;
import com.cypal.ming.cypal.bean.UserBean;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的
 */
@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment {

    private CircleImageView myicon;//头像
    private TextView right_view_text;

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
        super.onResume();

    }


    private void initDate(UserBean userBean) {
    }


    private void initView(View view) {
        right_view_text = (TextView) view.findViewById( R.id.right_view_text );
        right_view_text.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump( mcontext, PersonalActivity.class, false );
            }
        } );
    }
}
