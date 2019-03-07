package com.fmt.ming.paotui.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.base.BaseActivity;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.ImageUtil;
import com.fmt.ming.paotui.utils.ParamTools;
import com.fmt.ming.paotui.utils.Tools;
import com.fmt.ming.paotui.view.ImageWithDelete;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
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
 * @author 我要取件
 */
public class TakeActivity extends BaseActivity implements ImageWithDelete.ICallBack {


    @Bind(R.id.et_text)
    EditText etText;
    @Bind(R.id.topic_photo_group)
    LinearLayout topicPhotoGroup;
    @Bind(R.id.tv_adds)
    TextView tvAdds;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.rb_qishou)
    RadioButton rbQishou;
    @Bind(R.id.rb_xianshang)
    RadioButton rbXianshang;
    @Bind(R.id.rb_yue)
    RadioButton rbYue;
    @Bind(R.id.tv_information)
    TextView tv_information;


    private String order_uuid, typestring;
    private List<ImageWithDelete> images;
    private ImageView iv_add_photo;
    private int maxSelectedCount = 3;

    private List<String> filePaths;
    private List<String> icon_ids;
    private List<Bitmap> bitmapInfos;
    private List<String> resultList;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_take);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.darkgray));
        Tools.acts.add(this);
        initTitle();
        initView();
    }

    private void initView() {
        rbQishou.setChecked(true);
        order_uuid = getIntent().getStringExtra("order_uuid");
        typestring = getIntent().getStringExtra("typestring");
        if (typestring.equals("取件")) {
            title.setText("我要" + typestring);

        } else {
            title.setText("我已" + typestring);
        }
        tv_information.setText(typestring+"信息");
        tvText.setText(typestring + "拍照");
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        filePaths = new ArrayList<String>();
        icon_ids = new ArrayList<String>();
        images = new ArrayList<ImageWithDelete>();
        bitmapInfos = new ArrayList<Bitmap>();
        resultList = new ArrayList<String>();
        iv_add_photo = new ImageView(this);
        iv_add_photo.setBackgroundResource(R.drawable.add_photo);
        iv_add_photo.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_add_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Intent intent = new Intent(TakeActivity.this, MultiImageSelectorActivity.class);
                    // 是否显示拍摄图片
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    // 最大可选择图片数量
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxSelectedCount - images.size());
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        initPhotos(0);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有图片 有就上传
                if (filePaths.size() > 0) {
                    for (int i = 0; i < filePaths.size(); i++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("token", mSavePreferencesData.getStringData("token"));
                        map.put("israpp", "1");
                        File file = new File(filePaths.get(i));
                        post_file(map, file, i);
                    }

                } else {
                    //更新订单状态
                    if (typestring.equals("取件")) {
                        update(order_uuid, 2);
                    } else {
                        update(order_uuid, 3);
                    }
                }


            }
        });
        tvAdds.setText(mSavePreferencesData.getStringData("location_name"));

    }

    /**
     * @param order_uuid
     * @param action     操作类型{0:拒单,1:接单,2:取件,3:完成
     */
    public void update(String order_uuid, int action) {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        map.put("order_uuid", order_uuid);
        map.put("action", action + "");//操作类型{0:拒单,1:接单,2:取件,3:完成
        map.put("remark", etText.getText().toString());
        map.put("imgs[]", icon_ids.toString());
        mQueue.add(ParamTools.packParam(Const.update, this, this, map));
    }

    /**
     * 这里动态的去添加图片
     */
    private void initPhotos(int size) {
        images = new ArrayList<ImageWithDelete>();
        topicPhotoGroup.removeAllViewsInLayout();
        int photo_width = Tools.dip2px(this, 65);
        int line_width = Tools.dip2px(this, 5);
        for (int i = 0; i < size; i++) {
            ImageWithDelete iv_photo = new ImageWithDelete(this);
            iv_photo.setonClick(this);
            iv_photo.getIv_photo().setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(photo_width, photo_width);
            View blank_view = new View(this);
            blank_view.setLayoutParams(new LinearLayout.LayoutParams(line_width, line_width));
            iv_photo.setLayoutParams(params);

            iv_photo.getIv_photo().setLayoutParams(new RelativeLayout.LayoutParams(photo_width, photo_width));
            topicPhotoGroup.addView(iv_photo);
            topicPhotoGroup.addView(blank_view);
            images.add(i, iv_photo);
            iv_photo.setPosition(i);
        }
        topicPhotoGroup.addView(iv_add_photo);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }

    protected void post_file(final Map<String, String> map, File file, final int i) {
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
        Request request = new Request.Builder().url(Const.BASE_URL + Const.upload).post(requestBody.build()).tag(TakeActivity.this).build();
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
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    icon_ids.add(jsonObject1.getString("path"));
                    if (i + 1 == filePaths.size()) {
                        mDialog.dismiss();
                        Log.i("lfq", "图片上传全部完成" + i);
                        //更新订单状态
                        if (typestring.equals("取件")) {
                            update(order_uuid, 2);
                        } else {
                            update(order_uuid, 3);
                        }
                    } else {
                        Log.i("lfq", "图片上传成功" + i);
                    }


                } else {
                    Log.i("lfq", "图片上传失败" + i);
                    if (i + 1 == filePaths.size()) {
                        mDialog.dismiss();
                    }
                    Tools.showToast(TakeActivity.this, "上传失败");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            resultList = data.getExtras().getStringArrayList("select_result");

            int resultSize = resultList.size();
            int imageSize = images.size();

            int totalSize = imageSize + resultSize; // 这里算出总的长度 重新初始化
            initPhotos(totalSize);
            for (int k = 0; k < resultSize; k++) // 将数据重新整合
            {
                Bitmap bitmap = ImageUtil.decodeImage(resultList.get(k));
                bitmapInfos.add(bitmap);
                filePaths.add(resultList.get(k));
            }

            for (int i = 0; i < bitmapInfos.size(); i++) {
                Bitmap bitmap = bitmapInfos.get(i);
                images.get(i).setVisibility(View.VISIBLE);
                images.get(i).setBackgroundDrawable(bitmap);
            }
            iv_add_photo.setVisibility(checkAddphotoIsAble() ? View.VISIBLE : View.GONE);
        }

    }

    private Boolean checkAddphotoIsAble() {
        if (bitmapInfos.size() < maxSelectedCount)
            return true;
        else
            return false;

    }

    @Override
    public void onResponse(String response, String url) {
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("status");
            String msg = json.optString("msg");
            if (stauts == 0) {
                finish();
                if (typestring.equals("取件")) {
                    Tools.showToast(TakeActivity.this, "取件成功");

                } else {
                    Tools.showToast(TakeActivity.this, "成功送达");
                }

            } else {
                Tools.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(this, "发生错误,请重试!");
        }

    }

    private void setPhotosPos() {
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setPosition(i);
        }
    }

    @Override
    public void onClickDeleteButton(int position) {
        images.get(position).setVisibility(View.GONE);
        filePaths.remove(position);
        bitmapInfos.remove(position);
        images.remove(position);
        icon_ids.remove(position);
        setPhotosPos();
        iv_add_photo.setVisibility(checkAddphotoIsAble() ? View.VISIBLE : View.GONE);

    }
}
