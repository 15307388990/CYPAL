package com.cypal.ming.cypal.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activityTwo.PayPasswordActivity;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.InfoEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.popwindow.SelectPopupWindow;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.MD5Util;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.cypal.ming.cypal.view.CircleImageView;
import com.cypal.ming.cypal.ws.WsManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * @author luoming 个人中心
 */
public class PersonalActivity extends BaseActivity {
    @ViewInject(R.id.ll_view_back)
    private LinearLayout iv_back;

    @ViewInject(R.id.ll_myicon)
    private LinearLayout ll_myicon;
    @ViewInject(R.id.ll_myname)
    private LinearLayout ll_myname;
    @ViewInject(R.id.ll_myphone)
    private LinearLayout ll_myphone;
    @ViewInject(R.id.my_accounts)
    private TextView my_accounts;//账户
    @ViewInject(R.id.myicon)
    private CircleImageView myicon;
    @ViewInject(R.id.my_name)
    private TextView my_name;
    @ViewInject(R.id.my_phone)
    private TextView my_phone;
    @ViewInject(R.id.my_hasPayPassword)
    private TextView my_hasPayPassword;
    @ViewInject(R.id.ll_hasPayPassword)
    private LinearLayout ll_hasPayPassword;


    private Button btn_exit;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();
    private String upload_token;
    private String member_headimgurl;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ViewUtils.inject(this);
        initView();
        MySetting();

    }

    private void initView() {
        btn_exit = (Button) findViewById(R.id.btn_exit);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalActivity.this.finish();
            }
        });
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        my_name.setText(storeBean.getName());
        my_phone.setText(storeBean.getDepartment());
        my_accounts.setText(storeBean.getMobile());
        if (storeBean.getAvatar() == null) {
            imageLoader.displayImage(Const.USER_DEFAULT_ICON, myicon, options);
        } else {
            imageLoader.displayImage(storeBean.getAvatar(), myicon,
                    options);

        }
        initOlcik();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void MySetting() {
        Map<String, String> map = new HashMap<>();
        map.put("contactType", "mySetting");
        mQueue.add(ParamTools.packParam(Const.setIn, this, this, map, com.android.volley.Request.Method.GET, this));
        loading();
    }

    private void loginOut() {
        Map<String, String> map = new HashMap<>();
        mQueue.add(ParamTools.packParam(Const.loginOut, this, this, map, com.android.volley.Request.Method.GET, this));
        loading();
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

    private void modifyAvatar(String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Tools.showToast(PersonalActivity.this, "图片地址获取失败");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("avatar", avatar);
        mQueue.add(ParamTools.packParam(Const.modifyAvatar, this, this, this, map));
    }

    private void initOlcik() {

        ll_myicon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                initPermission();

            }
        });
        my_accounts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(PersonalActivity.this, MemberActivity.class, false);
            }
        });
        my_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, NickNameActivity.class);
                intent.putExtra("nickname", my_name.getText().toString().trim());
                startActivityForResult(intent, 1);
            }
        });
        btn_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrderDialog();
            }
        });
        //支付密码
        ll_hasPayPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSavePreferencesData.getBooleanData("hasPayPassword")) {
                    inoutPsw();
                } else {
                    Tools.jump(PersonalActivity.this, PayPasswordActivity.class, false);
                }
            }
        });

    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE,
                        Permission.Group.CAMERA).
                onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Intent intent = new Intent(PersonalActivity.this,
                                MultiImageSelectorActivity.class);
                        intent.putExtra("isUploadIcon", true);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                                false);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                                1);
                        startActivityForResult(intent, 0);
                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Tools.showToast(PersonalActivity.this, "无法获取手机权限，功能无法正常使用");
                    }
                }).
                start();

    }

    private void deleteOrderDialog() {

        CancelTheDealDialog.newInstance().setTitle("温馨提示").setContext("您确定要退出登录?").setQtext("取消").setOktext("确定")
                .setOnClickListener(new CancelTheDealDialog.OnClickListener() {
                    @Override
                    public void successful() {
                        loginOut();
                    }
                }).show(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
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
                map.put("uploadEnum", "avatar");
                File file = new File(filePaths.get(0));
                myicon.setImageBitmap(bitmapInfos.get(0));
                post_file(map, file);

            } else {
                if (data != null) {
                    my_name.setText(data.getStringExtra("nickname"));
                }
            }
        }
    }


    protected void post_file(final Map<String, String> map, File file) {
        mDialog.setMessage("图片上传中...");
        mDialog.show();
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(Const.BASE_URL + Const.image).post(requestBody.build()).tag(PersonalActivity.this).
                build().newBuilder().addHeader("token", mSavePreferencesData.getStringData("token")).
                addHeader("os", "android").addHeader("version", "1001").build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDialog.dismiss();
                // Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    changejson(str);
                    mDialog.dismiss();
                    // Log.i("lfq", response.message() + " , body " + str);

                } else {
                    mDialog.dismiss();
                    // Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        });

    }

    private void changejson(String response) {
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("code");
            String msg = json.optString("msg");
            if (stauts == 1) {
                String data = json.optString("data");
                List<String> list = JSON.parseArray(data, String.class);
                modifyAvatar(list.get(0));
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "数据格式不对");
        }

    }

    @Override
    protected void returnData(String data, String url) {
        if (url.contains(Const.setIn)) {
            InfoEntity infoEntity = JSON.parseObject(data, InfoEntity.class);
            InfoEntity.DataBean.MyInformationBeanBean myInformationBeanBean = infoEntity.data.myInformationBean;
            if (myInformationBeanBean.avatar == null) {
                imageLoader.displayImage(Const.USER_DEFAULT_ICON, myicon, options);
            } else {
                imageLoader.displayImage(myInformationBeanBean.avatar, myicon,
                        options);

            }
            my_name.setText(myInformationBeanBean.nickName);
            my_phone.setText(myInformationBeanBean.account);
            //是否认证
            if (myInformationBeanBean.certification) {
                my_accounts.setText("已认证");
            } else {
                my_accounts.setText("未认证");
            }
            if (mSavePreferencesData.getBooleanData("hasPayPassword")) {
                my_hasPayPassword.setText("已设置");
            } else {
                my_hasPayPassword.setText("未设置");
            }
        } else if (url.contains(Const.loginOut)) {
            mSavePreferencesData.putStringData("token", "");
            Tools.jump(PersonalActivity.this, LoginActivity.class, true);
            //断开连接
            WsManager.getInstance().disconnect();
        } else if (url.contains(Const.setPayPassword)) {
            my_hasPayPassword.setText("已设置");
            mSavePreferencesData.putBooleanData("hasPayPassword", true);
        }
    }

    //打开输入密码的对话框
    public void inoutPsw() {
        SelectPopupWindow menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete)
                    setPayPassword(psw);
            }
        });
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }


}
