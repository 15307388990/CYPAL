package com.cypal.ming.cypal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
 * @author luoming 添加收款账号
 */
public class SaveAccountAcitity extends BaseActivity implements View.OnClickListener {

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
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private TextView tv_account;
    private String accountData;
    private AccountListEntity.DataBean dataBean;
    private TextView right_view_text;
    private String accountId;
    private EditText et_pid;
    private LinearLayout ll_pid;
    private LinearLayout ll_code;
    private TextView tv_pid;
    private TextView tv_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_save_account);
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
        map.put("accountData", accountData);
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
        tv_pid = (TextView) findViewById(R.id.tv_pid);
        btn_next.setOnClickListener(this);
        iv_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();
            }
        });
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_account.setText(value + "账号");
        et_accout.setHint("请输入" + value + "账号");
        right_view_text = (TextView) findViewById(R.id.right_view_text);
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
        tv_text.setText("温馨提示： \n1、请确保" + value + "已实名认证，并填写真实姓名（请不要填写 呢称） \n2、为了保证顺利提现，30天内不要更换其他账号，否则会 导致提现不通过 \n3、我们承诺不会向任何人透露您的个人信息");
        tv_pid.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        et_pid = (EditText) findViewById(R.id.et_pid);
        ll_pid = (LinearLayout) findViewById(R.id.ll_pid);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);
        if (type.equals("ALIPAY_PID")) {
            ll_pid.setVisibility(View.VISIBLE);
            ll_code.setVisibility(View.GONE);
        } else {
            ll_code.setVisibility(View.VISIBLE);
            ll_pid.setVisibility(View.GONE);
        }
        tv_pid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveAccountAcitity.this, WebviewActivity.class);
                String url = Const.BASE_URL + "/h5/pid";
                intent.putExtra("link_url", url);
                intent.putExtra("link_name", "PID获取图文教程");
                startActivity(intent);
            }
        });
        if (dataBean != null) {
            InitDate(dataBean);
        }
    }

    private void InitDate(AccountListEntity.DataBean dataBean) {
        right_view_text.setVisibility(View.VISIBLE);
        et_name.setText(dataBean.realName);
        et_accout.setText(dataBean.accountName);
        accountData = dataBean.accountData;
        accountId = dataBean.id + "";
        Bitmap bitmap = CodeUtils.createImage(dataBean.accountData, 166, 167, null);
        iv_xia.setImageBitmap(bitmap);
        et_pid.setText(accountData);
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
                    accountData = result;
                    iv_xia.setImageBitmap(bitmapInfos.get(0));
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
        if (type.equals("ALIPAY_PID")) {
            String pid = et_pid.getText().toString().trim();
            accountData = pid;
            if (TextUtils.isEmpty(pid)) {
                Toast.makeText(this, "请输入支付宝PID", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            if (TextUtils.isEmpty(accountData)) {
                Toast.makeText(this, "请先上传正确的收款二维码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
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
                        Intent intent = new Intent(SaveAccountAcitity.this,
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
                        Tools.showToast(SaveAccountAcitity.this, "需要获取相册权限");
                    }
                }).
                start();

    }

}
