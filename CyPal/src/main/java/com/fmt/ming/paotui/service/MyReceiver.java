package com.fmt.ming.paotui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.activity.TabActivity;
import com.fmt.ming.paotui.activity.WebviewActivity;
import com.fmt.ming.paotui.base.MianApplication;
import com.fmt.ming.paotui.config.Const;
import com.fmt.ming.paotui.utils.Logger;
import com.fmt.ming.paotui.utils.SavePreferencesData;
import com.iflytek.cloud.SpeechSynthesizer;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */


public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private Context mContext;
    public SavePreferencesData mSavePreferencesData;
    MianApplication application;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        mSavePreferencesData = new SavePreferencesData(context);
        application = (MianApplication) mContext.getApplicationContext();
        SpeechSynthesizer.getSynthesizer();
        try {
            Bundle bundle = intent.getExtras();

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                String masgee = bundle.getString(JPushInterface.EXTRA_EXTRA);
                JSONObject jsonObject = JSON.parseObject(masgee);
                String jtype = jsonObject.getString("jtype");
                if (jtype.equals("1")) {
                    JSONObject massagejs = jsonObject.getJSONObject("message");
                    String uuid = massagejs.getString("uuid");
                    createNotificationMessge(uuid);
                } else {
                    createNotificationMessge();
                }


            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

//                //打开自定义的Activity
//                Intent i = new Intent(context, TabActivity.class);
//                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            Logger.d(TAG, "[MyReceiver] Unhandled intent - " + e.toString());
        }

    }


    private void createNotificationMessge() {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //打开自定义的Activity
        Intent i = new Intent(mContext, TabActivity.class);
        PendingIntent paddingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        Uri parse = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.dingdong);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("您有新的订单")
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSound(parse).setContentIntent(paddingIntent);
        Notification notification = builder.build();

        int score = application.getScore();
        notificationManager.notify(score, notification);
        application.setScore(score+1);
    }

    private void createNotificationMessge(String uuid) {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //打开自定义的Activity
        Intent i = new Intent(mContext, WebviewActivity.class);
        i.putExtra("link_url", Const.BASE_URL + Const.messageshow + "?token=" + mSavePreferencesData.getStringData("token") + "&message_uuid=" + uuid);
        i.putExtra("link_name", "消息详情");
        PendingIntent paddingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        Uri parse = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.beep);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("您有新的消息")
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSound(parse).setContentIntent(paddingIntent);
        Notification notification = builder.build();
        int score = application.getScore();
        notificationManager.notify(score, notification);
        application.setScore(score+1);
    }

}
