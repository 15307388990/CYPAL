package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.AccountListEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageUtil;
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
 * @author luoming 添加微博红包收款账号
 */
public class SaveWbhbAccountAcitity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_view_back;
    private TextView top_view_text;
    private EditText et_accout;
    private EditText et_name;
    private ImageView iv_shang;
    private ImageView iv_xia;
    private Button btn_next;
    private String type;
    private String value;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> filePaths = null;
    private TextView tv_account;
    private String accountData;
    private AccountListEntity.DataBean dataBean;
    private TextView right_view_text;
    private String accountId;
    private EditText et_pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_save_wbhb_account);
        initView();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("SelectCity", "onResume");
    }

    private void getBankBranchsByCityCode() {
        Map<String, String> map = new HashMap<>();
        map.put("accountName", et_accout.getText().toString().trim());
        map.put("realName", et_name.getText().toString().trim());
        map.put("accountType", type);
        map.put("accountData", et_pid.getText().toString().trim());
        if (!TextUtils.isEmpty(accountId)) {
            map.put("id", accountId);
        }
        mQueue.add(ParamTools.packParam(Const.payAccountSave, this, this, this, map));
        loading();
    }

    private void del() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(accountId)) {
            map.put("account_id", accountId);
        }
        mQueue.add(ParamTools.packParam(Const.del, this, this, this, map));
        loading();
    }


    private void initView() {
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        value = getIntent().getStringExtra("value");
        dataBean = (AccountListEntity.DataBean) getIntent().getSerializableExtra("date");
        top_view_text.setText("添加" + value);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        et_accout = (EditText) findViewById(R.id.et_accout);
        et_name = (EditText) findViewById(R.id.et_name);
        iv_shang = (ImageView) findViewById(R.id.iv_shang);
        iv_xia = (ImageView) findViewById(R.id.iv_xia);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        iv_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();
            }
        });
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setText(value + "账号");
        et_accout.setHint("请输入" + value + "账号");
        right_view_text = (TextView) findViewById(R.id.right_view_text);
        right_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveWbhbAccountAcitity.this);
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
        et_pid = (EditText) findViewById(R.id.et_pid);
        if (dataBean != null) {
            InitDate(dataBean);
        }
    }

    private void InitDate(AccountListEntity.DataBean dataBean) {
        right_view_text.setVisibility(View.VISIBLE);
        et_name.setText(dataBean.realName);
        et_accout.setText(dataBean.accountName);
        et_pid.setText(dataBean.accountData);
        accountId = dataBean.id + "";
        //    PddBean pddBean = JSON.parseObject(accountData, PddBean.class);
//        et_pid.setText(pddBean.mall_id + "");
//        if (dataBean.enable) {
//            btn_next.setVisibility(View.GONE);
//        }
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
                    accountData = result;
                    iv_xia.setImageBitmap(bitmapInfos.get(0));
                }

                @Override
                public void onAnalyzeFailed() {
                    Tools.showToast(SaveWbhbAccountAcitity.this, "无法解析图片二维码，请重新选择");
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

        if (TextUtils.isEmpty(et_pid.getText().toString().trim())) {
            Toast.makeText(this, "请先上传正确的吹牛皮账号", Toast.LENGTH_SHORT).show();
            return;
        }
        PddBean pddBean = new PddBean();
        pddBean.mall_id = et_pid.getText().toString().trim();
        pddBean.group_id = "";
        pddBean.goods_id = "";
        pddBean.sku_id = "";
        accountData = JSON.toJSONString(pddBean, SerializerFeature.WriteMapNullValue);
        getBankBranchsByCityCode();

    }

    @Override
    protected void returnData(String data, String url) {
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        finish();
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
                        accountData = null;
                        Intent intent = new Intent(SaveWbhbAccountAcitity.this,
                                MultiImageSelectorActivity.class);
                        intent.putExtra("isUploadIcon", true);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                                false);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                                1);
                        startActivityForResult(intent, 1);
                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Tools.showToast(SaveWbhbAccountAcitity.this, "需要获取相册权限");
                    }
                }).
                start();

    }

    static class PddBean {

        public String mall_id; //店铺id
        public String group_id;//商品品类id
        public String goods_id;//商品id
        public String sku_id; //库存id

    }
}
