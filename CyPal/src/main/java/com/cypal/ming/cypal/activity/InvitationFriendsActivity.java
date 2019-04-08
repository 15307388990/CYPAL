package com.cypal.ming.cypal.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.InvitationEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoming 邀请好友
 */
public class InvitationFriendsActivity extends BaseActivity {

    private LinearLayout ll_view_back;
    private TextView right_view_text;
    private TextView tv_invite_code;
    private ImageView iv_img;
    private String qrcodeSrc;
    private TextView tv_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_friends);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.CY_3776FB));
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        invite();
    }

    private void invite() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.invite, this, this, map, Request.Method.GET, this));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_copy = (TextView) findViewById(R.id.tv_copy);
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //好友列表
                Tools.jump(InvitationFriendsActivity.this, FriendsListActivity.class, false);
            }
        });
        tv_invite_code = (TextView) findViewById(R.id.tv_invite_code);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        iv_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Tools.onSaveBitmap(CodeUtils.createImage(qrcodeSrc, 150, 150, null), InvitationFriendsActivity.this);
                Tools.showToast(InvitationFriendsActivity.this, "二维码已保存到系统图库");
                return false;
            }
        });
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) InvitationFriendsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    clipboardManager.setText(tv_invite_code.getText().toString().trim());
                    Tools.showToast(InvitationFriendsActivity.this, "复制成功");
                }

            }
        });

    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.invite)) {
            InvitationEntity invitationEntity = JSON.parseObject(data, InvitationEntity.class);
            tv_invite_code.setText(invitationEntity.data.inviteCode);
            qrcodeSrc = invitationEntity.data.qrCodeUrl;
            iv_img.setImageBitmap(CodeUtils.createImage(qrcodeSrc, 166, 166, null));
        }


    }
}
