package com.cypal.ming.cypal.dialogfrment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.CertificationActivity;
import com.cypal.ming.cypal.activity.SaveAccountAcitity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.CancelTheDealDialogBinding;
import com.cypal.ming.cypal.databinding.ConfirmPaymentDialogBinding;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;
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

import static java.lang.String.valueOf;


/**
 * @Author luoming
 * @Date 2019/3/14 10:32 AM
 * 确认付款
 */
public class ConfirmPaymentDialog extends CenterDialog {

    private static final String URL = "url";
    private ConfirmPaymentDialogBinding binding;
    private OnClickListener onClickListener;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private ImageView imageView;
    private ProgressDialog mDialog;
    private String paymentVoucher;
    public SavePreferencesData mSavePreferencesData;

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void successful(String paymentVoucher);

    }

    public ConfirmPaymentDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;

    }


    public static ConfirmPaymentDialog newInstance(String url) {
        ConfirmPaymentDialog dialog = new ConfirmPaymentDialog();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        dialog.setArguments(bundle);
        return dialog;
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
        super.onDismiss(dialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.confirm_payment_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth(getActivity()) * 0.75);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
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
                map.put("uploadEnum", "pay");
                File file = new File(filePaths.get(0));
                imageView.setImageBitmap(bitmapInfos.get(0));
                post_file(map, file);


            }
        }
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (ConfirmPaymentDialogBinding) dataBinding;
        imageView = binding.ivImg;
        mDialog = new ProgressDialog(ConfirmPaymentDialog.this.getActivity());
        mSavePreferencesData = new SavePreferencesData(ConfirmPaymentDialog.this.getActivity());
        mDialog.setCancelable(false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String buildUrl = bundle.getString(URL);
        }
        binding.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();
            }
        });
        binding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.successful(paymentVoucher);
                dismiss();
            }
        });
        binding.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
        Request request = new Request.Builder().url(Const.BASE_URL + Const.image).post(requestBody.build()).tag(this).
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
                        Intent intent = new Intent(ConfirmPaymentDialog.this.getActivity(),
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
                        Tools.showToast(ConfirmPaymentDialog.this.getActivity(), "需要获取相册权限");
                    }
                }).
                start();

    }

    private void changejson(String response) {
        try {
            JSONObject json = new JSONObject(response);
            int stauts = json.optInt("code");
            String msg = json.optString("msg");
            if (stauts == 1) {
                String data = json.optString("data");
                List<String> list = JSON.parseArray(data, String.class);
                paymentVoucher = list.get(0);

            } else {
                Tools.showToast(getActivity(), msg);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
            Tools.showToast(getActivity(), "数据格式不对");
        }

    }

}
