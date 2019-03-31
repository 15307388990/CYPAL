package com.cypal.ming.cypal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.ParamTools;
import com.cypal.ming.cypal.utils.Tools;

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

import static com.cypal.ming.cypal.config.Const.save;
import static java.lang.String.valueOf;

/**
 * 申诉订单
 */
public class ComplaintOrderActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_view_back;
    private EditText et_text;
    private ImageView iv_img;
    private Button btn_next;
    private List<Bitmap> bitmapInfos = null;
    private List<String> resultList = null;
    private List<String> icon_ids = null;
    private List<String> filePaths = null;
    private ProgressDialog mDialog;
    private String orderId;
    private String shenfen1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_complaint_order );
        orderId = getIntent().getStringExtra( "orderId" );
        initView();
    }


    public void save() {
        Map<String, String> map = new HashMap<>();
        map.put( "paymentVoucher", shenfen1 );
        map.put( "orderId", orderId );
        map.put( "remark", et_text.getText().toString().trim() );
        mQueue.add( ParamTools.packParam( Const.service, this, this, this, map ) );
        loading();
    }


    private void initView() {
        mDialog = new ProgressDialog( this );
        mDialog.setCancelable( false );
        ll_view_back = (LinearLayout) findViewById( R.id.ll_view_back );
        ll_view_back.setOnClickListener( this );
        et_text = (EditText) findViewById( R.id.et_text );
        iv_img = (ImageView) findViewById( R.id.iv_img );
        iv_img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ComplaintOrderActivity.this,
                        MultiImageSelectorActivity.class );
                intent.putExtra( "isUploadIcon", true );
                intent.putExtra( MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        false );
                intent.putExtra( MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
                        1 );
                startActivityForResult( intent, 0 );
            }
        } );
        btn_next = (Button) findViewById( R.id.btn_next );
        btn_next.setOnClickListener( this );
        ll_view_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
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
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK) {
            if (data != null) {
                resultList = data.getExtras().getStringArrayList( "select_result" );
                bitmapInfos = new ArrayList<Bitmap>();
                filePaths = new ArrayList<String>();
                icon_ids = new ArrayList<String>();

                for (int k = 0; k < resultList.size(); k++) {
                    Bitmap bitmap = ImageUtil.decodeImage( resultList.get( k ) );
                    bitmapInfos.add( bitmap );
                    filePaths.add( resultList.get( k ) );
                }
                Map<String, String> map = new HashMap<>();
                map.put( "uploadEnum", "pay" );
                File file = new File( filePaths.get( 0 ) );
                iv_img.setImageBitmap( bitmapInfos.get( 0 ) );

                post_file( map, file, requestCode );
            }
        }
    }

    protected void post_file(final Map<String, String> map, File file, final int requestCode) {
        mDialog.setMessage( "图片上传中..." );
        mDialog.show();
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType( MultipartBody.FORM );
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create( MediaType.parse( "image/*" ), file );
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart( "file", file.getName(), body );
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart( valueOf( entry.getKey() ), valueOf( entry.getValue() ) );
            }
        }
        Request request = new Request.Builder().url( Const.BASE_URL + Const.image ).post( requestBody.build() ).tag( ComplaintOrderActivity.this ).
                build().newBuilder().addHeader( "token", mSavePreferencesData.getStringData( "token" ) ).
                addHeader( "os", "android" ).addHeader( "version", "1001" ).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout( 5000, TimeUnit.MILLISECONDS ).build().newCall( request ).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDialog.dismiss();
                // Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    changejson( str );
                    mDialog.dismiss();
                    // Log.i("lfq", response.message() + " , body " + str);

                } else {
                    mDialog.dismiss();
                    // Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        } );

    }

    private void changejson(String response) {
        try {
            JSONObject json = new JSONObject( response );
            int stauts = json.optInt( "code" );
            String msg = json.optString( "msg" );
            if (stauts == 1) {
                String data = json.optString( "data" );
                List<String> list = JSON.parseArray( data, String.class );
                shenfen1 = list.get( 0 );
            } else {
                Tools.showToast( this, msg );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast( this, "数据格式不对" );
        }

    }

    private void submit() {
        // validate
        String code = et_text.getText().toString().trim();
        if (TextUtils.isEmpty( code )) {
            Tools.showToast( this, "请输入备注" );
            return;
        }

        if (TextUtils.isEmpty( shenfen1 )) {
            Tools.showToast( this, "请上传付款凭证" );
            return;
        }
        save();


    }

    @Override
    protected void returnData(String data, String url) {
        Tools.showToast( this, "提交成功等待审核" );
        finish();
    }
}
