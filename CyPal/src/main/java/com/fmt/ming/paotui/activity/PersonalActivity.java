package com.fmt.ming.paotui.activity;

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
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ImageLoaderUtil;
import com.fmt.ming.paotui.utils.ImageUtil;
import com.fmt.ming.paotui.utils.Tools;
import com.fmt.ming.paotui.view.CircleImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
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


    }

    private void initView() {
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


    private void initOlcik() {

        ll_myicon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PersonalActivity.this,
                        MultiImageSelectorActivity.class);
                intent.putExtra("isUploadIcon", true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        false);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                        1);
                startActivityForResult(intent, 0);
            }
        });

    }


    private String pNumber(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
        return "";
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
                map.put("token", mSavePreferencesData.getStringData("token"));
                map.put("israpp", "1");
                File file = new File(filePaths.get(0));
                myicon.setImageBitmap(bitmapInfos.get(0));
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
            requestBody.addFormDataPart("img", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(Const.BASE_URL + Const.avatar).post(requestBody.build()).tag(PersonalActivity.this).build();
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
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(str);
                    changejson(jsonObject);
                    mDialog.dismiss();
                    // Log.i("lfq", response.message() + " , body " + str);

                } else {
                    mDialog.dismiss();
                    // Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        });

    }

    @Override
    public void onResponse(String response, String url) {
        dismissLoading();
        try {
            JSONObject json = new JSONObject(response);
            int resultCode = json.getInt("status");
            String msg = json.getString("msg");

            if (resultCode == 0) {
//                if (url.contains(Const.uploadToken)) {
//                    String resultJson = json.getString("result");
//                    upload_token = resultJson;
//                } else if ((url.contains(Const.updateMemberInfo))) {
//                    Tools.showToast(PersonalActivity.this, "修改成功");
//                    // 取到登录到json 改变其中的数据
//                    changejson();
//                }
            } else if (resultCode == -100) {
                Tools.jump(this, LoginActivity.class, true);
                Tools.showToast(this, "登录过期请重新登录");
            } else {
                Tools.showToast(getApplicationContext(), msg);
            }
        } catch (JSONException e) {
            // TODO: handle exception
            Tools.showToast(getApplicationContext(), "解析数据错误");
        }
    }

    private void changejson(com.alibaba.fastjson.JSONObject jsonObject) {
        storeBean.setAvatar(jsonObject.getJSONObject("data").getString("avatar"));
        String json = JSON.toJSONString(storeBean);
        mSavePreferencesData.putStringData("json", json);
        initAdmin();

    }
}
