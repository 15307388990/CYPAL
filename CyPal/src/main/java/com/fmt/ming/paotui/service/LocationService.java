package com.fmt.ming.paotui.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.SavePreferencesData;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class LocationService extends Service implements TencentLocationListener {

    public SavePreferencesData mSavePreferencesData;

    @Override
    public void onCreate() {
        mSavePreferencesData = new SavePreferencesData(this);
        TencentLocationManager locationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(300000);//多少毫米重新获取位置
        int error = locationManager.requestLocationUpdates(request, this);
        super.onCreate();
    }

    //位置服务
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSavePreferencesData.getStringData("token"));
        map.put("israpp", "1");
        map.put("lon", tencentLocation.getLongitude() + "");
        map.put("lat", tencentLocation.getLatitude() + "");
        mSavePreferencesData.putStringData("location_name",tencentLocation.getName());

        //获取到位置 传给服务器
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(Const.BASE_URL + Const.gis).post(requestBody.build()).tag(LocationService.this).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("lfq", "位置更新成功" + response.message());

            }
        });


    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
