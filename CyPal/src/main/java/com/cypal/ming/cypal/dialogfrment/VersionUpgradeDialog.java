package com.cypal.ming.cypal.dialogfrment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cypal.ming.cypal.BuildConfig;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.activity.TabActivity;
import com.cypal.ming.cypal.bean.VersionEntity;
import com.cypal.ming.cypal.config.Const;
import com.cypal.ming.cypal.databinding.ConfirmPaymentDialogBinding;
import com.cypal.ming.cypal.databinding.UpgradeDialogBinding;
import com.cypal.ming.cypal.service.DownloadService;
import com.cypal.ming.cypal.utils.ImageUtil;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
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
 * 版本升级
 */
public class VersionUpgradeDialog extends CenterDialog {

    private static final String VERSION = "version";
    private UpgradeDialogBinding binding;
    private OnClickListener onClickListener;
    private ProgressDialog mDialog;
    public SavePreferencesData mSavePreferencesData;
    private VersionEntity.DataBean versionBean;
    private ProgressBar pBar;
    private TextView tv_progress;
    private Handler mViewUpdateHandler;
    // 下载存储的文件名
    private static final String DOWNLOAD_NAME = "cypay";

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        void successful(String paymentVoucher);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage( msg );
                int progress = pBar.getProgress();
                int max = pBar.getMax();
                double dProgress = (double) progress / (double) (1024 * 1024);
                double dMax = (double) max / (double) (1024 * 1024);

            }
        };
    }




    public VersionUpgradeDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;

    }


    public static VersionUpgradeDialog newInstance(VersionEntity.DataBean versionBean) {
        VersionUpgradeDialog dialog = new VersionUpgradeDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable( VERSION, versionBean );
        dialog.setArguments( bundle );
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
        super.onDismiss( dialog );
    }

    @Override
    public int getLayoutId() {
        return R.layout.upgrade_dialog;
    }

    @Override
    public int getWindowWidth() {
        return (int) (Tools.getScreenWidth( getActivity() ) * 0.75);
    }


    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (UpgradeDialogBinding) dataBinding;
        pBar = binding.pbProgressbar;
        tv_progress = binding.tvProgress;
        mDialog = new ProgressDialog( VersionUpgradeDialog.this.getActivity() );
        mSavePreferencesData = new SavePreferencesData( VersionUpgradeDialog.this.getActivity() );
        mDialog.setCancelable( false );
        Bundle bundle = getArguments();
        if (bundle != null) {
            versionBean = (VersionEntity.DataBean) bundle.getSerializable( VERSION );
        }
        binding.tvTitle.setText( "发现新版本" + versionBean.versionName );
        binding.tvContext.setText( versionBean.updateContext );
        binding.tvOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvContext.setVisibility( View.GONE );
                binding.llProgres.setVisibility( View.VISIBLE );
                binding.tvOk.setVisibility( View.GONE );
                DownloadTask downloadTask = new DownloadTask(
                        getActivity() );
                downloadTask.execute( "http://openbox.mobilem.360.cn/index/d/sid/3429345" );
            }
        } );
        binding.tvCancle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );
    }

    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL( sUrl[0] );
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED )) {
                    file = new File( Environment.getExternalStorageDirectory(),
                            DOWNLOAD_NAME );

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }

                } else {
                    Toast.makeText( getActivity(), "sd卡未挂载",
                            Toast.LENGTH_LONG ).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream( file );
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read( data )) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress( (int) (total * 100 / fileLength) );
                    output.write( data, 0, count );

                }
            } catch (Exception e) {
                System.out.println( e.toString() );
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService( Context.POWER_SERVICE );
            mWakeLock = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName() );
            mWakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate( progress );
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate( false );
            pBar.setMax( 100 );
            pBar.setProgress( progress[0] );
            tv_progress.setText( pBar.getProgress()+ "%" );

        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            dismiss();
            System.out.print( "r日你" + result);
            if (result != null) {

//                // 申请多个权限。大神的界面
//                AndPermission.with(MainActivity.this)
//                        .requestCode(REQUEST_CODE_PERMISSION_OTHER)
//                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
//                        .rationale(new RationaleListener() {
//                                       @Override
//                                       public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
//                                           // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
//                                           AndPermission.rationaleDialog(MainActivity.this, rationale).show();
//                                       }
//                                   }
//                        )
//                        .send();
                // 申请多个权限。
                List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                permissonItems.add( new PermissionItem( Manifest.permission.WRITE_EXTERNAL_STORAGE, "文件下载", R.drawable.permission_ic_storage ) );
                permissonItems.add( new PermissionItem( Manifest.permission.READ_EXTERNAL_STORAGE, "文件读取", R.drawable.permission_ic_location ) );
                permissonItems.add( new PermissionItem( Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, "文件安装", R.drawable.permission_ic_location ) );
                HiPermission.create( getActivity() ).permissions( permissonItems );

                Toast.makeText( context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG ).show();
            } else {
                // Toast.makeText(context, "File downloaded",
                // Toast.LENGTH_SHORT)
                // .show();
                update();
            }

        }
    }


    private static final int REQUEST_CODE_SETTING = 300;


    private void update() {
        File apkfile = new File( DOWNLOAD_NAME );
        if (!apkfile.exists()) {
            return;
        }
        //安装应用
        Intent intent = new Intent( Intent.ACTION_VIEW );
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            Uri contentUri = FileProvider.getUriForFile( getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", apkfile );
            intent.setDataAndType( contentUri, "application/vnd.android.package-archive" );
        } else {
            intent.setDataAndType( Uri.fromFile( new File( Environment
                    .getExternalStorageDirectory(), DOWNLOAD_NAME ) ), "application/vnd.android.package-archive" );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        }
        startActivity( intent );


    }
}
