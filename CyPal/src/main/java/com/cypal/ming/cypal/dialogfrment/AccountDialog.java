package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.MemberActivity;
import com.cypal.ming.cypal.activity.TopUpDetailsActivity;
import com.cypal.ming.cypal.adapter.AccountListAdapter;
import com.cypal.ming.cypal.adapter.DialogAccountListAdapter;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.AccountDialogBinding;
import com.cypal.ming.cypal.databinding.TradingDialogBinding;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 账号列表
 */

public class AccountDialog extends CenterDialog implements Response.Listener<String>, Response.ErrorListener, DialogAccountListAdapter.OnClickListener {

    private static final String MINLIMIT = "minLimit";
    private static final String RWPID = "rwpId";
    private AccountDialogBinding binding;
    private String rwpId;
    private int minLimit = 0;
    private Context mContext;
    public RequestQueue mQueue; // 请求列队
    public SavePreferencesData mSavePreferencesData;
    private DialogAccountListAdapter accountListAdapter;
    private List<AccountListEntity.DataBean> list;
    private RecyclerView recycleView;
    private List<String> ids = new ArrayList<>();
    private OnClickListener onClickListener;

    public AccountDialog() {
    }

    public static AccountDialog newInstance() {
        AccountDialog dialog = new AccountDialog();
        Bundle bundle = new Bundle();
//        bundle.putString( RWPID, rwpId );
//        bundle.putInt( MINLIMIT, minLimit );
        dialog.setArguments( bundle );
        return dialog;
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void successful();

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss( dialog );
    }

    @Override
    public int getLayoutId() {
        return R.layout.account_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 1);
    }

    @Override
    public int getScreenHeight() {
        return (int) (Tools.getScreenHeight( getActivity() ) * 1 / 2);
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (AccountDialogBinding) dataBinding;
        recycleView = binding.ryList;
        accountListAdapter = new DialogAccountListAdapter( mContext, list, AccountDialog.this );
        recycleView.setAdapter( accountListAdapter );
        recycleView.setLayoutManager( new LinearLayoutManager( mContext ) );
        binding.btnNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Use( Tools.convertListToString( ids ) );
            }
        } );
        binding.ivColse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );

    }

    @Override
    public void show(Object object) {
        mContext = (Context) object;
        mSavePreferencesData = new SavePreferencesData( mContext );
        mQueue = Volley.newRequestQueue( mContext );
        getBankBranchsByCityCode();
        super.show( object );
    }


    private void getBankBranchsByCityCode() {
        Map<String, String> map = new HashMap<>();
        mQueue.add( ParamTools.packParam( Const.payAccount, this, this, map, Request.Method.GET, mSavePreferencesData.getStringData( "token" ) ) );
    }

    private void Use(String account_id) {
        Map<String, String> map = new HashMap<>();
        map.put( "ids", account_id );
        mQueue.add( ParamTools.packParam( Const.use, mContext, this, this, map ) );
    }

    @Override
    public void onResponse(String response, String url) {
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "code" );
            String msg = json.optString( "msg" );
            String data = json.optString( "data" );
            if (stauts == 1) {
                if (url.contains( Const.payAccount )) {
                    AccountListEntity accountListEntity = JSON.parseObject( response, AccountListEntity.class );
                    accountListAdapter.updateAdapter( Finishing( accountListEntity.data ) );
                } else if (url.contains( Const.use )) {
                    dismiss();
                    onClickListener.successful();
                }

            } else if (stauts == -2) {
                dismiss();
                //跳转至认证会员
                Tools.jump( (Activity) mContext, MemberActivity.class, false );
            } else {
                dismiss();
                Tools.showToast( mContext, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dismiss();
            Tools.showToast( mContext, "数据格式不对" );
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    /**
     * 整理数据
     *
     * @return
     */
    public List<AccountListEntity.DataBean> Finishing(List<AccountListEntity.DataBean> dataBeanList) {
        List<AccountListEntity.DataBean> wxlist = new ArrayList<>();
        boolean isx = true;
        for (AccountListEntity.DataBean dataBean : dataBeanList) {
            if (dataBean.accountType.equals( "WXPAY" )) {
                if (isx) {
                    dataBean.isx = isx;
                    isx = false;
                }
                if (dataBean.used) {
                    ids.add( dataBean.id + "" );
                }
                wxlist.add( dataBean );

            }
        }

        isx = true;
        for (AccountListEntity.DataBean dataBean : dataBeanList) {

            if (dataBean.accountType.equals( "ALIPAY" )) {
                if (isx) {
                    dataBean.isx = isx;
                    isx = false;
                }
                if (dataBean.used) {
                    ids.add( dataBean.id + "" );
                }
                wxlist.add( dataBean );
            }
        }
        isx = true;
        for (AccountListEntity.DataBean dataBean : dataBeanList) {
            if (dataBean.accountType.equals( "CLOUDPAY" )) {
                if (isx) {
                    dataBean.isx = isx;
                    isx = false;
                }
                if (dataBean.used) {
                    ids.add( dataBean.id + "" );
                }
                wxlist.add( dataBean );
            }
        }
        return wxlist;
    }

    @Override
    public void add(String id) {
        ids.add( id );
    }

    @Override
    public void delete(String id) {
        ids.remove( id );

    }
}
