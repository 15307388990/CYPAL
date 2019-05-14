package com.cypal.ming.cypal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.bean.ManagerEntity;
import com.cypal.ming.cypal.bean.MemberEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.MessageEnum;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

import static com.cypal.ming.cypal.config.Const.accountList;
import static com.cypal.ming.cypal.config.Const.save;
import static java.lang.String.valueOf;

/**
 * 申请认证
 */
public class CertificationActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private EditText et_code;
    private EditText et_name;
    private ImageView iv_shenfen1;
    private ImageView iv_shenfen2;
    private ImageView iv_shenfen3;
    private TextView tv_text;
    private Button btn_next;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private ProgressDialog mDialog;

    private String shenfen1 = null;
    private String shenfen2 = null;
    private String shenfen3 = null;

    private String str;

    private MemberEntity memberEntity;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = ImageLoaderUtil.getOptions();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                changejson();
            } else if (msg.what == 1) {
                Tools.showToast(CertificationActivity.this, "图片上传失败 请重新上传");
            }

        }
    };
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        initView();
        memberEntity = (MemberEntity) getIntent().getSerializableExtra("memberEntity");
        if (memberEntity != null) {
            try {
                et_code.setText(memberEntity.data.certification.identitycard_number + "");
                et_name.setText(memberEntity.data.certification.real_name);
                shenfen1 = memberEntity.data.certification.identitycard_front;
                shenfen2 = memberEntity.data.certification.identitycard_reverse;
                shenfen3 = memberEntity.data.certification.identitycard_hand;
                imageLoader.displayImage(memberEntity.data.host + shenfen1, iv_shenfen1,
                        options);
                imageLoader.displayImage(memberEntity.data.host + shenfen2, iv_shenfen2,
                        options);
                imageLoader.displayImage(memberEntity.data.host + shenfen3, iv_shenfen3,
                        options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void save() {
        Map<String, String> map = new HashMap<>();
        map.put("identitycard_front", shenfen1);
        map.put("identitycard_reverse", shenfen2);
        map.put("identitycard_hand", shenfen3);
        map.put("identitycard_number", et_code.getText().toString().trim());
        map.put("real_name", et_name.getText().toString().trim());
        mQueue.add(ParamTools.packParam(save, this, this, this, map));
        loading();
    }


    private void initView() {
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        ll_view_back = (LinearLayout) findViewById(R.id.ll_view_back);
        et_code = (EditText) findViewById(R.id.et_code);
        et_name = (EditText) findViewById(R.id.et_name);
        iv_shenfen1 = (ImageView) findViewById(R.id.iv_shenfen1);
        iv_shenfen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificationActivity.this,
                        MultiImageSelectorActivity.class);
                intent.putExtra("isUploadIcon", true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        false);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                        1);
                startActivityForResult(intent, 0);
            }
        });
        iv_shenfen2 = (ImageView) findViewById(R.id.iv_shenfen2);
        iv_shenfen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificationActivity.this,
                        MultiImageSelectorActivity.class);
                intent.putExtra("isUploadIcon", true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        false);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                        1);
                startActivityForResult(intent, 1);
            }
        });
        iv_shenfen3 = (ImageView) findViewById(R.id.iv_shenfen3);
        iv_shenfen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificationActivity.this,
                        MultiImageSelectorActivity.class);
                intent.putExtra("isUploadIcon", true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        false);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                        1);
                startActivityForResult(intent, 2);
            }
        });
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        ll_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text.setText("1、手持白纸和身份证，并写明仅" + getString(R.string.app_name) + "上面使用 \n2、拍摄时确保身份证边框完整、脸部清晰，字体清晰，证件全部信息清晰无遮挡");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                submit();
                break;
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
                this.requestCode = requestCode;
                post_file(map, file);
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
        Request request = new Request.Builder().url(Const.BASE_URL + Const.image).post(requestBody.build()).tag(CertificationActivity.this).
                build().newBuilder().addHeader("token", mSavePreferencesData.getStringData("token")).
                addHeader("os", "android").addHeader("version", "1001").build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDialog.dismiss();
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    str = response.body().string();
                    mDialog.dismiss();
                    handler.sendEmptyMessage(0);

                } else {
                    mDialog.dismiss();
                }
            }
        });

    }

    private void changejson() {
        try {
            JSONObject json = new JSONObject(str);
            int stauts = json.optInt("code");
            String msg = json.optString("msg");
            if (stauts == 1) {
                String data = json.optString("data");
                List<String> list = JSON.parseArray(data, String.class);

                switch (requestCode) {
                    case 0:
                        shenfen1 = list.get(0);
                        iv_shenfen1.setImageBitmap(bitmapInfos.get(0));
                        break;
                    case 1:
                        shenfen2 = list.get(0);
                        iv_shenfen2.setImageBitmap(bitmapInfos.get(0));
                        break;
                    case 2:
                        shenfen3 = list.get(0);
                        iv_shenfen3.setImageBitmap(bitmapInfos.get(0));
                        break;
                }
            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "数据格式不对");
        }

    }

    private void submit() {
        // validate
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Tools.showToast(this, "请输入身份证号码");
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Tools.showToast(this, "请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(shenfen1)) {
            Tools.showToast(this, "请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(shenfen2)) {
            Tools.showToast(this, "请上传身份证反面");
            return;
        }
        if (TextUtils.isEmpty(shenfen3)) {
            Tools.showToast(this, "请上传手持身份证照");
            return;
        }
        save();
        // TODO validate success, do something


    }

    @Override
    protected void returnData(String data, String url) {
        Tools.showToast(this, "提交成功等待审核");
        finish();
    }
}
