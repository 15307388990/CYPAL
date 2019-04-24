package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activityTwo.LoginPhotoValidationActivity;
import com.cypal.ming.cypal.adapter.AddAccoutAdapter;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountEntity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.bean.PayAccoutBobyEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @author luoming 添加收款账号
 */
public class SaveAccountAcitity extends BaseActivity implements View.OnClickListener, AddAccoutAdapter.OnClickListener {

    private LinearLayout ll_view_back;
    private TextView top_view_text;
    private EditText et_accout;
    private EditText et_name;
    private Button btn_next;
    private String type;
    private String value;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private TextView tv_account;
    private List<AccountEntity> accountDatas = new ArrayList<>();
    private int[] accountMoney = {100, 200, 300, 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
    private AccountListEntity.DataBean dataBean;
    private TextView right_view_text;
    private String accountId;

    private RecyclerView recycleView;
    private View headView;
    private View bootomView;
    private AddAccoutAdapter accoutAdapter;
    private int position;
    private TextView tv_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_save_account);
        initView();
        initPageDate();
    }


    private void initView() {
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        recycleView = findViewById(R.id.recycleView);
        headView = LayoutInflater.from(this).inflate(R.layout.account_item_head, null);
        bootomView = LayoutInflater.from(this).inflate(R.layout.account_item_bootm, null);

        /**
         * headView
         */
        et_accout = (EditText) headView.findViewById(R.id.et_accout);
        et_name = (EditText) headView.findViewById(R.id.et_name);
        tv_account = (TextView) headView.findViewById(R.id.tv_account);

        /**
         * bootomView
         */
        btn_next = (Button) bootomView.findViewById(R.id.btn_next);
        tv_text = (TextView) bootomView.findViewById(R.id.tv_text);


        btn_next.setOnClickListener(this);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveAccountAcitity.this);
                builder.setMessage("确定删除该账号?");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        del();

                    }
                });
                builder.create().show();

            }
        });


    }

    /**
     * 初始化页面
     */
    private void initPageDate() {

        type = getIntent().getStringExtra("type");
        value = getIntent().getStringExtra("value");
        dataBean = (AccountListEntity.DataBean) getIntent().getSerializableExtra("date");
        top_view_text.setText("添加" + value + "二维码");
        tv_account.setText(value + "账号");
        et_accout.setHint("请输入" + value + "账号");
        /**
         * 初始化图片
         */
        for (int i = 0; i < accountMoney.length; i++) {
            AccountEntity entity = new AccountEntity();
            entity.accountMoney = accountMoney[i];
            accountDatas.add(entity);
        }
        if (dataBean != null) {
            InitDate(dataBean);
        }
        tv_text.setText("温馨提示： \n\n 1、请确保" + value + "已实名认证，并填写真实姓名（请不要填写 呢称） \n\n2、为了保证顺利提现，" +
                "30天内不要更换其他账号，否则会 导致提现不通过 \n\n3.本功能需要上传同一" + value + "14个金额码，请确保码的金额与添加的金额完全对应，否则不会收款到账。" +
                "\n\n4、我们承诺不会向任何人透露您的个人信息");
        accoutAdapter = new AddAccoutAdapter(this, accountDatas, this);
        accoutAdapter.setHeaderView(headView);
        accoutAdapter.setFooderView(bootomView);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setAdapter(accoutAdapter);


    }

    /**
     * 添加账号
     */
    private void getBankBranchsByCityCode(String pwd) {
        PayAccoutBobyEntity payAccoutBobyEntity = new PayAccoutBobyEntity();
        payAccoutBobyEntity.accountName = et_accout.getText().toString().trim();
        payAccoutBobyEntity.accountType = type;
        payAccoutBobyEntity.realName = et_name.getText().toString().trim();
        payAccoutBobyEntity.payAccountCodeList = accountDatas;
        if (!TextUtils.isEmpty(accountId)) {
            payAccoutBobyEntity.id = accountId;
        }
        String payPwd = MD5Util.getMD5String(pwd);
        Map<String, String> map = new HashMap<>();

        mQueue.add(ParamTools.packParam(Const.payAccountSave + "?payPassword=" + payPwd, this, this, this, map, JSON.toJSONString(payAccoutBobyEntity).getBytes()));
        loading();
    }

    /**
     * 删除账号
     */
    private void del() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(accountId)) {
            map.put("account_id", accountId);
        }
        mQueue.add(ParamTools.packParam(Const.del, this, this, this, map));
        loading();
    }

    private void JumpMultiImage(int requestCode) {

        Intent intent = new Intent(SaveAccountAcitity.this,
                MultiImageSelectorActivity.class);
        intent.putExtra("isUploadIcon", true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                false);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                1);
        startActivityForResult(intent, requestCode);
    }

    private void InitDate(AccountListEntity.DataBean dataBean) {
        right_view_text.setVisibility(View.VISIBLE);
        et_name.setText(dataBean.realName);
        et_accout.setText(dataBean.accountName);
        accountId = dataBean.id + "";
        if (!TextUtils.equals(dataBean.payAccountCodeList, "[]")) {
            accountDatas = JSON.parseArray(dataBean.payAccountCodeList, AccountEntity.class);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                resultList = data.getExtras().getStringArrayList("select_result");
                bitmapInfos = new ArrayList<Bitmap>();
                filePaths = new ArrayList<String>();
                icon_ids = new ArrayList<String>();

                for (int k = 0; k < resultList.size(); k++) {
                    Bitmap bitmap = ImageUtil.decodeImage(resultList.get(k));
                    bitmapInfos.add(bitmap);
                    filePaths.add(resultList.get(k));
                }
                Map<String, String> map = new HashMap<>();
                map.put("uploadEnum", "certification");
                File file = new File(filePaths.get(0));
                parseInfoFromBitmap(filePaths.get(0));


            }
        }
    }

    public void parseInfoFromBitmap(String path) {
        try {
            CodeUtils.analyzeBitmap(path, new CodeUtils.AnalyzeCallback() {
                @Override
                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

                    accountDatas.get(position - 1).accountData = result;
                    accoutAdapter.updateAdapter(accountDatas);

                }

                @Override
                public void onAnalyzeFailed() {
                    Tools.showToast(SaveAccountAcitity.this, "无法解析图片二维码，请重新选择");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String accout = et_accout.getText().toString().trim();
        if (TextUtils.isEmpty(accout)) {
            Toast.makeText(this, "请输入" + value + "账号", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        for (AccountEntity entity : accountDatas) {
            if (TextUtils.isEmpty(entity.accountData)) {
                Tools.showToast(this, "请上传" + entity.accountMoney + "元二维码");
                return;
            }

        }
        inoutPsw();


    }

    //打开输入密码的对话框
    public void inoutPsw() {
        final boolean hasPayPassword = mSavePreferencesData.getBooleanData("hasPayPassword");
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    if (hasPayPassword) {
                        getBankBranchsByCityCode(psw);
                    } else {
                        setPayPassword(psw);
                    }
                }
            }
        });
        if (!hasPayPassword) {
            menuWindow.setTitle("设置支付密码");
        }
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }

    /**
     * 设置支付密码
     */
    private void setPayPassword(String payPassword) {
        Map<String, String> map = new HashMap<>();
        map.put("payPassword", MD5Util.getMD5String(payPassword));
        mQueue.add(ParamTools.packParam(Const.setPayPassword, this, this, this, map));
        loading();
    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.setPayPassword)) {
            Tools.showToast(this, "设置成功");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        } else {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void AddImg(int position) {
        this.position = position;
        initPermission();


    }

    /**
     * 获取相册权限
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE).
                onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        JumpMultiImage(position);
                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Tools.showToast(SaveAccountAcitity.this, "需要获取相册权限");
                    }
                }).
                start();

    }
}
