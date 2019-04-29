package com.cypal.ming.cypal.activityTwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.CertificationActivity;
import com.cypal.ming.cypal.activity.LoginActivity;
import com.cypal.ming.cypal.activity.TabActivity;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.LoginEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.dialogfrment.CancelTheDealDialog;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cypal.ming.cypal.config.Const.loginAdvancedVerify;
import static com.cypal.ming.cypal.config.Const.save;
import static java.lang.String.valueOf;

/**
 * @author luoming
 * created at 2019/4/19 9:49 AM
 * 登陆上传照片身份验证
 */
public class LoginPhotoValidationActivity extends BaseActivity {
    private LinearLayout ll_view_back;
    private ImageView iv_shenfen3;
    private TextView tv_text;
    private Button btn_next;
    private ProgressDialog mDialog;

    private String accout;
    String shenfen3;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_login_photo_validation);
        path = getExternalCacheDir().getAbsolutePath() + File.separator + "222.png";
        initView();
    }

    private void initView() {
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        accout = getIntent().getStringExtra("accout");
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        iv_shenfen3 = (ImageView) findViewById(R.id.iv_shenfen3);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_next = (Button) findViewById(R.id.btn_next);
        iv_shenfen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_text.setText("1、手持白纸和身份证，并写明仅" + getString(R.string.app_name) + "上面使用 \n2、拍摄时确保身份证边框完整、脸部清晰，字体清晰，证件全部信息清晰无遮挡");
    }

    /**
     * 获取相册权限
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA).
                onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Intent intent = new Intent();
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                        startActivityForResult(intent, 300);

                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Tools.showToast(LoginPhotoValidationActivity.this, "需要获取拍照权限");
                    }
                }).
                start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            BitmapFactory.Options options = new BitmapFactory.Options();
//            2.对于属性进行设置，需要解锁边缘
            options.inJustDecodeBounds = true;
//            3.对于图片进行编码处理
            BitmapFactory.decodeFile(path, options);
//            4.获取原来图片的宽度和高度
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;
//            5.200,200  获得要压缩的比例
            int sampleHeight = outHeight / 200;  //2
            int sampleWidth = outWidth / 200;    //1.5
//            6.获取较大的比例
            int size = Math.max(sampleHeight, sampleWidth);
//            7.设置图片压缩的比例
            options.inSampleSize = size;
            /**图片的质量   1个字节是8位
             * ARGB_8888  32位     4字节   100*100*4 = 40000 个字节
             * ARGB_4444  16位     2字节   100*100*2 = 20000 个字节
             * RGB_565    16位      2字节  100*100*2 = 20000 个字节
             * Alpha_8    8位       1字节  100*100*1 = 10000 个字节
             *
             * 100px*100px  的图片
             * */
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;   //设置图片的质量类型
//            8.锁定边缘
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            Map<String, String> map = new HashMap<>();
            map.put("uploadEnum", "loginVerify");

            iv_shenfen3.setImageBitmap(bitmap);
            try {
                Tools.saveBitmap(bitmap, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            post_file(map, file);
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
        Request request = new Request.Builder().url(Const.BASE_URL + Const.image).post(requestBody.build()).tag(LoginPhotoValidationActivity.this).
                build().newBuilder().addHeader("token", mSavePreferencesData.getStringData("token")).
                addHeader("os", "android").addHeader("version", "1001").build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDialog.dismiss();
                // Log.i("lfq" ,"onFailu re");
                Tools.showToast(LoginPhotoValidationActivity.this, "图片上传失败 请重新上传");
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
                    Tools.showToast(LoginPhotoValidationActivity.this, "图片上传失败 请重新上传");
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
                shenfen3 = list.get(0);
            } else {
                Tools.showToast(this, msg);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "数据格式不对");
        }

    }

    /**
     * 高级安全验证
     */
    public void loginAdvancedVerify() {
        Map<String, String> map = new HashMap<>();
        map.put("account", accout);
        map.put("loginCertificate", shenfen3);
        mQueue.add(ParamTools.packParam(loginAdvancedVerify, this, this, this, map));
        loading();
    }

    private void submit() {

        if (TextUtils.isEmpty(shenfen3)) {
            Tools.showToast(this, "请上传手持身份证照");
            return;
        }
        loginAdvancedVerify();
        // TODO validate success, do something


    }

    @Override
    public void returnData(String data, String url) {
        new CancelTheDealDialog().setTitle("温馨提示").
                setContext("您的审核已成功提交，客服审核通过后会发短信通知您").setIsQuBtn(false).setOktext("知道了").setOnClickListener(new CancelTheDealDialog.OnClickListener() {
            @Override
            public void successful() {
                LoginPhotoValidationActivity.this.finish();
            }
        }).show(this);

    }


}
