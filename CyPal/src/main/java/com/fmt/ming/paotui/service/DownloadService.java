package com.fmt.ming.paotui.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.fmt.ming.paotui.BuildConfig;
import com.fmt.ming.paotui.R;

public class DownloadService extends Service {
    private static final int NOTIFY_ID = 0;
    private int progress;
    private NotificationManager mNotificationManager;
    private boolean canceled;
    // 返回的安装包url
    public static String apkUrl;
    // private String apkUrl = MyApp.downloadApkUrl;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/jiebian/";

    private static String saveFileName = savePath + "jiebianGo.apk";
    private NotificationUpdateActivity.ICallbackResult callback;
    private DownloadBinder binder;
    private boolean serviceIsDestroy = false;

    private Context mContext = this;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case 2:
                    // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        RemoteViews contentview = mNotification.contentView;
                        contentview.setTextViewText(R.id.tv_progress, rate + "%");
                        contentview.setProgressBar(R.id.progressbar, 100, rate, false);
                    } else {
                       // Notification.Builder builder = new Notification.Builder(mContext);
                        long when = System.currentTimeMillis();
//                        builder.setContentTitle();
//                        builder.setContentText("文件已下载完毕");
//                        builder.setWhen(when);
//                        builder.setWhen(when);
                        mNotification = new Notification(R.drawable.ic_launcher, "下载完成", when);

                        Intent intent = new Intent(mContext, NotificationUpdateActivity.class);
                        // 告知已完成
                        intent.putExtra("completed", "yes");
                        // 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        // 下载完毕后变换通知形式
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        mNotification.contentView = null;
                        //mNotification.setLatestEventInfo(mContext, "下载完成", "文件已下载完毕", contentIntent);

                        // 指定内容意图
                        mNotification.contentIntent = contentIntent;
                        //
                        serviceIsDestroy = true;
                        stopSelf();// 停掉服务自身
                    }

                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
            }
        }
    };

    //
    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
    // // TODO Auto-generated method stub
    // return START_STICKY;
    // }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("是否执行了 onBind");
        return binder;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        System.out.println("downloadservice ondestroy");
        // 假如被销毁了，无论如何都默认取消了。
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("downloadservice onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        // TODO Auto-generated method stub

        super.onRebind(intent);
        System.out.println("downloadservice onRebind");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        binder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //startForeground(true);// 这个不确定是否有作用
    }

    public class DownloadBinder extends Binder {
        public void start() {
            if (downLoadThread == null || !downLoadThread.isAlive()) {

                progress = 0;
                setUpNotification();
                new Thread() {
                    public void run() {
                        // 下载
                        startDownload();
                    }

                    ;
                }.start();
            }
        }

        public void cancel() {
            canceled = true;
        }

        public int getProgress() {
            return progress;
        }

        public boolean isCanceled() {
            return canceled;
        }

        public boolean serviceIsDestroy() {
            return serviceIsDestroy;
        }

        public void cancelNotification() {
            mHandler.sendEmptyMessage(2);
        }

        public void addCallback(NotificationUpdateActivity.ICallbackResult callback) {
            DownloadService.this.callback = callback;
        }
    }

    private void startDownload() {
        // TODO Auto-generated method stub
        canceled = false;
        downloadApk();
    }

    //
    Notification mNotification;

    // 通知栏

    /**
     * 创建通知
     */
    private void setUpNotification() {
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentText(tickerText);
//        builder.setWhen(when);
//        builder.setSmallIcon(icon);
        mNotification = new Notification(icon, tickerText, when);

        RemoteViews contentView = new RemoteViews(getPackageName(),
                R.layout.download_notification_layout);
        contentView.setTextViewText(R.id.name, "街边GO.apk 正在下载...");
       // mNotification = builder.getNotification();
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        // 指定个性化视图
        mNotification.contentView = contentView;
        // mNotification.icon=icon;

        Intent intent = new Intent(this, NotificationUpdateActivity.class);
        // 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
        // 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
        // 是这么理解么。。。
        // intent.setAction(Intent.ACTION_MAIN);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    //
    /**
     * 下载apk
     *
     * @param url
     */
    private Thread downLoadThread;

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     *
     * @param
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(DownloadService.this, BuildConfig.APPLICATION_ID + ".fileProvider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
        callback.OnBackResult("finish");

    }

    private int lastRate = 0;
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                ApkFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                        if (callback != null)
                            callback.OnBackResult(progress);
                    }
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(0);
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

}
